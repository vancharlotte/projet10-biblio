package com.example.clientui.beans;

import java.util.Date;

public class BookingInformation {

    private String user;
    private String book;
    private Date returnDate;
    private int nbRank;

    public BookingInformation() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getNbRank() {
        return nbRank;
    }

    public void setNbRank(int nbRank) {
        this.nbRank = nbRank;
    }

    @Override
    public String toString() {
        return "BookingInformation{" +
                "user=" + user +
                ", book=" + book +
                ", returnDate=" + returnDate +
                ", nbRank=" + nbRank +
                '}';
    }
}
