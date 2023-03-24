package com.wasp.data;

import java.util.ArrayList;
import java.util.List;

public enum ExpirationEnum {
    NEVER("never", 0),
    DAILY("daily", 1),
    WEEKLY("weekly", 7),
    MONTHLY("monthly", 30),
    QUARTERLY("quarterly", 90),
    HALF_YEARLY("half-yearly", 180),
    YEARLY("yearly", 365);

    private final String label;
    private final int value;

    ExpirationEnum(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    public static List<String> getAllLabels() {
        List<String> labels = new ArrayList<>();
        for (ExpirationEnum f : ExpirationEnum.values()) {
            labels.add(f.label);
        }
        return labels;
    }

    public static ExpirationEnum fromLabel(String label) {
        for (ExpirationEnum f : ExpirationEnum.values()) {
            if (f.label.equalsIgnoreCase(label)) {
                return f;
            }
        }
        throw new IllegalArgumentException("Invalid expiration label: " + label);
    }

    public static ExpirationEnum fromValue(int value) {
        for (ExpirationEnum f : ExpirationEnum.values()) {
            if (f.value == value) {
                return f;
            }
        }
        throw new IllegalArgumentException("Invalid expiration value: " + value);
    }
}

