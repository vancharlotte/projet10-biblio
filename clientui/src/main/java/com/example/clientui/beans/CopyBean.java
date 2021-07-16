package com.example.clientui.beans;

public class CopyBean {

    private int id;
    private int book;

    public CopyBean() {
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

    @Override
    public String toString() {
        return "Copy{" +
                "id=" + id +
                ", book=" + book +
                '}';
    }
}
