package com.example.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class RequestDistanceCalculate implements Serializable {
    private CalculationTypes type;
    private String fromcity;
    private String tocity;

    public CalculationTypes getType() {
        return type;
    }

    public void setType(CalculationTypes type) {
        this.type = type;
    }

    public String getFromcity() {
        return fromcity;
    }

    public void setFromcity(String fromcity) {
        this.fromcity = fromcity;
    }

    public String getTocity() {
        return tocity;
    }

    public void setTocity(String tocity) {
        this.tocity = tocity;
    }

    public RequestDistanceCalculate(CalculationTypes type, String fromcity, String tocity) {
        this.type = type;
        this.fromcity = fromcity;
        this.tocity = tocity;
    }

    public RequestDistanceCalculate() {
    }
}