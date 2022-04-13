package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clube.R;
import com.example.clube.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import model.comments;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    FirebaseUser Firebaseuser;
      private List<comments> commentlist;
      private Context context;
    public CommentsAdapter(Context context,List<comments> commentlist) {
        this.commentlist = commentlist;
        this.context=context;
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false);
        return new CommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int i) {
        Firebaseuser= FirebaseAuth.getInstance().getCurrentUser();
        comments comment= commentlist.get(i);
        holder.comment.setText (comment.getComment());
        //publisherinfo (holder.profile, holder.username,comment.getPublisher());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(comment.getPublisher());
        reference.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue (User.class);
               // Glide.with(context).load (user.getimageurl()).into (imageView);
                holder.username.setText(user.getfirstname()+" "+user.getlastname());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       //getUserinfo(holder.profile,holder.comment,comment.getPublisher());
      /*  holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context, MainActivity.class);
                intent.putExtra( "publisherid", comment.getPublisher ());
                context.startActivity (intent);
            }
        });
        holder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context, Mainall.class);
                intent.putExtra( "publisherid", comment.getPublisher ());
                context.startActivity (intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return commentlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView username,comment;
        private ImageView profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            comment=itemView.findViewById(R.id.comment);
            profile=itemView.findViewById(R.id.image_profile);


        }
    }




}