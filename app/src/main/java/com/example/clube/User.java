package com.example.clube;

import com.google.android.gms.tasks.OnCompleteListener;

import java.util.HashMap;

public class User {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String imageurl = "https://firebasestorage.googleapis.com/v0/b/clube-8d72e.appspot.com/o/ajax.png?alt=media&token=e0ded851-240e-4cd2-885f-590449ec4983";

    public User(String id,String firstname, String lastname, String email, String password) {
        this.id=id;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.password=password;
  }

    public User() {

    }

    public String getid() {
        return id;
    }
    public String getfirstname() {
        return firstname;
    }
    public String getlastname() {
        return lastname;
    }
    public String getemail() {
        return email;
    }
    public String getpassword() {
        return password;
    }
    public String getimageurl() {
        return imageurl;
    }



}
