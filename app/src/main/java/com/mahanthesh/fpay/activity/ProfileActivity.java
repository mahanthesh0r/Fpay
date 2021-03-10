package com.mahanthesh.fpay.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.model.UserInfo;
import com.mahanthesh.fpay.viewModel.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity {

    private ProfileViewModel profileViewModel;
    private static final String TAG = "ProfileActivity";
    private ShimmerFrameLayout shimmerFrameLayout;
    private ScrollView scrollView;
    private EditText et_fname, et_lname, et_email, et_phone, et_gender, et_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

    }

    private void init() {
        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone_number);
        et_gender = findViewById(R.id.et_gender);
        et_address = findViewById(R.id.et_address);
        scrollView = findViewById(R.id.scrollView2);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_layout);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getUserInfoLiveData().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.hideShimmer();
                scrollView.setVisibility(View.VISIBLE);
                updateUI(userInfo);

            }
        });



    }

    private void updateUI(UserInfo userInfo) {
        if(userInfo != null){
            et_fname.setText(userInfo.getFirstName());
            et_lname.setText(userInfo.getLastName());
            et_email.setText(userInfo.getEmail());
            et_phone.setText(userInfo.getPhoneNo());
            et_gender.setText(userInfo.getGender());
            et_address.setText(userInfo.getAddress());
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}