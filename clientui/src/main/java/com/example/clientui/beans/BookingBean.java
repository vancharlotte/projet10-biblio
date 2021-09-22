package com.example.clientui.beans;

import java.util.Date;

public class BookingBean {

    private int id;
    private int user;
    private int book;
    private Date startDate;
    private Date notifDate;

    public BookingBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getBook() {
        return book;
    }

    public void setBook(int book) {
        this.book = book;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getNotifDate() {
        return notifDate;
    }

    public void setNotifDate(Date notifDate) {
        this.notifDate = notifDate;
    }

    @Override
    public String toString() {
        return "BookingBean{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", startDate=" + startDate +
                ", notifDate=" + notifDate +
                '}';
    }
}
