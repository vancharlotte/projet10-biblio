package com.example.libraryloan.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Loan {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private int copy;

    @NotNull
    private int user;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    private boolean returned;

    private boolean renewed;

    public Loan() {
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
