package com.example.clube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import Adapter.GoupAdapter;
import model.Groups;

public class add_message extends AppCompatActivity {
    private TextView sendMessage;
    private EditText userMessageInput;

    private RecyclerView recyclerView;
    private GoupAdapter groupAdapter;
    private List<Groups> groupLists;
    private DatabaseReference usersRefs, groupNameRef, groupMessageKeyRef;
    private FirebaseAuth mAuth;
    private String currentGroupName, currentUserid, currentUserName, currentDate, currentTime;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);
        mAuth = FirebaseAuth.getInstance();
        currentUserid = mAuth.getCurrentUser().getUid();
        usersRefs = FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView=findViewById(R.id.recyclerViewgroup);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        groupLists=new ArrayList<>();
        groupAdapter=new GoupAdapter(getApplicationContext(),groupLists);
        recyclerView.setAdapter(groupAdapter);
        mAuth = FirebaseAuth.getInstance();

        currentUserid = mAuth.getCurrentUser().getUid();
        usersRefs = FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView.setAdapter(groupAdapter);
        initializeB();
        getuserinfo();
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "you click hhhhhh", Toast.LENGTH_SHORT).show();
                GetUserInfo();
            }
        });

    }


    private void initializeB() {

        sendMessage = findViewById(R.id.send_msg);
        userMessageInput = findViewById(R.id.add_message);

    }

    private void GetUserInfo() {
        usersRefs.child(currentUserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentUserName = snapshot.child("firstname").getValue().toString();
                    currentGroupName = snapshot.child("groupname").getValue().toString();
                    saveMsgToDB(currentGroupName);
                    userMessageInput.setText("");
                    
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void saveMsgToDB(String currentGroup) {

      //a chercher
        groupNameRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroup);

        String message = userMessageInput.getText().toString();
        String messageKey = groupNameRef.push().getKey();
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Please type in the field", Toast.LENGTH_SHORT).show();
        } else {
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd,yyyy");
            currentDate = currentDateFormat.format(calForDate.getTime());
            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentDateTime = new SimpleDateFormat("hh:mm");
            currentTime = currentDateTime.format(calForTime.getTime());
            HashMap<String, Object> groupMessageKey = new HashMap<>();
            groupNameRef.updateChildren(groupMessageKey);
            groupMessageKeyRef = groupNameRef.child(messageKey);
            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("name", currentUserName);
            messageInfoMap.put("message", message);
            messageInfoMap.put("date", currentDate);
            messageInfoMap.put("time", currentTime);
            messageInfoMap.put("from", currentUserid);
            messageInfoMap.put("order", -1*(System.currentTimeMillis()/1000));
            groupMessageKeyRef.updateChildren(messageInfoMap);
        }
    }

    public void readpost(String currentGroupName){
        groupNameRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroupName);
        Query qyer=groupNameRef.orderByChild("order").limitToLast(100);

        qyer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupLists.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    String name=snapshot1.child("name").getValue(String.class);
                    String message=snapshot1.child("message").getValue(String.class);
                    String date=snapshot1.child("date").getValue(String.class);
                    String time=snapshot1.child("time").getValue(String.class);
                    String from=snapshot1.child("from").getValue(String.class);

                    int order=snapshot1.child("order").getValue(int.class);
                    Groups group=new Groups(message,date,time,name,from,order);                    groupLists.add(group);

                }
                groupAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getuserinfo(){
        usersRefs.child(currentUserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentGroupName = snapshot.child("groupName").getValue().toString();
                    readpost(currentGroupName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}