package com.mahanthesh.fpay.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.braintreepayments.cardform.OnCardFormSubmitListener;
import com.braintreepayments.cardform.utils.CardType;
import com.braintreepayments.cardform.view.CardEditText;
import com.braintreepayments.cardform.view.CardForm;
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.adapters.CreditCardAdapter;
import com.mahanthesh.fpay.model.SavedCardModel;
import com.mahanthesh.fpay.utils.LinePagerIndicatorDecoration;
import com.mahanthesh.fpay.viewModel.SavedCardViewModel;

import java.util.List;

import static com.mahanthesh.fpay.utils.Utils.hideKeyboard;

public class CreditCardActivity extends AppCompatActivity implements OnCardFormSubmitListener,
        CardEditText.OnCardTypeChangedListener, View.OnClickListener {

    private CardForm cardForm;
    private Button btnAddCard;
    private CardView cardViewCreditCardForm;
    private SavedCardViewModel savedCardViewModel;
    private static final String TAG = "CreditCardActivity";
    private RecyclerView listViewCreditCard;
    private CreditCardAdapter adapter;
    private PagerSnapHelper snapHelper;
    private ShimmerFrameLayout shimmerFrameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        init();
        listener();
    }

    private void init() {
        cardForm = (CardForm) findViewById(R.id.card_form);
        btnAddCard = findViewById(R.id.btn_add_card);
        cardViewCreditCardForm = findViewById(R.id.cv_credit_card_form);
        savedCardViewModel = new ViewModelProvider(this).get(SavedCardViewModel.class);
        listViewCreditCard = findViewById(R.id.lv_credit_card);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(listViewCreditCard);
        // pager indicator
        listViewCreditCard.addItemDecoration(new LinePagerIndicatorDecoration());
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_layout_credit_card);


        adapter = new CreditCardAdapter();
        listViewCreditCard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        listViewCreditCard.setAdapter(adapter);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        initCreditCardForm();
        fetchSavedCards();


    }

    private void listener(){
        cardForm.setOnCardTypeChangedListener(this);
        cardForm.setOnCardFormSubmitListener(this);
        btnAddCard.setOnClickListener(this);
    }

    private void initCreditCardForm(){
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .saveCardCheckBoxChecked(true)
                .saveCardCheckBoxVisible(true)
                .setup(this);
    }

    private void fetchSavedCards(){
        savedCardViewModel.getSavedCardLiveData().observe(this, new Observer<List<SavedCardModel>>() {
            @Override
            public void onChanged(List<SavedCardModel> savedCardModelList) {
                adapter.setSavedCardModelList(savedCardModelList);
                adapter.notifyDataSetChanged();
                shimmerFrameLayout.hideShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

            }
        });

        savedCardViewModel.getSavedCardMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.contains("Error")){
                    Log.d(TAG, "onChanged: " + s);
                    shimmerFrameLayout.hideShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void saveCreditCard(){

        SavedCardModel savedCardModel = new SavedCardModel();
        savedCardModel.setCardHolderName(cardForm.getCardholderName());
        savedCardModel.setCardHolderNumber(cardForm.getCardNumber());
        savedCardModel.setCardCVV(cardForm.getCvv());
        savedCardModel.setCardExpiry(cardForm.getExpirationMonth() + "/" + cardForm.getExpirationYear());
        savedCardModel.setPhoneno(cardForm.getMobileNumber());

        savedCardViewModel.postSavedCard(savedCardModel);

        savedCardViewModel.getSavedCardMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("Card Saved")){
                    Log.d(TAG, "onChanged: " + s);
                    cardViewCreditCardForm.setVisibility(View.GONE);
                    fetchSavedCards();
                } else {
                    Log.d(TAG, "onChanged: " + s);
                }
            }
        });
    }

    @Override
    public void onCardFormSubmit() {
        if(cardForm.isValid()){
            Toast.makeText(this, "Card Saved", Toast.LENGTH_SHORT).show();
        } else {
            cardForm.validate();
            Toast.makeText(this, "Invalid card", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCardTypeChanged(CardType cardType) {
        if (cardType == CardType.EMPTY) {

        } else {

        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add_card){
            hideKeyboard(this);
            if(cardForm.isValid()){
                saveCreditCard();
                Toast.makeText(this, "Card Saved", Toast.LENGTH_SHORT).show();
            } else {
                cardForm.validate();
                Toast.makeText(this, "Invalid card", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if(item.getItemId() == R.id.menu_add_card_btn){
            cardViewCreditCardForm.setVisibility(View.VISIBLE);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}