package com.example.dto;

import com.example.entity.City;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "cities")
public class Cities {

    List<City> cities = new LinkedList<>();

    @XmlElement(name = "city")
    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public Cities(List<City> cities) {
        this.cities = cities;
    }

    public Cities() {
    }
}
