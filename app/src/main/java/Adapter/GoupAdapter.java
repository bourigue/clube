package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clube.R;
import com.example.clube.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import model.Groups;

public class GoupAdapter extends RecyclerView.Adapter<GoupAdapter.ViewHolder>{

    public Context mcontext;
    private List<Groups> groupList;

    private FirebaseAuth auth;
    private String mykey;
    public GoupAdapter(Context mcontext, List<Groups> groupList){
        this.mcontext=mcontext;
        this.groupList=groupList;
    }

    @NonNull
    @Override
    public GoupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.group_item,parent,false);
        return new GoupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoupAdapter.ViewHolder holder, int position) {
        Groups group= groupList.get(position);

        if (mykey.equals(group.getFrom())) {

            holder.lyMessageOutText.setVisibility(View.VISIBLE);
            holder.lyMessageInText.setVisibility(View.GONE);
            holder.mydisplayMessage.setText(group.getMessage());
            holder.mytime.setText(group.getTime());
            Toast.makeText(mcontext, group.getName(), Toast.LENGTH_SHORT).show();
        } else {
            holder.lyMessageInText.setVisibility(View.VISIBLE);
            holder.lyMessageOutText.setVisibility(View.GONE);
            holder.displayMessage.setText(group.getMessage());
            holder.time.setText(group.getTime());
            holder.myname.setText(group.getName());
        }
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton sendMessage;
        private EditText userMessageInput;
        private TextView myname;
        private TextView myname2;

        private TextView time;
        private TextView displayMessage;
        private TextView mytime;
        private TextView mydisplayMessage;
        private LinearLayout lyMessageOutText;
        private LinearLayout lyMessageInText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            displayMessage = itemView.findViewById(R.id.display);
            time = itemView.findViewById(R.id.time);
            mydisplayMessage = itemView.findViewById(R.id.mydisplay);
            mytime = itemView.findViewById(R.id.mytime);
            auth= FirebaseAuth.getInstance();
            mykey=auth.getCurrentUser().getUid();
            lyMessageInText=itemView.findViewById(R.id.other_message);
            lyMessageOutText=itemView.findViewById(R.id.my_message);
            myname=itemView.findViewById(R.id.myname);

       }
    }
}
