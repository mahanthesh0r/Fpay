
package com.mahanthesh.fpay.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.mahanthesh.fpay.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewArrowSettings;
    ConstraintLayout constraintLayoutSettings;

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
    }

    private void listeners() {
        textViewArrowSettings.setOnClickListener(this);
        constraintLayoutSettings.setOnClickListener(this);

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
                break;
        }
    }
}