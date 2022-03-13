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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    EditText firstname,lastname,email,password;
    Button login;
    TextView text_registred;
    FirebaseAuth auth;
    ProgressDialog pd;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        text_registred=findViewById(R.id.textregister);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login_button);
        auth=FirebaseAuth.getInstance();
        text_registred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,register.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd=new ProgressDialog(login.this);
                pd.setMessage("Please white ...");
                pd.show();

                String emailtext=email.getText().toString();
                String passwordtext=password.getText().toString();
                if(emailtext.isEmpty()||passwordtext.isEmpty()){
                    Toast.makeText(login.this, "all fileds all required", Toast.LENGTH_SHORT).show();
                }
                else{

         auth.signInWithEmailAndPassword(emailtext,passwordtext).
                 addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){

                             reference=FirebaseDatabase.getInstance().getReference().child("Users")
                             .child(auth.getCurrentUser().getUid());
                             reference.addValueEventListener(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                     pd.dismiss();
                                     Intent i=new Intent(login.this,MainActivity.class);
                                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                     startActivity(i);
                                     pd.dismiss();
                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError error) {
                                 pd.dismiss();
                                 }
                             });
                         }
                         else{

                             pd.dismiss();
                             Toast.makeText(login.this, "you cant' authentificated with this email or passsword", Toast.LENGTH_SHORT).show();

                         }

                     }
                 });



                }

   }
        });
    }

    public void register(View view) {

        Intent registerpage=new Intent(login.this,register.class);
        startActivity(registerpage);

    }

}