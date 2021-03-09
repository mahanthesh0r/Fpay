package com.mahanthesh.fpay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.mahanthesh.fpay.R;

public class HomeActivity extends AppCompatActivity implements SpaceOnClickListener {

    SpaceNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }

        init();
        listeners();
    }

    private void init() {
       bottomNavigation = (SpaceNavigationView) findViewById(R.id.bottomNavigation);
       bottomNavigationConfig();
    }

    private void listeners() {
        bottomNavigation.setSpaceOnClickListener(this);
    }

    private void bottomNavigationConfig(){
        bottomNavigation.addSpaceItem(new SpaceItem("Home",R.drawable.ic_menu));
        bottomNavigation.addSpaceItem(new SpaceItem("Settings",R.drawable.ic_settings));
        bottomNavigation.addSpaceItem(new SpaceItem("Add Card",R.drawable.ic_card));
        bottomNavigation.addSpaceItem(new SpaceItem("Profile",R.drawable.ic_profile));
        bottomNavigation.showIconOnly();
    }

    @Override
    public void onCentreButtonClick() {
        Intent qrCodeIntent = new Intent(this, QRCodeActivity.class);
        startActivity(qrCodeIntent);
    }

    @Override
    public void onItemClick(int itemIndex, String itemName) {
        switch (itemName){
            case "Profile":
                startActivity(new Intent(this, ProfileActivity.class));

        }

    }

    @Override
    public void onItemReselected(int itemIndex, String itemName) {

    }
}