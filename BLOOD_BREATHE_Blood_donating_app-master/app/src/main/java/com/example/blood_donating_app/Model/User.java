package com.example.blood_donating_app.Model;

public class User {
    String name,bloodgroup,id,mail,number,city;

    public User() {
    }

    public User(String name, String bloodgroup, String id, String mail, String number, String city) {
        this.name = name;
        this.bloodgroup = bloodgroup;
        this.id = id;
        this.mail = mail;
        this.number = number;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String email) {
        this.mail = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
