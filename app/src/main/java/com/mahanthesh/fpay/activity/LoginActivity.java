package com.mahanthesh.fpay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.mahanthesh.fpay.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSignup, btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signin:
                Intent signinIntent = new Intent(this, HomeActivity.class);
                startActivity(signinIntent);
                break;
            case R.id.btn_create_account:
                Intent signupIntent = new Intent(this, SignupActivity.class);
                startActivity(signupIntent);
                break;
        }
    }
}