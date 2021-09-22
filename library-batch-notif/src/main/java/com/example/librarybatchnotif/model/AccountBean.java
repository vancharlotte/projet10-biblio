package com.example.librarybatchnotif.model;

public class AccountBean {

    private Integer id;
    private String email;

    public AccountBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
