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
            CsvToBeanBuilder<T> csvBeanReader = new CsvToBeanBuilder<T>(new FileReader(csvFile)).withType(clazz);
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

    public void updateEntries(List<T> entries) {
        String[] header = getCsvHeader().toArray(new String[0]);

        try (FileWriter fileWriter = new FileWriter(csvFile);
            CSVWriter csvWriter = new CSVWriter(fileWriter)) {
            csvWriter.writeNext(header, false);
            entries.forEach(entry -> csvWriter.writeNext(entry.getValues(), false));
        } catch (IOException e) {
            e.printStackTrace();
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
}
