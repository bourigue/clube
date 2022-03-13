package com.example.clube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import Fragment.*;

public class MainActivity extends AppCompatActivity {
  /*  BottomNavigationView bnv;
    Fragment fselectorview=null;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bnv=findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.framgment_container,new FragmentHome()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            =new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.home:
                    fselectorview=new FragmentHome();
                    break;
                case R.id.search:
                    fselectorview=null;
                    fselectorview=new searchFragment();
                    break;
                case R.id.post:
                    fselectorview=null;
                    fselectorview=new addPostFragment();
                    break;
                case R.id.favorite:
                    fselectorview=null;
                    fselectorview=new favoriteFragment();
                    break;


            }
            if(fselectorview!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.framgment_container,new FragmentHome()).commit();
 }

            return true;


        }
    };
*/

}