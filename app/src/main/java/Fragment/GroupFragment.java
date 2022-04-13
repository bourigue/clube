package Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.clube.R;
import com.example.clube.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Adapter.GoupAdapter;
import Adapter.UserAdapter;
import model.Groups;

public class GroupFragment extends Fragment {

    private RecyclerView recyclerView;
    private GoupAdapter groupAdapter;
    private List<Groups> groupLists;
    private DatabaseReference usersRefs, groupNameRef, groupMessageKeyRef;
    private FirebaseAuth mAuth;
    private String currentGroupName, currentUserid, currentUserName, currentDate, currentTime;
    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_group, container, false);
        recyclerView=view.findViewById(R.id.recyclerViewgroup);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        groupLists=new ArrayList<>();
        groupAdapter=new GoupAdapter(getContext(),groupLists);
        recyclerView.setAdapter(groupAdapter);
        mAuth = FirebaseAuth.getInstance();

        currentUserid = mAuth.getCurrentUser().getUid();
        usersRefs = FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView.setAdapter(groupAdapter);
        getuserinfo();


        return view;
    }
    public void readpost(String currentGroupName){
         groupNameRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroupName);
         groupNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupLists.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    String name=snapshot1.child("name").getValue(String.class);
                    String message=snapshot1.child("message").getValue(String.class);
                    String date=snapshot1.child("date").getValue(String.class);
                    String time=snapshot1.child("time").getValue(String.class);
                    String from=snapshot1.child("from").getValue(String.class);
                    Groups group=new Groups(message,date,time,name,from);
                    groupLists.add(group);
                    Toast.makeText(getContext(), from, Toast.LENGTH_SHORT).show();

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
                    currentGroupName = snapshot.child("groupname").getValue().toString();
                    Toast.makeText(getContext(), currentGroupName, Toast.LENGTH_SHORT).show();
                    readpost(currentGroupName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}