package com.example.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "distance")
public class Distance {
    private City fromCity;
    private City toCity;
    private Double distance;

    public City getFromCity() {
        return fromCity;
    }

    @XmlElement
    public void setFromCity(City fromCity) {
        this.fromCity = fromCity;
    }

    public City getToCity() {
        return toCity;
    }

    @XmlElement
    public void setToCity(City toCity) {
        this.toCity = toCity;
    }

    public Double getDistance() {
        return distance;
    }

    @XmlAttribute(name = "value")
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Distance(City fromCity, City toCity, Double distance) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj instanceof Distance){
            Distance distance = (Distance) obj;
            if(!distance.getFromCity().equals(this.getFromCity())){
                return false;
            } else if(!distance.getToCity().equals(this.getToCity())){
                return false;
            } else if(distance.getDistance() != this.getDistance()){
                return false;
            }
            return true;
        }
        return false;
    }

    public Distance() {
    }
}