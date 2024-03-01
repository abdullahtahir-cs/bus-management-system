package com.example.smdfinalproject;

public class MyBus {
    String NumberPlate, From, To, Price, AvailableSeats;

    public MyBus(String numberPlate, String from, String to, String price, String seats) {
        NumberPlate = numberPlate;
        From = from;
        To = to;
        Price = price;
        AvailableSeats = seats;
    }

    public String getNumberPlate() {
        return NumberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        NumberPlate = numberPlate;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getAvailableSeats() {
        return AvailableSeats;
    }

    public void setAvailableSeats(String availableSeats) {
        AvailableSeats = availableSeats;
    }
}
