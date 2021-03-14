package com.mahanthesh.fpay.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.model.UserInfo;
import com.mahanthesh.fpay.utils.Utils;
import com.mahanthesh.fpay.viewModel.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private ProfileViewModel profileViewModel;
    private static final String TAG = "ProfileActivity";
    private ShimmerFrameLayout shimmerFrameLayout;
    private ScrollView scrollView;
    private EditText et_fname, et_lname, et_email, et_phone,  et_address;
    private Button btnSave;
    private Spinner spinnerGender;
    String[] genderList = { "Choose gender","Male", "Female", "Other"};
    String selectedGender;
    private ProgressBar progressBar;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
        listener();

    }

    private void init() {
        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone_number);
        spinnerGender = (Spinner) findViewById(R.id.spinner_gender);
        et_address = findViewById(R.id.et_address);
        scrollView = findViewById(R.id.scrollView2);
        btnSave = findViewById(R.id.btn_save);
        progressBar = findViewById(R.id.progress_bar);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,genderList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(aa);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_layout);
        if(firebaseUser != null){
            if(!firebaseUser.getEmail().isEmpty()){
                et_email.setText(firebaseUser.getEmail());
                et_email.setEnabled(false);
            } else if(!firebaseUser.getPhoneNumber().isEmpty()){
                et_phone.setText(firebaseUser.getPhoneNumber());
                et_phone.setEnabled(false);
            } else if(!firebaseUser.getDisplayName().isEmpty()){
                et_fname.setText(firebaseUser.getDisplayName());
            }
        }
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getUserInfoLiveData().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.hideShimmer();
                scrollView.setVisibility(View.VISIBLE);
                fetchProfileDetails(userInfo);

            }
        });

        profileViewModel.getUserInfoMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.hideShimmer();
                scrollView.setVisibility(View.VISIBLE);

            }
        });

    }

    private void listener(){
        spinnerGender.setOnItemSelectedListener(this);
        btnSave.setOnClickListener(this);
    }

    private void fetchProfileDetails(UserInfo userInfo) {
        if(userInfo != null){
            et_fname.setText(userInfo.getFirstName());
            et_lname.setText(userInfo.getLastName());
            et_email.setText(userInfo.getEmail());
            et_phone.setText(userInfo.getPhoneNo());
            et_address.setText(userInfo.getAddress());
            if(userInfo.getGender().equalsIgnoreCase("male")){
                spinnerGender.setSelection(1);
            } else if(userInfo.getGender().equalsIgnoreCase("female")){
                spinnerGender.setSelection(2);
            } else {
                spinnerGender.setSelection(3);
            }
        }
    }

    private void saveProfileDetails(){
        if(Utils.isEmpty(et_fname) || Utils.isEmpty(et_lname) || Utils.isEmpty(et_email) || Utils.isEmpty(et_phone) || Utils.isEmpty(et_address)){
            showToast(this, "Please fill all the details");
        } else {
            showProgressBar();
            UserInfo userInfo = new UserInfo();
            userInfo.setFirstName(et_fname.getText().toString());
            userInfo.setLastName(et_lname.getText().toString());
            userInfo.setEmail(et_email.getText().toString());
            userInfo.setPhoneNo(et_phone.getText().toString());
            userInfo.setAddress(et_address.getText().toString());
            userInfo.setGender(selectedGender);
            profileViewModel.setUserInfo(userInfo);
            profileViewModel.getUserInfoOnSaveMessage().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (s.equalsIgnoreCase("Data Saved")) {
                        showToast(ProfileActivity.this, "Data saved successfully");
                    } else {
                        showToast(ProfileActivity.this, "Failed: Please try again");
                    }
                    hideProgressBar();
                }
            });
        }
    }

    private void showProgressBar(){
        btnSave.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
        btnSave.setVisibility(View.VISIBLE);
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            // do nothing
        }else {
            selectedGender = genderList[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_save){
            Utils.hideKeyboard(this);
            saveProfileDetails();
        }
    }
}