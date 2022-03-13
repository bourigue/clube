package Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clube.Poste;
import com.example.clube.R;
import com.example.clube.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.PostAdapter;
import Adapter.UserAdapter;


public class searchFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userLists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView=view.findViewById(R.id.recyclerViewuser);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        userLists=new ArrayList<>();
        userAdapter=new UserAdapter(getContext(),userLists);
        recyclerView.setAdapter(userAdapter);
        readpost();
        // Inflate the layout for this fragment
        return view;
    }
    public void readpost(){
        DatabaseReference Reference= FirebaseDatabase.getInstance().getReference("Users");
        Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userLists.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    User post=snapshot1.getValue(User.class);
                    userLists.add(post);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}