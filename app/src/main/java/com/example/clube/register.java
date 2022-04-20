package com.example.clube;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class register extends AppCompatActivity {
    EditText firstname, lastname, email, password;
    Button register;
    TextView text_login;
    FirebaseAuth auth;
    DatabaseReference Reference;
    ProgressDialog pd;
    private DatabaseReference groupname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.registred_button);

        option();
        auth = FirebaseAuth.getInstance();
        option();

        Spinner dropdown = findViewById(R.id.group);
        ArrayList<String> items = new ArrayList<String>();
        items.add("Dev");
        items.add("Reseau");
        items.add("Art");
        ArrayAdapter<String> adap = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                items);
        dropdown.setAdapter(adap);

        Spinner dropdown2 = findViewById(R.id.type);
        ArrayList<String> items1 = new ArrayList<String>();
        items1.add("Leader");
        items1.add("Member");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                items1);
        dropdown2.setAdapter(adapter);


        register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(register.this);
                pd.setMessage("Please Wait ...");
                pd.show();
                String firstnametext = firstname.getText().toString();
                String lastnametext = lastname.getText().toString();
                String emailtext = email.getText().toString();
                String passwordtext = password.getText().toString();
                String group = dropdown.getSelectedItem().toString();
                String type = dropdown2.getSelectedItem().toString();
                if (firstnametext.isEmpty() || lastnametext.isEmpty() || emailtext.isEmpty() || passwordtext.isEmpty() || type.isEmpty() || group.isEmpty()) {
                    Toast.makeText(register.this, "a field is empty", Toast.LENGTH_SHORT).show();
                } else {


                    register(firstnametext, lastnametext, emailtext, passwordtext, type, group);
                }

            }
        });


    }

    public void register(String firstname, String lastname, String email, String password
            , String typeUser, String group) {
        Log.d(TAG, "the user type " + typeUser + "the group aff " + group);
        auth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();
                            Reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                            //    HashMap<String,Object> hashMap=new HashMap<>();
                            User user = new User(userId, firstname, lastname, email, password, typeUser, group);
//            hashMap.put("id",userId);
//            hashMap.put("firstname",firstname);
//            hashMap.put("lastname",lastname);
//            hashMap.put("email",email);
//            hashMap.put("password",password);
//            hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/clube-8d72e.appspot.com/o/ajax.png?alt=media&token=e0ded851-240e-4cd2-885f-590449ec4983");
                            Reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {


                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        pd.dismiss();
                                        Intent i = new Intent(register.this, start.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                    }
                                }
                            });


                        } else {
                            pd.dismiss();
                            Toast.makeText(register.this, "you cant' registed with this email or passsword", Toast.LENGTH_SHORT).show();


                        }
                    }
                });

    }

    public String[] option() {
        groupname = FirebaseDatabase.getInstance().getReference("Groups");
        String[] gn = new String[20];

        groupname.addChildEventListener(new ChildEventListener() {
            int i = 0;

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    GroupNames groupList = snapshot1.getValue(GroupNames.class);
                    String val = String.valueOf(snapshot1.getKey());
                    Toast.makeText(getApplicationContext(), "The value of : "+val , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return gn;
    }
}