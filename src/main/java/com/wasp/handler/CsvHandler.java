package com.wasp.handler;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvValidationException;
import com.wasp.data.BaseData;

import java.io.*;
import java.util.*;

public class CsvHandler<T extends BaseData> {
    private final Class<T> clazz;
    private final File csvFile;

    public CsvHandler(File csvFile, Class<T> clazz) {
        this.clazz = clazz;
        this.csvFile = csvFile;
    }

    public List<T> getCsvList() {
        try {
            CsvToBeanBuilder<T> csvBeanReader = new CsvToBeanBuilder<T>(new FileReader(csvFile))
                    .withType(clazz);
            CsvToBean<T> csvToBean = csvBeanReader.build();
            return csvToBean.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getCsvHeader() {
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            return new ArrayList<>(Arrays.asList(csvReader.readNext()));
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addEntry(T entry) {
        try (FileWriter fileWriter = new FileWriter(csvFile, true);
             CSVWriter csvWriter = new CSVWriter(fileWriter)) {
            String[] nextLine = entry.getValues();
            csvWriter.writeNext(nextLine, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> getTableEntries(String searchString, int option, boolean isPasswordHidden) {
        List<T> entries = getCsvList();
        List<T> filteredEntries = new ArrayList<>();

        for (T entry : entries) {
            String[] entryValues = entry.getValues();
            String entryValuesStr = String.join(",", entryValues);

            if (isPasswordHidden) {
                entry.hidePassword();
            }

            if (searchString.isEmpty()) {
                filteredEntries.add(entry);
            } else if (option == 0 && entryValuesStr.contains(searchString.toLowerCase())) {
                filteredEntries.add(entry);
            } else if (option != 0 && entryValues[option-1].toLowerCase().contains(searchString.toLowerCase())) {
                filteredEntries.add(entry);
            }
        }
        return filteredEntries;
    }

    public List<T> getTableEntries(String searchString, int option) {
        return getTableEntries(searchString, option, true);
    }

    public List<T> getTableEntries() {
        return getTableEntries("", 0, true);
    }

    public int getNextIndex() {
        List<T> entries = getCsvList();
        return Integer.parseInt(entries.get(entries.size()-1).getValues()[0]) + 1;
    }
}
