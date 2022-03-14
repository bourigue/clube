package com.example.clube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

public class add_post extends AppCompatActivity {

        String Storage_Path = "All_Image_Uploads/";
        Button ChooseButton, UploadButton;
        EditText ImageName ;
        ImageView SelectImage;
        Uri FilePathUri;
        private String firebaseHomeUri;
       private StorageTask mUploadTask;
        DatabaseReference databaseReference;
        int Image_Request_Code = 7;
        ProgressDialog progressDialog ;
       StorageReference reference = FirebaseStorage.getInstance().getReference("Posts");
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_post);
            databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
            ChooseButton = (Button)findViewById(R.id.ButtonChooseImage);
            UploadButton = (Button)findViewById(R.id.ButtonUploadImage);
            ImageName = (EditText)findViewById(R.id.ImageNameEditText);
            SelectImage = (ImageView)findViewById(R.id.ShowImageView);
            progressDialog = new ProgressDialog(add_post.this);
            ChooseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

                }
            });


            UploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    uploadImageHomeToFirebase();
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

                FilePathUri = data.getData();

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                    SelectImage.setImageBitmap(bitmap);
                    ChooseButton.setText("Image Selected");

                }
                catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

        public String getFileExtension(Uri uri) {

            ContentResolver contentResolver = getContentResolver();

            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

            // Returning the file Extension.
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

        }

        public void UploadImageFileToFirebaseStorage() {


            if (FilePathUri != null) {
                StorageReference fileReference = reference.child(System.currentTimeMillis()
                        + "." + getFileExtension(FilePathUri));

                mUploadTask = fileReference.putFile(FilePathUri);
                mUploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.setProgress(0);
                                    }
                                }, 500);

                                Toast.makeText(add_post.this, "Upload successful", Toast.LENGTH_LONG).show();
                                Poste upload = new Poste(ImageName.getText().toString().trim(),
                                        taskSnapshot.getUploadSessionUri().toString(),"","");
                                String uploadId = databaseReference.push().getKey();
                                databaseReference.child(uploadId).setValue(upload);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(add_post.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setProgress((int) progress);
                                progressDialog.show();
                            }
                        });
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }


        }
    private void uploadImageHomeToFirebase() {

        StorageReference fileReference = reference.child(FilePathUri + "." + getFileExtension(FilePathUri));

        fileReference.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.setProgress(0);
                            }
                        }, 500);
                        String idpost=databaseReference.push().getKey();
                        String user=FirebaseAuth.getInstance().getUid();
                        firebaseHomeUri = uri.toString();
                        Poste upload = new Poste(ImageName.getText().toString().trim(),firebaseHomeUri,user,idpost);
                        String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(upload);
                     //   progressDialog.setVisibility(View.INVISIBLE);

                        Toast.makeText(add_post.this, "Uploaded Successfully 2", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setProgress((int) progress);
                progressDialog.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // progressBarUniversite.setVisibility(View.INVISIBLE);

                Toast.makeText(add_post.this, "Uploading Failed2 !!", Toast.LENGTH_SHORT).show();
            }
        });

    }



    }