package com.mahanthesh.fpay.model;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class SavedCardModel implements Serializable {

    @DocumentId
    private String cardId;
    private String cardHolderName;
    private String cardHolderNumber;
    private String cardExpiry;
    private String cardCVV;
    private String phoneno;
    private String cardBrand;

    public SavedCardModel(String cardId, String cardHolderName, String cardHolderNumber, String cardExpiry, String cardCVV, String phoneno, String cardBrand) {
        this.cardId = cardId;
        this.cardHolderName = cardHolderName;
        this.cardHolderNumber = cardHolderNumber;
        this.cardExpiry = cardExpiry;
        this.cardCVV = cardCVV;
        this.phoneno = phoneno;
        this.cardBrand = cardBrand;
    }

    public SavedCardModel() {
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardHolderNumber() {
        return cardHolderNumber;
    }

    public void setCardHolderNumber(String cardHolderNumber) {
        this.cardHolderNumber = cardHolderNumber;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}