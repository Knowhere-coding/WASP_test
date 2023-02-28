package com.wasp.handler;

import com.wasp.data.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvHandler {
    private final File csvFile;

    public CsvHandler(File csvFile) {
        this.csvFile = csvFile;
    }

    public List<Entry> getCsvList(String value, int option) {
        List<Entry> entries = new ArrayList<>();
        String line;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
            csvReader.readLine();
            while ((line = csvReader.readLine()) != null) {
                if (value.isEmpty()) {
                    entries.add(new Entry(line.split(",")));
                } else if (option == 0 && line.toLowerCase().contains(value.toLowerCase())) {
                    entries.add(new Entry(line.split(",")));
                } else if (option != 0 && line.split(",")[option-1].toLowerCase().contains(value.toLowerCase())){
                    entries.add(new Entry(line.split(",")));
                }
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public List<Entry> getCsvList() {
        return getCsvList("", 0);
    }

    public String[] getSearchOptions() {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
            return csvReader.readLine().split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Entry> getObservableTableList(List<Entry> csvEntries) {
        ObservableList<Entry> entries = FXCollections.observableArrayList();
        csvEntries.forEach(entry -> entry.setPassword(entry.getPassword().replaceAll(".", "*")));
        entries.addAll(csvEntries);
        return entries;
    }

    public ObservableList<String> getObservableSearchOptionsList(String[] searchOptions) {
        ObservableList<String> entries = FXCollections.observableArrayList();
        entries.add(0, "all");
        entries.addAll(Arrays.asList(searchOptions));
        return entries;
    }
}
