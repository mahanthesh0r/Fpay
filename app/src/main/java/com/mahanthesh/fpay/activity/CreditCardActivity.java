package com.mahanthesh.fpay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    private SupportedCardTypesView mSupportedCardTypesView;
    private Button btnAddCard;
    private CreditCardView creditCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        init();
        listener();
    }

    private void init() {
        cardForm = (CardForm) findViewById(R.id.card_form);
        mSupportedCardTypesView = findViewById(R.id.supported_card_types);
        mSupportedCardTypesView.setSupportedCardTypes(SUPPORTED_CARD_TYPES);
        btnAddCard = findViewById(R.id.btn_add_card);
        creditCardView = (CreditCardView) findViewById(R.id.credit_card_view);
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
            mSupportedCardTypesView.setSupportedCardTypes(SUPPORTED_CARD_TYPES);
        } else {
            mSupportedCardTypesView.setSelected(cardType);
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
}