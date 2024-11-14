package com.example.b07demosummer2024;

public class User {
    public String firstname;
    public String lastname;
    public String email;
    public String password;
    public User(){

    }
    public User(String fn, String ln, String e, String p){
        this.firstname = fn;
        this.lastname = ln;
        this.email = e;
        this.password = p;
    }
}
