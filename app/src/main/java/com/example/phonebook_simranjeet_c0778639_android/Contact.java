package com.example.phonebook_simranjeet_c0778639_android;

public class Contact {

    int id;
    String fname,lname,email,phone,address;

    public Contact(int id, String fname, String lname, String email,String phone, String address){

        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.address = address;

    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }


    public String getEmail() { return email; }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
