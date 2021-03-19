package com.mahanthesh.fpay.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.davidmiguel.numberkeyboard.NumberKeyboard;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;
import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.viewModel.ConfirmTopupViewModel;

import java.text.NumberFormat;

import static com.mahanthesh.fpay.utils.Constants.MAX_TOPUP_LIMIT;

public class TopUpActivity extends AppCompatActivity implements NumberKeyboardListener, View.OnClickListener {

    TextView textViewAmount;
    Button btnNext;
    NumberKeyboard numberKeyboard;
    private ImageButton imageButtonBack;
    private int amount = 0;
    private NumberFormat nf = NumberFormat.getInstance();
    private ConfirmTopupViewModel confirmTopupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        init();
        listener();

    }

    private void init() {
        textViewAmount = findViewById(R.id.amount);
        btnNext = findViewById(R.id.btn_next);
        numberKeyboard = findViewById(R.id.numberKeyboard);
        imageButtonBack = findViewById(R.id.ib_back);
        confirmTopupViewModel = new ViewModelProvider(this).get(ConfirmTopupViewModel.class);
    }

    private void listener(){
        numberKeyboard.setListener(this);
        btnNext.setOnClickListener(this);
        imageButtonBack.setOnClickListener(this);
    }

    private void showAmount(){
        textViewAmount.setText(nf.format(amount));
    }

    @Override
    public void onLeftAuxButtonClicked() {

    }

    @Override
    public void onNumberClicked(int i) {
        int newAmount = (int) (amount * 10.0 + i);
        if(newAmount <= MAX_TOPUP_LIMIT){
            amount = newAmount;
            showAmount();
        } else {
            Toast.makeText(this, "Max Topup limit is 5000", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRightAuxButtonClicked() {
        amount = (int) (amount / 10.0);
        showAmount();


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_next:
                //TODO handle next button
               confirmTopupViewModel.setTopupValue(amount);
                Intent topupIntent = new Intent(this, ConfirmTopupActivity.class);
                topupIntent.putExtra("amount", amount);
                startActivity(topupIntent);
                this.finish();
                break;
            case R.id.ib_back:
                startActivity(new Intent(this, HomeActivity.class));
                this.finish();
                break;
        }
    }
}