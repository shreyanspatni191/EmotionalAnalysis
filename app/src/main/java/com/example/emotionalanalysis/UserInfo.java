package com.example.emotionalanalysis;

import android.widget.EditText;

public class UserInfo {
    private String Username, Email, Password, Firstname, Middlename, Lastname;

    public UserInfo(String username, String email, String password, String firstname, String middlename, String lastname) {
        Username = username;
        Email = email;
        Password = password;
        Firstname = firstname;
        Middlename = middlename;
        Lastname = lastname;
    }

    public UserInfo() {
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail(){
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getMiddlename() {
        return Middlename;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setEmail(String email){
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public void setMiddlename(String middlename) {
        Middlename = middlename;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }
}
