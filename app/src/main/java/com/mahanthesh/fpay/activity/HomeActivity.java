package com.mahanthesh.fpay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.mahanthesh.fpay.R;

public class HomeActivity extends AppCompatActivity {

    SpaceNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        listeners();
    }

    private void init() {
       bottomNavigation = (SpaceNavigationView) findViewById(R.id.bottomNavigation);
       bottomNavigationConfig();
    }

    private void listeners() {

    }

    private void bottomNavigationConfig(){
        bottomNavigation.addSpaceItem(new SpaceItem("Home",R.drawable.ic_menu));
        bottomNavigation.addSpaceItem(new SpaceItem("Settings",R.drawable.ic_settings));
        bottomNavigation.addSpaceItem(new SpaceItem("Add Card",R.drawable.ic_card));
        bottomNavigation.addSpaceItem(new SpaceItem("Profile",R.drawable.ic_profile));
        bottomNavigation.showIconOnly();
    }

}