package com.example.clube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import Fragment.FragmentHome;
import Fragment.addPostFragment;
import Fragment.favoriteFragment;
import Fragment.searchFragment;

public class Mainall extends AppCompatActivity {
    private final int ID_HOME=1;
    private final int ID_NOTIFICATION=2;
    private final int ID_ADD_POST=3;
    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainall);

       bottomNavigation = findViewById(R.id.bottom_navigation3);

        bottomNavigation.add (new MeowBottomNavigation.Model(ID_HOME , R.drawable.ic_home));
        bottomNavigation.add (new MeowBottomNavigation.Model(ID_NOTIFICATION,R.drawable.ic_favor ));
        bottomNavigation.add (new MeowBottomNavigation.Model(ID_ADD_POST, R.drawable.ic_add_post ));


        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                Fragment fragment = null;

                switch (item.getId()){
                    case 1:

                        fragment = new FragmentHome();
                        break;
                    case 2:

                        fragment = new favoriteFragment();
                        break;
                    case 3:

                        fragment = new addPostFragment();
                        break;
                   }

                loadFragment(fragment);
            }
        });
        bottomNavigation.show(1, true);

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });


    }


    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fram_lq,fragment)
                .commit();
    }


}
