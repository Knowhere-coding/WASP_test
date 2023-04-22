package com.wasp.handler;

import com.wasp.data.AccountData;
import com.wasp.data.AppData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataHandler {

    public static List<AccountData> getTableEntries(String searchString, String option, boolean isPasswordHidden) {
        List<AccountData> entries = AppData.getInstance().getAccountDataList();
        List<AccountData> filteredEntries = new ArrayList<>();

        for (AccountData entry : entries) {
            Map<String, String> entryValues = entry.getMappedValues();
            String entryValuesStr = String.join(",", entry.getValues());

            if (isPasswordHidden) {
                entry.hidePassword();
            } else {
                entry.unhidePassword();
            }

            if (searchString.isEmpty()) {
                filteredEntries.add(entry);
            } else if (option.isEmpty() && entryValuesStr.contains(searchString.toLowerCase())) {
                filteredEntries.add(entry);
            } else if (!option.isEmpty() && entryValues.get(option).toLowerCase().contains(searchString.toLowerCase())) {
                filteredEntries.add(entry);
            }
        }
        return filteredEntries;
    }

    public static List<AccountData> getTableEntries(String searchString, String option) {
        return getTableEntries(searchString, option, true);
    }

    public static List<AccountData> getTableEntries() {
        return getTableEntries("", "", true);
    }

    public static int getNextAccountIndex() {
        List<AccountData> entries = AppData.getInstance().getAccountDataList();
        return Integer.parseInt(entries.get(entries.size()-1).getValues()[0]) + 1;
    }
}
