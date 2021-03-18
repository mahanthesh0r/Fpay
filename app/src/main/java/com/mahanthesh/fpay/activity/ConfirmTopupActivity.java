package com.mahanthesh.fpay.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.model.SavedCardModel;
import com.mahanthesh.fpay.viewModel.ConfirmTopupViewModel;

import java.text.NumberFormat;

import static com.mahanthesh.fpay.utils.Constants.GET_SAVED_CARD;

public class ConfirmTopupActivity extends AppCompatActivity implements View.OnClickListener {

    private int amount = 0;
    private TextView textViewAmountTitle, textViewAmountSummary, textViewCardName, textViewCardNumber;
    private NumberFormat nf = NumberFormat.getInstance();
    private ImageView imageViewCardBrand;
    private ImageButton imageButtonChooseCard, imageButtonBack;
    private Button btnConfirm;
    private static final String TAG = "ConfirmTopupActivity";
    private ConfirmTopupViewModel confirmTopupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_topup);

        init();
        getAmount();
        listener();
        handleSelectCard();
    }

    private void getAmount() {
        Intent i = getIntent();
        amount = i.getIntExtra("amount",0);
        if(amount != 0){
            confirmTopupViewModel.setTopupValue(amount);
            confirmTopupViewModel.getTopupValue().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    textViewAmountTitle.setText(nf.format(integer));
                    textViewAmountSummary.setText(nf.format(integer));
                }
            });

        } else{
            textViewAmountTitle.setText(nf.format(0));
            textViewAmountSummary.setText(nf.format(0));
        }



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
        confirmTopupViewModel = new ViewModelProvider(this).get(ConfirmTopupViewModel.class);


    }

    private void listener(){
        imageButtonChooseCard.setOnClickListener(this);
        imageButtonBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    private void showChooseCard(SavedCardModel savedCardModel) {
        if(savedCardModel != null){
            switch(savedCardModel.getCardBrand()){
                case "VISA":
                    imageViewCardBrand.setImageResource(R.drawable.ic_billing_visa_logo);
                    imageViewCardBrand.setBackgroundColor(getColor(R.color.colorBlack));
                    break;
                case "MASTERCARD":
                    imageViewCardBrand.setImageResource(R.drawable.ic_billing_mastercard_logo);
                default:
                    imageViewCardBrand.setImageResource(R.drawable.bt_ic_unknown);

            }

            textViewCardNumber.setText(savedCardModel.getCardHolderNumber());
            textViewCardName.setText(savedCardModel.getCardHolderName());
        } else{
            imageViewCardBrand.setVisibility(View.INVISIBLE);
            textViewCardName.setText("Choose a payment option");
            textViewCardNumber.setText("Select or Add a Card");

        }
    }

    private void handleSelectCard(){
        Intent i = getIntent();
       SavedCardModel savedCardModel = (SavedCardModel) i.getSerializableExtra("selected_card");
       if(savedCardModel != null){
           Log.d(TAG, "handleSelectCard: " + savedCardModel.getCardHolderName());
           showChooseCard(savedCardModel);
       } else{
           showChooseCard(null);
       }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_select_card:
                Intent savedCardIntent = new Intent(this, CreditCardActivity.class);
                savedCardIntent.putExtra("saved_card",GET_SAVED_CARD);
                savedCardIntent.putExtra("amount_value", amount);
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