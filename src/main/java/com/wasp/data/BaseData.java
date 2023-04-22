package com.wasp.data;

import java.util.Map;

public interface BaseData {
    String[] getValues();
    Map<String, String> getMappedValues();
    void hidePassword();
    void unhidePassword();
}
