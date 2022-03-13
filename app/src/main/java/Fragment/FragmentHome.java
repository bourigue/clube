package Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.PostAdapter;

import com.example.clube.Poste;
import com.example.clube.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Poste> posteLists;
    private List<String> followingList;
    public FragmentHome() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        posteLists=new ArrayList<>();
        postAdapter=new PostAdapter(getContext(),posteLists);
        recyclerView.setAdapter(postAdapter);

        readpost();
        return view;
    }
    public void readpost(){
        DatabaseReference Reference= FirebaseDatabase.getInstance().getReference("Posts");
        Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                posteLists.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    Poste post=snapshot1.getValue(Poste.class);
                   posteLists.add(post);
                  }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
     }
   }