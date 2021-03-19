package com.mahanthesh.fpay.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.adapters.BannerSliderAdapter;
import com.mahanthesh.fpay.adapters.SpendingAdapter;
import com.mahanthesh.fpay.model.TransactionModel;
import com.mahanthesh.fpay.utils.GlideImageLoadingService;
import com.mahanthesh.fpay.viewModel.TransactionViewModel;
import com.mahanthesh.fpay.viewModel.WalletViewModel;

import java.util.List;

import ss.com.bannerslider.Slider;

public class HomeActivity extends AppCompatActivity implements SpaceOnClickListener, View.OnClickListener {

    SpaceNavigationView bottomNavigation;
    private static final String TAG = "HomeActivity";
    private Button btnTopUp, btnTransfer;
    private TextView textViewWalletBalance;
    private WalletViewModel walletViewModel;
    private Slider slider;
    private RecyclerView recyclerViewSpendings;
    private TransactionViewModel transactionViewModel;
    private SpendingAdapter spendingAdapter;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
        Slider.init(new GlideImageLoadingService(this));
        init();
        listeners();
    }

    private void init() {
       bottomNavigation = (SpaceNavigationView) findViewById(R.id.bottomNavigation);
       btnTopUp = findViewById(R.id.btn_top_up);
       btnTransfer = findViewById(R.id.btn_transfer);
       textViewWalletBalance = findViewById(R.id.tv_wallet_balance);
       recyclerViewSpendings = findViewById(R.id.rv_spendings);
       walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
       slider = findViewById(R.id.banner_slider);
       transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
       spendingAdapter = new SpendingAdapter(5);
       recyclerViewSpendings.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
       recyclerViewSpendings.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
       recyclerViewSpendings.setAdapter(spendingAdapter);

        setupBannerView();
       getWalletBalance();
       bottomNavigationConfig();
       fetchSpendings();
    }

    private void listeners() {
        bottomNavigation.setSpaceOnClickListener(this);
        btnTopUp.setOnClickListener(this);
        btnTransfer.setOnClickListener(this);
    }

    private void bottomNavigationConfig(){
        bottomNavigation.addSpaceItem(new SpaceItem("Home",R.drawable.ic_menu));
        bottomNavigation.addSpaceItem(new SpaceItem("Settings",R.drawable.ic_settings));
        bottomNavigation.addSpaceItem(new SpaceItem("Add Card",R.drawable.ic_card));
        bottomNavigation.addSpaceItem(new SpaceItem("Profile",R.drawable.ic_profile));
        bottomNavigation.showIconOnly();

    }

    private void getWalletBalance(){
        walletViewModel.getWalletBalance().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                textViewWalletBalance.setText(integer.toString());
            }
        });
    }

    private void setupBannerView(){
        slider.setAdapter(new BannerSliderAdapter());
        slider.setSelectedSlide(0);
    }

    private void fetchSpendings(){
        transactionViewModel.getTransactionListViewModel().observe(this, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModelList) {
                spendingAdapter.setTransactionModelList(transactionModelList);
                spendingAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

         new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onCentreButtonClick() {
        Intent qrCodeIntent = new Intent(this, QRCodeActivity.class);
        startActivity(qrCodeIntent);
    }

    @Override
    public void onItemClick(int itemIndex, String itemName) {
        switch (itemName){
            case "Profile":
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case "Settings":
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case "Add Card":
                startActivity(new Intent(this, CreditCardActivity.class));
                break;

        }

    }

    @Override
    public void onItemReselected(int itemIndex, String itemName) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
        bottomNavigation.changeCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_top_up:
                startActivity(new Intent(this, TopUpActivity.class));
                break;
            case R.id.btn_transfer:
                startActivity(new Intent(this, BiometricActivity.class));
                break;

        }
    }


}