package com.mahanthesh.fpay.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.OnCardFormSubmitListener;
import com.braintreepayments.cardform.utils.CardType;
import com.braintreepayments.cardform.view.CardEditText;
import com.braintreepayments.cardform.view.CardForm;
import com.braintreepayments.cardform.view.SupportedCardTypesView;
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.utils.Utils;

import static com.mahanthesh.fpay.utils.Utils.SUPPORTED_CARD_TYPES;
import static com.mahanthesh.fpay.utils.Utils.hideKeyboard;

public class CreditCardActivity extends AppCompatActivity implements OnCardFormSubmitListener,
        CardEditText.OnCardTypeChangedListener, View.OnClickListener {

    private CardForm cardForm;
    private Button btnAddCard;
    private CreditCardView creditCardView;
    private CardView cardViewCreditCardForm;


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
        creditCardView = (CreditCardView) findViewById(R.id.credit_card_view);
        cardViewCreditCardForm = findViewById(R.id.cv_credit_card_form);
        creditCardForm();

    }

    private void listener(){
        cardForm.setOnCardTypeChangedListener(this);
        cardForm.setOnCardFormSubmitListener(this);
        btnAddCard.setOnClickListener(this);
    }

    private void creditCardForm(){
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .saveCardCheckBoxChecked(true)
                .saveCardCheckBoxVisible(true)
                .setup(this);
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
                creditCardView.setCardHolderName(cardForm.getCardholderName());
                creditCardView.setCardExpiry(cardForm.getExpirationMonth().concat("/").concat(cardForm.getExpirationYear()));
                creditCardView.setCardNumber(cardForm.getCardNumber());
                creditCardView.setCVV(cardForm.getCvv());
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