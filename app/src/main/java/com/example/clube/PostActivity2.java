package com.example.clube;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import java.util.HashMap;

public class PostActivity2 extends AppCompatActivity {

    ImageView postclose,postimage;
    TextView poster;
    EditText postdescription;

    Uri imageuri;
    String myuri;
    StorageTask uploadeTask;
    StorageReference storagereference;


    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);

        postclose=findViewById(R.id.postclose);
        postimage=findViewById(R.id.postimage);
        poster=findViewById(R.id.poster);
        postdescription=findViewById(R.id.postdescription);

        StorageReference sr= FirebaseStorage.getInstance().getReference("posts");

        postclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostActivity2.this,MainActivity.class));
            }
        });
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
        CropImage.activity().setAspectRatio(1,1).start(PostActivity2.this);

    }
    private String getFileExtention(Uri uri){

        ContentResolver contentResoulver=getContentResolver();
        MimeTypeMap mim=MimeTypeMap.getSingleton();


        return mim.getExtensionFromMimeType(contentResoulver.getType(uri)) ;
    }
    private void uploadImage() {
        ProgressDialog  pd=new ProgressDialog(PostActivity2.this);
        pd.setMessage("Posting ...");
        pd.show();

        if(imageuri!=null){
            StorageReference filerreference=storagereference.child(System.currentTimeMillis()+"."+getFileExtention(imageuri));
            uploadeTask=filerreference.putFile(imageuri);
            uploadeTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isComplete()){
                        throw task.getException();
                           }
                    return filerreference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloaduri=task.getResult();
                        myuri=downloaduri.toString();
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Posts");
                        String postid=databaseReference.push().getKey();
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("posteid",postid);
                        hashMap.put("postimage",myuri);
                        hashMap.put("description",postdescription.getText().toString());
                        hashMap.put("publither", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        databaseReference.child(postid).setValue(hashMap);
                        startActivity(new Intent(PostActivity2.this,MainActivity.class));
                        finish();
                         }else{

                        Toast.makeText(PostActivity2.this, "Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity2.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }else{

            Toast.makeText(PostActivity2.this, "NO image selected", Toast.LENGTH_SHORT).show();
       }
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result=CropImage.getActivityResult(data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE&&resultCode==RESULT_OK){
            imageuri=result.getUri();
            postimage.setImageURI(imageuri);
        }else{
            Toast.makeText(PostActivity2.this, "Something gone wrong", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(PostActivity2.this,MainActivity.class));
            finish();

        }
    }

}