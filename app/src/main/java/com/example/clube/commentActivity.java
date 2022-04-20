package com.example.clube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.CommentsAdapter;
import model.comments;

public class commentActivity extends AppCompatActivity {

   private  RecyclerView recyclerView;
   private CommentsAdapter commentAdapter;
   private List<comments> commentList;


    EditText addcomment;
    ImageView image_profile;
    TextView post;
    String postid;
    String publisherid;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Toolbar toolbar =findViewById(R.id. toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle ("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled (true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        recyclerView = findViewById (R.id.RecyclerView);
        recyclerView.setHasFixedSize (true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager ( this);
        recyclerView.setLayoutManager (linearLayoutManager);
        commentList = new ArrayList<>();
        commentAdapter = new CommentsAdapter(this, commentList);
        recyclerView. setAdapter (commentAdapter);



        addcomment = findViewById (R.id. add_comment);
        image_profile = findViewById (R.id. image_profile);
        post = findViewById (R.id.post);
        Intent intent = getIntent ();
        postid = intent.getStringExtra ( "postid");
        publisherid = intent.getStringExtra ( "publisherid");
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addcomment.getText ().toString ().equals ("")){
                    Toast.makeText ( commentActivity.this,"You can't send empty comment", Toast. LENGTH_SHORT).show ();
                } else {
                    addComment();
                }




            }
        });



getImage();
        readcomment();
}

private void addComment() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put ("comment", addcomment.getText().toString());
        hashMap.put ("publisher", firebaseUser.getUid());
        reference.push ().setValue (hashMap);
        addcomment.setText ("");

    }

    private  void getImage(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference ( "Users").child (firebaseUser.getUid ());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue (User.class);

                //Glide.with(getApplicationContext ()).load (user.getimageurl()).into (image_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
         }
        });


    }
        private void readcomment(){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Comments").child(postid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                commentList.clear ();
                for (DataSnapshot snapshot : datasnapshot.getChildren ()){
                    comments comment=snapshot.getValue(comments.class);
                    commentList.add(comment);
               }

                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}