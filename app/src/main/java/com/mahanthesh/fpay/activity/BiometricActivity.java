package com.mahanthesh.fpay.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.an.biometric.BiometricCallback;
import com.an.biometric.BiometricManager;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.model.UserInfo;
import com.mahanthesh.fpay.viewModel.ProfileViewModel;

public class BiometricActivity extends AppCompatActivity implements BiometricCallback, FirebaseAuth.AuthStateListener {

    private ImageView imageViewFingerPrint;
    private ProfileViewModel profileViewModel;
    private TextView textViewUsername;
    private Button btnShowFingerprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);


        init();
    }

    private void init(){
        imageViewFingerPrint = findViewById(R.id.image_view_finger_print);
        textViewUsername = findViewById(R.id.tv_user_name);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        btnShowFingerprint = findViewById(R.id.btn_show_fingerprint);
        getUserDetails();
        showGif();
        btnShowFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBiometricDialog();
            }
        });

    }

    private void getUserDetails() {
        profileViewModel.getUserInfoLiveData().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                if(userInfo != null){
                    textViewUsername.setText("Hello" + " " + userInfo.getFirstName() + ",");
                }
            }
        });
    }

    private void showBiometricDialog() {
        BiometricManager mBiometricManager = new BiometricManager.BiometricBuilder(BiometricActivity.this)
                .setTitle(getString(R.string.biometric_title))
                .setSubtitle(getString(R.string.biometric_subtitle))
                .setDescription(getString(R.string.biometric_description))
                .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                .build();

        //start authentication
        mBiometricManager.authenticate(BiometricActivity.this);
    }

    private void showGif(){
        Glide.with(this).load(R.drawable.fingerprint_500).into(imageViewFingerPrint);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);

    }

    @Override
    public void onSdkVersionNotSupported() {
        showToast(getString(R.string.biometric_error_sdk_not_supported));
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        showToast(getString(R.string.biometric_error_hardware_not_supported));
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        showToast(getString(R.string.biometric_error_not_available));

    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        showToast(getString(R.string.biometric_error_permission_not_granted));
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        showToast(error);
    }

    @Override
    public void onAuthenticationFailed() {
        showToast(getString(R.string.biometric_error_failed));

    }

    @Override
    public void onAuthenticationCancelled() {
        showToast(getString(R.string.biometric_cancelled));

    }

    @Override
    public void onAuthenticationSuccessful() {
        showToast(getString(R.string.biometric_success));
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
            return;
        }else{
            showBiometricDialog();
        }
    }
}