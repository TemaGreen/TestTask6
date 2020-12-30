package com.example.dto;

import java.io.Serializable;

public enum CalculationTypes implements Serializable {
    All("All"),
    Crowflight("Crowflight"),
    DistanceMatrix("DistancMatrix");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    CalculationTypes(String value) {
        this.value = value;
    }

    CalculationTypes() {
    }
}
