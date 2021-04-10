package com.mahanthesh.fpay.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.model.SavedCardModel;
import com.mahanthesh.fpay.model.TransactionModel;
import com.mahanthesh.fpay.model.UserInfo;
import com.mahanthesh.fpay.repository.TransferFromWalletRepository;
import com.mahanthesh.fpay.viewModel.ConfirmTopupViewModel;
import com.mahanthesh.fpay.viewModel.ProfileViewModel;
import com.mahanthesh.fpay.viewModel.TransactionViewModel;
import com.mahanthesh.fpay.viewModel.TransferFromWalletViewModel;
import com.mahanthesh.fpay.viewModel.WalletViewModel;

import java.text.NumberFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.mahanthesh.fpay.utils.Constants.FPAY_RECEIVER_UID;
import static com.mahanthesh.fpay.utils.Constants.FPAY_SENDER_UID;
import static com.mahanthesh.fpay.utils.Constants.FPAY_WALLET;
import static com.mahanthesh.fpay.utils.Constants.GET_SAVED_CARD;
import static com.mahanthesh.fpay.utils.Constants.PAYMENT_METHOD_KEY;

public class ConfirmTopupActivity extends AppCompatActivity implements View.OnClickListener {

    private int amount = 0, walletBalance = 0;
    private String paymentMethod, senderID, receiverID;
    private TextView textViewAmountTitle, textViewAmountSummary, textViewCardName, textViewCardNumber;
    private NumberFormat nf = NumberFormat.getInstance();
    private ImageView imageViewCardBrand;
    private ImageButton imageButtonChooseCard, imageButtonBack;
    private Button btnConfirm;
    private static final String TAG = "ConfirmTopupActivity";
    private ConfirmTopupViewModel confirmTopupViewModel;
    private WalletViewModel walletViewModel;
    private SweetAlertDialog pDialog;
    private boolean isCardSelected = false;
    private UserInfo userInfoPayload, receiverInfoPayload;
    private SavedCardModel cardSelectedPayload;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_topup);

        init();
        getAmount();
        listener();
        getWalletBalance();

    }

    private void getAmount() {
        Intent i = getIntent();
        amount = i.getIntExtra("amount",0);
        paymentMethod = i.getStringExtra(PAYMENT_METHOD_KEY);
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

        if(paymentMethod.equalsIgnoreCase(FPAY_WALLET)){
            imageViewCardBrand.setVisibility(View.GONE);
            textViewCardName.setText("Fpay Wallet");
            textViewCardNumber.setText(amount + " Will be deducted from your Fpay wallet");
            imageButtonChooseCard.setOnClickListener(null);

            //Get sender and receiver id from intent
            getSenderReceiverIntent();

        }else{
            handleSelectCard();
        }



    }

    private void getSenderReceiverIntent(){
        Intent i = getIntent();
        senderID = i.getStringExtra(FPAY_SENDER_UID);
        receiverID = i.getStringExtra(FPAY_RECEIVER_UID);
        getReceiverDetails();

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
            showProgressDialog("Please wait...");
            Long Lamount = (long) amount;
            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setAmount(Lamount);
            transactionModel.setCredited(true);
            transactionModel.setCreatedAt(System.currentTimeMillis());
            if(userInfoPayload != null && cardSelectedPayload != null){
                transactionModel.setUserInfo(userInfoPayload);
                transactionModel.setSavedCardModel(cardSelectedPayload);
                walletViewModel.setWalletBalance(Lamount);
                TransactionViewModel transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
                transactionViewModel.saveTransaction(transactionModel);

                transactionViewModel.getOnSuccessMessage().observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if(!s.isEmpty()){
                            pDialog.dismiss();
                            pDialog = null;
                            if(pDialog == null)
                                startTransactionActivity(s);


                        }
                    }
                });

                transactionViewModel.getOnErrorMessage().observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if(s != null && !s.equals("")){
                            pDialog.dismiss();
                            showDialogMessage("Failed");
                        }
                    }
                });
            }

        } else {
            Toast.makeText(this, "Please select a payment option", Toast.LENGTH_SHORT).show();
        }
    }

    private void transferFromWallet(){
        showProgressDialog("Please wait...");
        Long Lamount = (long) amount;
        if(checkWalletBalance(amount)){
            if(receiverInfoPayload != null){
                TransferFromWalletViewModel transferFromWalletViewModel = new ViewModelProvider(this).get(TransferFromWalletViewModel.class);
                transferFromWalletViewModel.transferFromWallet(senderID, receiverID, Lamount);
                transferFromWalletViewModel.getWalletUpdateStatus().observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if(s.equalsIgnoreCase("success")){
                            saveTransaction((long) amount, false,userInfoPayload, null, receiverInfoPayload);
                        } else{
                            //failed
                            pDialog.dismiss();
                        }
                    }
                });
            }
        } else{
            //Low Balance
            pDialog.dismiss();
            showDialogMessage("Sorry insufficient funds, Please Top-up your wallet");
        }
    }

    private boolean checkWalletBalance( long amount){
        return walletBalance != 0 && walletBalance >= amount;
    }

    private void getWalletBalance(){
        walletViewModel.getWalletBalance().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if(integer != null)
                     walletBalance = Math.toIntExact(integer);
            }
        });
    }

    private void saveTransaction(Long amount, boolean isCredited, UserInfo userInfo,SavedCardModel savedCardModel, UserInfo receiverInfo){
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setAmount(amount);
        transactionModel.setCredited(isCredited);
        transactionModel.setUserInfo(userInfo);
        transactionModel.setReceiverInfo(receiverInfo);
        Long createdAt = System.currentTimeMillis();
        transactionModel.setCreatedAt(createdAt);

        TransactionViewModel transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel.saveTransaction(transactionModel);

        transactionViewModel.getOnSuccessMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!s.isEmpty()){
                    pDialog.dismiss();
                    pDialog = null;
                    if(pDialog == null)
                        startTransactionActivity(s);
                }
            }
        });

        transactionViewModel.getOnErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null && !s.equals("")){
                    pDialog.dismiss();
                    showDialogMessage("Failed");
                }
            }
        });
    }

    private void startTransactionActivity(String id){
        Intent i = new Intent(ConfirmTopupActivity.this, TransactionStatusActivity.class);
        i.putExtra("trans_id",id);
        startActivity(i);
        killActivity();
    }

    private void killActivity(){
        this.finish();
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

    private void getReceiverDetails(){
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getReceiverUserData(receiverID);
        profileViewModel.getReceiverInfoLiveData().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                receiverInfoPayload = userInfo;
            }
        });
    }

    private void showProgressDialog(String message){
        pDialog = new SweetAlertDialog(ConfirmTopupActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getColor(R.color.colorPrimary));
        pDialog.setTitleText(message);
        pDialog.setCancelable(false);
        pDialog.show();

    }


    private void showDialogMessage(String message){
        new SweetAlertDialog(ConfirmTopupActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(message)
                .setConfirmText("Okay")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
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
                finish();
                break;
            case R.id.btn_confirm_topup:
                if(paymentMethod.equalsIgnoreCase(FPAY_WALLET)){
                    transferFromWallet();
                } else{
                    confirmWalletBalance();
                }
                break;
            case R.id.ib_back_confirm_topup:
                finish();
                break;
        }
    }
}