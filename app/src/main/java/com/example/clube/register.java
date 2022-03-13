package com.example.clube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register extends AppCompatActivity {
    EditText  firstname,lastname,email,password;
    Button  register;
    TextView text_login;
    FirebaseAuth auth;
    DatabaseReference Reference;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.registred_button);
        text_login=findViewById(R.id.textLogin);


        auth=FirebaseAuth.getInstance();
        text_login.setOnClickListener(new View.OnClickListener() {
        @Override
       public void onClick(View view) {
               startActivity(new Intent(register.this,login.class));
           }
       });
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               pd=new ProgressDialog(register.this);
               pd.setMessage("Please white ...");
               pd.show();
               String firstnametext=firstname.getText().toString();
               String lastnametext=lastname.getText().toString();
              String emailtext=email.getText().toString();
             String passwordtext=password.getText().toString();
              if(firstnametext.isEmpty()||lastnametext.isEmpty()||emailtext.isEmpty()||passwordtext.isEmpty()){
                  Toast.makeText(register.this, "plz complete all chams", Toast.LENGTH_SHORT).show();
              }
             else{


                   register(firstnametext,lastnametext,emailtext,passwordtext);
               }

           }
       });


    }
    public void register(String firstname,String lastname,String email,String password){
           auth.createUserWithEmailAndPassword(email,password).
           addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            FirebaseUser firebaseUser=auth.getCurrentUser();
            String userId=firebaseUser.getUid();
            Reference=FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        //    HashMap<String,Object> hashMap=new HashMap<>();
            User user=new User(userId,firstname,lastname,email,password);
//            hashMap.put("id",userId);
//            hashMap.put("firstname",firstname);
//            hashMap.put("lastname",lastname);
//            hashMap.put("email",email);
//            hashMap.put("password",password);
//            hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/clube-8d72e.appspot.com/o/ajax.png?alt=media&token=e0ded851-240e-4cd2-885f-590449ec4983");
          Reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){


                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                     pd.dismiss();
                     Intent i=new Intent(register.this, start.class);
                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(i);
                    }
                }
            });



        }
        else{
            pd.dismiss();
            Toast.makeText(register.this, "you cant' registed with this email or passsword", Toast.LENGTH_SHORT).show();


        }
    }
});

    }
}