package com.example.clientui.beans;

import java.util.Date;

public class LoanInformation {


    private int loanId;
    private String userName;
    private String bookTitle;
    private Date startDate;
    private Date endDate;
    private boolean returned;
    private boolean renewed;
    private boolean late;



    public LoanInformation() {
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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

    public boolean isLate() {
        return late;
    }

    public void setLate(boolean late) {
        this.late = late;
    }

    @Override
    public String toString() {
        return "LoanInformation{" +
                "loanId=" + loanId +
                ", userName='" + userName + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", returned=" + returned +
                ", renewed=" + renewed +
                ", late=" + late +

                '}';
    }
}