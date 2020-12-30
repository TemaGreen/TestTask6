package com.example.component.calculator;

import com.example.entity.City;
import com.example.exception.NoWayToCity;

public interface Calculator {
    double calculateDistance(City fromCity, City toCity) throws NoWayToCity;
}
