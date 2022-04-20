package Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.clube.R;
import com.example.clube.add_post;
import com.example.clube.login;
import com.example.clube.register;


public class addPostFragment extends Fragment {
    Button addMem;
    Button post;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_add_post, container, false);


        addMem = (Button) view.findViewById(R.id.addmem);
        addMem();
        post = (Button) view.findViewById(R.id.addpost);
        post();

        return view;
    }

    private void post() {
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), add_post.class);
                startActivity(i);
            }
        });
    }

    private void addMem() {

        addMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), register.class);
                startActivity(i);
            }
        });
    }
}