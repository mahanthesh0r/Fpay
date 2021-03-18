package com.mahanthesh.fpay.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahanthesh.fpay.repository.WalletBalanceRepository;

public class WalletViewModel extends ViewModel implements WalletBalanceRepository.OnFirestoreTaskComplete {

    private static final String TAG = "WalletViewModel";

    private WalletBalanceRepository walletBalanceRepository = new WalletBalanceRepository(this);
    private MutableLiveData<Long> walletBalance = new MutableLiveData<>();
    private MutableLiveData<String> infoMessage = new MutableLiveData<>();

    public MutableLiveData<Long> getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Long amount){
        walletBalanceRepository.setWalletBalance(amount);
    }

    public WalletViewModel(){
        walletBalanceRepository.getWalletBalance();
    }

    @Override
    public void onGetWalletBalance(Long amount) {
        walletBalance.setValue(amount);
    }

    @Override
    public void onError() {
        infoMessage.setValue("Error");
    }

    @Override
    public void onSetWalletBalance(Long amount) {
        walletBalance.setValue(amount);
    }

    @Override
    public void onSetError(Exception e) {
        infoMessage.setValue(e.getLocalizedMessage());
    }
}
