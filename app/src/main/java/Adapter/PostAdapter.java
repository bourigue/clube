package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clube.Poste;
import com.example.clube.R;
import com.example.clube.User;
import com.example.clube.commentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public Context mcontext;
    private List<Poste> mPost;

    public PostAdapter(Context context, List<Poste> posteLists) {
    this.mcontext=context;
    this.mPost=posteLists;
        
    }


   @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.post_item,parent,false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        FirebaseUser Firebaseuser=FirebaseAuth.getInstance().getCurrentUser();
        Poste post= mPost.get(i);
        holder.description.setText(post.getdescription());
       //Glide.with(mcontext).load(post.getImageURL()).into(holder.imagepost);
        Picasso.with(mcontext).load(post.getImageURL()).into(holder.imagepost);
       // publisherinfo(holder.usename,holder.publisher, post.getpublisher());
        islikes (post.getpostid(), holder.like);
        nrLikes(holder.likes,post.getpostid());
        getComments(post.getpostid(),holder.comment);


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.like.getTag().equals("like")){

                    FirebaseDatabase.getInstance().getReference ().child("likes").child (post.getpostid())
                            .child (Firebaseuser.getUid()).setValue(true);

                }else{

FirebaseDatabase.getInstance().getReference().
        child("likes").
        child(post.getpostid()).
        child(Firebaseuser.getUid()).
        removeValue();

                }
            }
        });


        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (mcontext, commentActivity.class);
                intent.putExtra ("postid", post.getpostid());
                intent.putExtra ("publisherid", post.getpublisher());
                mcontext.startActivity (intent);
            }
        });




    }

//    @Override
//    public void onBindViewHolder(@NonNull PostAdapter holder, int position) {
//
//    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imagepost,like,ic_comment;
        public TextView usename,publisher,likes,description,comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagepost=itemView.findViewById(R.id.poste_image);
            like=itemView.findViewById(R.id.like_post);
            usename=itemView.findViewById(R.id.publisher);
            likes=itemView.findViewById(R.id.likes);
            description=itemView.findViewById(R.id.description_post);
            comment=itemView.findViewById(R.id.comment);

            }
    }
    private void islikes (String postid, final ImageView imageView){
        final FirebaseUser firebaseUser =FirebaseAuth.getInstance ().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                .child("likes")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.child(firebaseUser.getUid()).exists()) {
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("liked");
                } else {
                    imageView.setImageResource(R.drawable.ic_favor);
                    imageView.setTag("like");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void nrLikes (final TextView likes, String postid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("likes")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likes.setText(snapshot.getChildrenCount() + " likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getComments (String postid, TextView comments){

        DatabaseReference reference = FirebaseDatabase.getInstance() .getReference ().child("Comments").child(postid);
reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        comments.setText ("View All "+snapshot.getChildrenCount () + " Comments");

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});





    }


//    public void publisherinfo(final TextView username,final TextView publisher,final String userid){
//
//        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user=snapshot.getValue(User.class);
//                username.setText(user.getfirstname());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }




}
