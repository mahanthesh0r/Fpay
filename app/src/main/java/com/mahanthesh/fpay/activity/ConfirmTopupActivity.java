package com.mahanthesh.fpay.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahanthesh.fpay.R;

import java.text.NumberFormat;

import static com.mahanthesh.fpay.utils.Constants.GET_SAVED_CARD;

public class ConfirmTopupActivity extends AppCompatActivity implements View.OnClickListener {

    private int amount = 0;
    private TextView textViewAmountTitle, textViewAmountSummary, textViewCardName, textViewCardNumber;
    private NumberFormat nf = NumberFormat.getInstance();
    private ImageView imageViewCardBrand;
    private ImageButton imageButtonChooseCard, imageButtonBack;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_topup);

        init();
        getAmount();
        listener();
    }

    private void getAmount() {
        Intent i = getIntent();
        amount = i.getIntExtra("amount",0);
        textViewAmountTitle.setText(nf.format(amount));
        textViewAmountSummary.setText(nf.format(amount));

    }

    private void init() {
        textViewAmountSummary = findViewById(R.id.tv_amount_summary);
        textViewAmountTitle = findViewById(R.id.tv_amount_title);
        textViewCardName = findViewById(R.id.tv_bank_name);
        textViewCardNumber = findViewById(R.id.tv_card_number);
        imageViewCardBrand = findViewById(R.id.iv_card_brand);
        imageButtonChooseCard = findViewById(R.id.ib_select_card);
        btnConfirm = findViewById(R.id.btn_confirm_topup);
        imageButtonBack = findViewById(R.id.ib_back_confirm_topup);
        showChooseCard();

    }

    private void listener(){
        imageButtonChooseCard.setOnClickListener(this);
        imageButtonBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    private void showChooseCard() {
        imageViewCardBrand.setVisibility(View.INVISIBLE);
        textViewCardName.setText("Choose a payment option");
        textViewCardNumber.setText("Select or Add a Card");
    }

    private void handleSelectCard(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_select_card:
                //TODO handle select card
                Intent savedCardIntent = new Intent(this, CreditCardActivity.class);
                savedCardIntent.putExtra("saved_card",GET_SAVED_CARD);
                startActivity(savedCardIntent);
                break;
            case R.id.btn_confirm_topup:
                //TODO handle confirm topup
                break;
            case R.id.ib_back_confirm_topup:
                finish();
                break;
        }
    }
}