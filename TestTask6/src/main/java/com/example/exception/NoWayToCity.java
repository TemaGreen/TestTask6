package com.example.exception;

public class NoWayToCity extends Exception{

    private String c1;
    private String c2;

    @Override
    public String toString() {
        if(c1 != null && c2 != null) {
            return "Путь между " + c1 + " и " + c2 + "не найден";
        }
        return "Путь не найден";
    }

    public NoWayToCity(String c1, String c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public NoWayToCity(String message) {
        super(message);
    }

    public NoWayToCity() {
    }
}
