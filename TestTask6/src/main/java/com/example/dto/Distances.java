package com.example.dto;

import com.example.entity.Distance;

import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "distances")
public class Distances {

    List<Distance> distances = new LinkedList<>();

    @XmlElement(name = "distance")
    public List<Distance> getDistances() {
        return distances;
    }

    public void setDistances(List<Distance> distances) {
        this.distances = distances;
    }

    public Distances(List<Distance> distances) {
        this.distances = distances;
    }

    public Distances() {
    }
}