package com.example.bookrentalsystemforcsumblibrary;

public class Resrvation {


    private String username;
    private String book;
    private String pickupTime;
    private String returnTime;
    private String reservationNum;
    private double total;

    public Resrvation(String username, String book, String pickupTime, String returnTime, String reservationNum, double total) {

        this.username = username;
        this.book = book;
        this.pickupTime = pickupTime;
        this.returnTime = returnTime;
        this.reservationNum = reservationNum;
        this.total = total;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getReservationNum() {
        return reservationNum;
    }

    public void setReservationNum(String reservationNum) {
        this.reservationNum = reservationNum;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double time) {
        this.total = time;
    }

}
