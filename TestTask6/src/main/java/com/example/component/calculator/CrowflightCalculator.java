package com.example.component.calculator;

import com.example.component.calculator.Calculator;
import com.example.entity.City;

public class CrowflightCalculator implements Calculator {

    //примерный радиус Земли
    private static final int RAD = 6371000;

    @Override
    public double calculateDistance(City fromCity, City toCity) {
        return calculateDistance(fromCity.getLatitude(), fromCity.getLongitude(), toCity.getLatitude(), toCity.getLongitude());
    }

    public static double calculateDistance(double latitude1, double longitude1,
                                           double latitude2, double longitude2) {

        double lat1 = convertRadian(latitude1);
        double long1 = convertRadian(longitude1);
        double lat2 = convertRadian(latitude2);
        double long2 = convertRadian(longitude2);

        double clat1 = Math.cos(lat1);
        double clat2 = Math.cos(lat2);
        double slat1 = Math.sin(lat1);
        double slat2 = Math.sin(lat2);

        double delta = Math.abs(long2 - long1);
        double cdelta = Math.cos(delta);
        double sdelta = Math.sin(delta);

        double y = Math.sqrt(
                Math.pow((clat2 * sdelta), 2) +
                        Math.pow((clat1 * slat2 - slat1 * clat2 * cdelta), 2)
        );
        double x = (slat1 * slat2) + (clat1 * clat2 * cdelta);

        double ad = Math.atan2(y,x);


        return ad * RAD;
    }

    private static double convertRadian(double value) {
        return (value * Math.PI) / 180;
    }
}