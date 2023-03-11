package com.wasp.handler;

import com.wasp.data.Entry;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvHandler {
    private final File csvFile;

    public CsvHandler(File csvFile) {
        this.csvFile = csvFile;
    }

    public List<Entry> getCsvList(String value, int option, boolean hiddenPassword) {
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

        if (hiddenPassword) {
            entries.forEach(entry -> entry.setPassword(entry.getPassword().replaceAll(".", "*")));
        }

        return entries;
    }

    public List<Entry> getCsvList() {
        return getCsvList("", 0, true);
    }

    public List<Entry> getCsvList(boolean hiddenPassword) {
        return getCsvList("", 0, hiddenPassword);
    }

    public List<String> getHeader() {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
            return new ArrayList<>(Arrays.asList(csvReader.readLine().split(",")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveNewEntry(Entry entry) {
        try {
            FileWriter fileWriter = new FileWriter(csvFile, true);
            BufferedWriter csvWriter = new BufferedWriter(fileWriter);

            csvWriter.write(entry.getCsvEntry());
            csvWriter.newLine();

            csvWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNextIndex() {
        try {
            RandomAccessFile raf = new RandomAccessFile(csvFile, "r");

            StringBuilder sb = new StringBuilder();

            for (long pointer = csvFile.length() - 1; pointer >= 0; pointer--) {
                raf.seek(pointer);

                char c = (char) raf.read();
                if (c == '\n' && pointer != csvFile.length() - 1) {
                    break;
                }
                sb.append(c);
            }
            raf.close();

            sb.reverse();
            String[] lastCsvEntry = sb.toString().split(",");
            return Integer.parseInt(lastCsvEntry[0]) + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
