package com.example.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="city")
public class City {
    private String name;
    private Double latitude;
    private Double longitude;

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    @XmlElement
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @XmlElement
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj instanceof City){
            City city = (City) obj;
            if(!city.getName().equals(this.getName())){
                return false;
            } else if(!city.getLatitude().equals(this.getLatitude())){
                return false;
            } else if(!city.getLongitude().equals(this.getLongitude())){
                return false;
            }
            return true;
        }
        return false;
    }

    public City(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public City(String name){
        this.name = name;
    }

    public City() {
    }
}
