package Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.clube.MainActivity;
import com.example.clube.R;
import com.example.clube.add_post;
import com.example.clube.login;


public class addPostFragment extends Fragment {
Button b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_post, container, false);

                Intent i=new Intent(getContext(), add_post.class);
                startActivity(i);

        return view;
 }

}