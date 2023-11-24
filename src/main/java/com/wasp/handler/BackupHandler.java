package com.wasp.handler;

import com.wasp.data.AccountData;
import com.wasp.data.AppData;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class BackupHandler {
    private List<AccountData> undoBackupData;

    public boolean saveBackup(File backupFile) {
        try (FileOutputStream fos = new FileOutputStream(backupFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            File accountDataFile = AppData.getInstance().getAccountDataFile();
            File masterAccountDataFile = AppData.getInstance().getMasterAccountDataFile();

            byte[] buffer = new byte[1024];

            for (File file : new File[]{accountDataFile, masterAccountDataFile}) {
                FileInputStream fis = new FileInputStream(file);

                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);

                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                fis.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public boolean loadBackup(File backupFile) {
        if (undoBackupData == null) {
            undoBackupData = AppData.getInstance().getAccountDataList();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
//            String backupFileName = dateFormat.format(new Date()) + "_backup.zip";
//            undoBackupFile = new File(Objects.requireNonNull(BackupHandler.class.getResource("/com/wasp/backup/")).getFile(), backupFileName);
//            saveBackup(undoBackupFile);
        }

        File accountDataFile = AppData.getInstance().getAccountDataFile();

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(backupFile))) {
            byte[] buffer = new byte[1024];

            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String fileName = zipEntry.getName();

                if ((accountDataFile.getName() + ".enc").equals(fileName) || accountDataFile.getName().equals(fileName)) {
                    FileOutputStream fos = new FileOutputStream(accountDataFile);
                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                    fos.close();
                }

                zipEntry = zis.getNextEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public boolean undoLoadBackup() {
        AppData.getInstance().setAccountDataList(undoBackupData);

        CsvHandler<AccountData> csvHandler = new CsvHandler<>(AppData.getInstance().getAccountDataFile(), AccountData.class);
        csvHandler.updateEntries(AppData.getInstance().getAccountDataList());

        return true;
    }

    public boolean deleteBackup(File backupFile) {
        return backupFile.delete();
    }
}
