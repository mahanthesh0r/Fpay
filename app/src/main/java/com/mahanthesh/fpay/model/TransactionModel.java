package com.mahanthesh.fpay.model;

public class TransactionModel {

    private Long amount;
    private Boolean isCredited;
    private UserInfo userInfo;
    private UserInfo receiverInfo;
    private SavedCardModel savedCardModel;
    private Long createdAt;


    public TransactionModel() {
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Boolean getCredited() {
        return isCredited;
    }

    public void setCredited(Boolean credited) {
        isCredited = credited;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public SavedCardModel getSavedCardModel() {
        return savedCardModel;
    }

    public void setSavedCardModel(SavedCardModel savedCardModel) {
        this.savedCardModel = savedCardModel;
    }

    public UserInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(UserInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
