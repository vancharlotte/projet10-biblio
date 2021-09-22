package com.example.librarybatchnotif.model;

import java.util.Date;

public class LoanBean {

    private int id;
    private int copy;
    private int user;
    private String userEmail;
    private Date startDate;
    private Date endDate;
    private boolean returned;
    private boolean renewed;

    public LoanBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCopy() {
        return copy;
    }

    public void setCopy(int copy) {
        this.copy = copy;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isRenewed() {
        return renewed;
    }

    public void setRenewed(boolean renewed) {
        this.renewed = renewed;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", copy=" + copy +
                ", user=" + user +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", returned=" + returned +
                ", renewed=" + renewed +
                '}';
    }
}
