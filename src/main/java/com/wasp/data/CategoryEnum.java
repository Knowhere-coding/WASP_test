package com.wasp.data;

import java.util.ArrayList;
import java.util.List;

public enum CategoryEnum {
    EMAIL("email"),
    SOCIAL_MEDIA("social_media"),
    GAMING("gaming"),
    CODING("coding"),
    SHOPPING("shopping"),
    BANKING("banking"),
    EDUCATION("education"),
    PRIVATE("private"),
    OTHER("other");

    private final String label;

    CategoryEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static List<String> getAllLabels() {
        List<String> labels = new ArrayList<>();
        for (CategoryEnum f : CategoryEnum.values()) {
            labels.add(f.label);
        }
        return labels;
    }

    public static CategoryEnum fromLabel(String label) {
        for (CategoryEnum f : CategoryEnum.values()) {
            if (f.label.equalsIgnoreCase(label)) {
                return f;
            }
        }
        throw new IllegalArgumentException("Invalid expiration label: " + label);
    }
}

