package com.mahanthesh.fpay.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mahanthesh.fpay.R;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSignup, btnSignin;
    private static final String TAG = "LoginActivity";
    int AUTHUI_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
          startActivity(new Intent(this, HomeActivity.class));
          this.finish();
        }

        init();
        listener();
    }

    private void listener() {
        btnSignup.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
    }

    private void init() {
        btnSignin = findViewById(R.id.btn_signin);
        btnSignup = findViewById(R.id.btn_create_account);
    }

    public void handleFirebaseLogin(){
        List<AuthUI.IdpConfig> provider = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
               new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com")
                .setAlwaysShowSignInMethodScreen(true)
                .setLogo(R.drawable.logo)
                .setTheme(R.style.CustomTheme)
                .build();

        startActivityForResult(intent, AUTHUI_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AUTHUI_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                //we have signed in the user or we have a new user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                if(user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()){
                    startActivity(new Intent(this, ProfileActivity.class));
                    this.finish();
                } else {
                    //Existing user
                    startActivity(new Intent(this, HomeActivity.class));
                    this.finish();
                }
            } else {
                //Sign in failed
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();

                if(response == null){
                    //user cancelled
                } else{
                    //response.getError
                    Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signin:
                handleFirebaseLogin();
                break;
            case R.id.btn_create_account:
                handleFirebaseLogin();
                break;
        }
    }
}