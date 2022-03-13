package com.example.clube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class start extends AppCompatActivity {
//     Button login,register;
//    FirebaseUser firebaseUser;
Button login,register;
    FirebaseUser firebaseUser;

    @Override
    protected void  onStart() {
        super.onStart();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
//
            startActivity(new Intent(start.this,Mainall.class));
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.start_register);
        register=findViewById(R.id.start_login);
    }
    public void register(View view) {

        Intent registerpage=new Intent(start.this,register.class);
        startActivity(registerpage);
    }


    public void login(View view) {
        Intent loginpage=new Intent(start.this,login.class);
        startActivity(loginpage);
    }



}