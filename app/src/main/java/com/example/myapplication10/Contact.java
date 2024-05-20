package com.example.myapplication10;

public class Contact {
    private int id;
    private String name;
    private String phone;
    private String surname;
    private String sx;
    private String age;

    public Contact(int id, String name, String surname, String phone,  String age, String sx) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.age = age;
        this.sx = sx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSx() {
        return sx;
    }

    public void setSx(String sx) {
        this.sx = sx;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
