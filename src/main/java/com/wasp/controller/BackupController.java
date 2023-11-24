package com.wasp.controller;

import com.wasp.data.AccountData;
import com.wasp.data.AppData;
import com.wasp.handler.BackupHandler;
import com.wasp.handler.CsvHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.UnaryOperator;

public class BackupController extends BaseMainController {

    @FXML private TreeView<File> backupFileTreeView;
    @FXML private TextField backupFileNameField;
    @FXML private VBox backupTableVBox;
    @FXML private Button undoLoadBackupButton;
    @FXML private Label backupNameLabel;
    @FXML private Label backupLocationLabel;
    @FXML private Label backupDateLabel;
    @FXML private Label backupSaveStatusLabel;
    @FXML private Label backupManageStatusLabel;

    private final File backupDirectory = AppData.getInstance().getBackupDirectory();
    private File currentFileTreeLocation = backupDirectory;

    private final BackupHandler backupHandler = new BackupHandler();

    @Override
    public void initialize() {
        initializeBackupFileNameField();
        initializeFileTreeView(currentFileTreeLocation);
    }

    private void initializeBackupFileNameField() {
        int maxLength = 32;
        String invalidCharacters = ".*[/\\\\*?\"<>|:].*";

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (!newText.matches(invalidCharacters) && newText.length() <= maxLength) {
                return change;
            }
            return null;
        };
        TextFormatter<String> formatter = new TextFormatter<>(filter);
        backupFileNameField.setTextFormatter(formatter);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String backupFileName = dateFormat.format(new Date()) + "_backup";
        backupFileNameField.setText(backupFileName);
    }

    private void initializeFileTreeView(File rootDirectory) {
        TreeItem<File> rootNode = new TreeItem<>(rootDirectory);
        rootNode.setExpanded(true);

        File[] files = Objects.requireNonNull(rootDirectory.listFiles());

        for (File file : files) {
            if (file.isDirectory()) {
                TreeItem<File> directoryNode = new TreeItem<>(file);
                rootNode.getChildren().add(directoryNode);
                populateDirectoryNode(directoryNode, file);
            } else {
                TreeItem<File> fileNode = new TreeItem<>(file);
                rootNode.getChildren().add(fileNode);
            }
        }
        backupFileTreeView.setRoot(rootNode);
        backupFileTreeView.setCellFactory(param -> new TreeCell<>() {
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    private void populateDirectoryNode(TreeItem<File> directoryNode, File directory) {
        File[] files = Objects.requireNonNull(directory.listFiles());

        for (File file : files) {
            if (file.isDirectory()) {
                TreeItem<File> subDirectoryNode = new TreeItem<>(file);
                directoryNode.getChildren().add(subDirectoryNode);
                populateDirectoryNode(subDirectoryNode, file);
            } else {
                TreeItem<File> fileNode = new TreeItem<>(file);
                directoryNode.getChildren().add(fileNode);
            }
        }
    }

    @FXML
    private void onSaveButtonPressed() {
        final String invalidFieldStyle = "-fx-border-width: 2px; -fx-border-radius: 2px; -fx-border-color: rgba(255, 0, 0, 0.8);";
        final String defaultFieldStyle = "-fx-border-width: 0px; -fx-border-radius: 0px; -fx-border-color: transparent;";

        if (backupFileNameField.getText().isEmpty()) {
            backupFileNameField.setStyle(invalidFieldStyle);
            return;
        } else {
            backupFileNameField.setStyle(defaultFieldStyle);
        }

        File backupFile = new File(currentFileTreeLocation, backupFileNameField.getText() + ".zip");
        boolean isSaved = backupHandler.saveBackup(backupFile);
        refreshFileTreeView();

        if (isSaved) {
            backupSaveStatusLabel.setText("Backup saved successfully!");
        } else {
            backupSaveStatusLabel.setText("An error occurred while trying to save backup!");
        }
    }

    @FXML
    private void onSaveAsButtonPressed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ZIP Archive", "*.zip")
        );
        fileChooser.setInitialDirectory(backupDirectory);
        fileChooser.setInitialFileName("Test");
        File selectedFile = fileChooser.showSaveDialog(AppData.getInstance().getStage());

        if (selectedFile == null) {
            return;
        }

        currentFileTreeLocation = selectedFile.getParentFile();
        boolean isSaved = backupHandler.saveBackup(selectedFile);
        refreshFileTreeView();

        if (isSaved) {
            backupSaveStatusLabel.setText("Backup saved successfully!");
        } else {
            backupSaveStatusLabel.setText("An error occurred while trying to save backup!");
        }
    }

    @FXML
    private void onOpenButtonPressed() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Directory");
        directoryChooser.setInitialDirectory(currentFileTreeLocation);
        File selectedDirectory = directoryChooser.showDialog(AppData.getInstance().getStage());

        if (selectedDirectory != null) {
            currentFileTreeLocation = selectedDirectory;
        }
        refreshFileTreeView();
    }

    @FXML
    private void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onSaveButtonPressed();
        }
    }

    @FXML
    private void onBackupSelected() {
        if (backupFileTreeView.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        File backup = backupFileTreeView.getSelectionModel().getSelectedItem().getValue();

        if (backup.isDirectory()) {
            clearBackupTable();
            backupTableVBox.setDisable(true);
            return;
        }

        fillBackupTable(backup);
        backupTableVBox.setDisable(false);
        undoLoadBackupButton.setVisible(true);
    }

    @FXML
    private void onDeleteButtonPressed() {
        File backup = backupFileTreeView.getSelectionModel().getSelectedItem().getValue();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wasp/pages/" + "password_barrier_dialog.fxml"));
            loader.load();

            PasswordBarrierDialogController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.showDialog(AppData.getInstance().getStage().getScene());

            if (controller.isValidated()) {
                boolean isDeleted = backupHandler.deleteBackup(backup);
                refreshFileTreeView();

                if (isDeleted) {
                    backupManageStatusLabel.setText("Backup deleted successfully!");
                } else {
                    backupManageStatusLabel.setText("An error occurred while trying to delete backup!");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onLoadBackupButtonPressed() {
        boolean isLoaded = backupHandler.loadBackup(backupFileTreeView.getSelectionModel().getSelectedItem().getValue());

        if (isLoaded) {
            backupManageStatusLabel.setText("Backup loaded successfully!");

            CsvHandler<AccountData> csvHandler = new CsvHandler<>(AppData.getInstance().getAccountDataFile(), AccountData.class);
            List<AccountData> accountDataList = csvHandler.getCsvList();
            AppData.getInstance().setAccountDataList(accountDataList);

            undoLoadBackupButton.setDisable(false);
            refreshFileTreeView();
        } else {
            backupManageStatusLabel.setText("An error occurred while trying to load backup!");
        }
    }

    @FXML
    private void onUndoLoadBackupButtonPressed() {
        boolean isUndone = backupHandler.undoLoadBackup();

        if (isUndone) {
            backupManageStatusLabel.setText("Backup loading undone!");
            refreshFileTreeView();
        } else {
            backupManageStatusLabel.setText("An error occurred while trying to undo the backup loading!");
        }

        undoLoadBackupButton.setDisable(true);
    }

    private void clearBackupTable() {
        backupNameLabel.setText("");
        backupLocationLabel.setText("");
        backupDateLabel.setText("");
    }

    private void fillBackupTable(File backup) {
        backupNameLabel.setText(backup.getName());
        backupLocationLabel.setText(backup.getAbsolutePath());

        long lastModifiedMillis = backup.lastModified();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date lastModifiedDate = new Date(lastModifiedMillis);
        String formattedDate = sdf.format(lastModifiedDate);

        backupDateLabel.setText(formattedDate);
    }

    private void refreshFileTreeView() {
        initializeFileTreeView(currentFileTreeLocation);
    }
}
