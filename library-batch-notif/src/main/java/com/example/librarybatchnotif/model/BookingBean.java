package com.example.librarybatchnotif.model;

import java.util.Date;

public class BookingBean {

    private int id;
    private int book;
    private int user;
    private String userEmail;
    private Date notifDate;
    private boolean expired;


    public BookingBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBook() {
        return book;
    }

    public void setBook(int book) {
        this.book = book;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getNotifDate() {
        return notifDate;
    }

    public void setNotifDate(Date notifDate) {
        this.notifDate = notifDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "BookingBean{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                ", userEmail='" + userEmail + '\'' +
                ", notifDate=" + notifDate +
                ", expired=" + expired +
                '}';
    }
}
