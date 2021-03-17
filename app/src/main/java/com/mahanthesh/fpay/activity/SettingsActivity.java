
package com.mahanthesh.fpay.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.model.UserInfo;
import com.mahanthesh.fpay.viewModel.ProfileViewModel;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewArrowSettings, textViewUsername, textViewPhone;
    ConstraintLayout constraintLayoutSettings;
    ProfileViewModel profileViewModel;
    ShimmerFrameLayout shimmerFrameLayout;
    CardView cardViewUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
        listeners();
    }

    private void init() {
        textViewArrowSettings = findViewById(R.id.tv_arrow_settings);
        constraintLayoutSettings = findViewById(R.id.cl_settings);
        textViewUsername = findViewById(R.id.tv_username);
        textViewPhone = findViewById(R.id.tv_phone_number);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_layout_user_details);
        cardViewUserDetails = findViewById(R.id.cv_user_details);
        cardViewUserDetails.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        getProfileDetails();
    }



    private void listeners() {
        textViewArrowSettings.setOnClickListener(this);
        constraintLayoutSettings.setOnClickListener(this);
        cardViewUserDetails.setOnClickListener(this);

    }

    private void getProfileDetails() {
        profileViewModel.getUserInfoLiveData().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                if(userInfo != null){
                    textViewUsername.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
                    textViewPhone.setText(userInfo.getPhoneNo());
                    shimmerFrameLayout.hideShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    cardViewUserDetails.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_arrow_settings:
            case R.id.cl_settings:
                startActivity(new Intent(this, PinSettingsActivity.class));
                this.finish();
                break;
            case R.id.cv_user_details:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
    }
}