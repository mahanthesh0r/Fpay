package com.mahanthesh.fpay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mahanthesh.fpay.R;

public class TransactionStatusActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_status);


        init();

    }

    private void init() {
        ImageView imageViewSuccess = findViewById(R.id.iv_success);
        TextView textViewTransactionId = findViewById(R.id.tv_transaction_id);
        Intent i = getIntent();
        textViewTransactionId.setText(i.getStringExtra("trans_id"));
        ImageButton imageButtonCancel = findViewById(R.id.ib_cancel_transaction_status);
        Button btnViewTransaction = findViewById(R.id.btnViewTransaction);
        btnViewTransaction.setOnClickListener(this);
        imageButtonCancel.setOnClickListener(this);
        Glide.with(this)
                .load(R.drawable.check_success)
                .into(imageViewSuccess);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ib_cancel_transaction_status:
                finish();
                break;
            case R.id.btnViewTransaction:
                //TODO handle view transaction
                break;
        }
    }
}