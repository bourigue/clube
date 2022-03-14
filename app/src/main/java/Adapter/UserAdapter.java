package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clube.Poste;
import com.example.clube.R;
import com.example.clube.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    public Context mcontext;
    private List<User> userList;

    public UserAdapter(Context mcontext, List<User> userList){
        this.mcontext=mcontext;
        this.userList=userList;
  }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user= userList.get(position);
        holder.usename.setText(user.getfirstname());
}

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView usename;
        public ViewHolder(@NonNull View itemView) {
           super(itemView);
           usename=itemView.findViewById(R.id.textviewuser);

        }
    }
}
