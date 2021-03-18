package com.mahanthesh.fpay.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gurutouchlabs.kenneth.elegantdialog.ElegantDialog;
import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.model.SavedCardModel;
import com.mahanthesh.fpay.model.TransactionModel;
import com.mahanthesh.fpay.model.UserInfo;
import com.mahanthesh.fpay.viewModel.ConfirmTopupViewModel;
import com.mahanthesh.fpay.viewModel.ProfileViewModel;
import com.mahanthesh.fpay.viewModel.TransactionViewModel;
import com.mahanthesh.fpay.viewModel.WalletViewModel;

import java.text.NumberFormat;

import dmax.dialog.SpotsDialog;

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
    private WalletViewModel walletViewModel;
    private SpotsDialog spotsDialog;
    private boolean isCardSelected = false;
    private UserInfo userInfoPayload;
    private SavedCardModel cardSelectedPayload;



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
        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        getUserDetails();


    }

    private void listener(){
        imageButtonChooseCard.setOnClickListener(this);
        imageButtonBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    private void showChooseCard(SavedCardModel savedCardModel) {
        if(savedCardModel != null){
            isCardSelected = true;
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
           cardSelectedPayload = savedCardModel;
           showChooseCard(savedCardModel);
       } else{
           showChooseCard(null);
       }

    }

    private void confirmWalletBalance(){
        if(isCardSelected){
            Long Lamount = (long) amount;
            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setAmount(Lamount);
            transactionModel.setCredited(true);
            if(userInfoPayload != null && cardSelectedPayload != null){
                transactionModel.setUserInfo(userInfoPayload);
                transactionModel.setSavedCardModel(cardSelectedPayload);
                walletViewModel.setWalletBalance(Lamount);
                TransactionViewModel transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
                transactionViewModel.saveTransaction(transactionModel);

                transactionViewModel.getOnSuccessMessage().observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if(s.equalsIgnoreCase("Success")){
                            if(spotsDialog != null)
                                spotsDialog.dismiss();
                            showDialogMessage();

                        }
                    }
                });

                transactionViewModel.getOnErrorMessage().observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if(s != null && !s.equals("")){
                            showDialogMessage();
                        }
                    }
                });
            }

        } else {
            spotsDialog.dismiss();
            Toast.makeText(this, "Please select a payment option", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserDetails(){
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getUserInfoLiveData().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                userInfoPayload = userInfo;
            }
        });
    }

    private void showProgressDialog(){
      spotsDialog = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomProgress)
                .setCancelable(false)
                .build();

      spotsDialog.show();

    }

    private void showDialogMessage(){
        ElegantDialog dialog = new ElegantDialog(this)
                .setTitleIcon(getDrawable(R.drawable.ic_card))
                .setTitleIconBackgroundColor(getColor(R.color.colorWhite))
                .setBackgroundTopColor(getColor(R.color.colorPrimary))
                .setCornerRadius(20)
                .setBackgroundBottomColor(getColor(R.color.colorWhite))
                .show();
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
                    showProgressDialog();
                    confirmWalletBalance();
                break;
            case R.id.ib_back_confirm_topup:
                finish();
                break;
        }
    }
}