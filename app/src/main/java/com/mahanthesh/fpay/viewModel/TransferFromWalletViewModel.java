package com.mahanthesh.fpay.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahanthesh.fpay.repository.TransferFromWalletRepository;

public class TransferFromWalletViewModel extends ViewModel implements TransferFromWalletRepository.OnFirestoreTaskComplete {

    private TransferFromWalletRepository transferFromWalletRepository = new TransferFromWalletRepository(this);
    private MutableLiveData<String> walletUpdateStatus = new MutableLiveData<>();

    public void transferFromWallet(String senderID, String receiverID, Long amount){
        transferFromWalletRepository.updateWalletBalance(senderID, receiverID, amount);
    }

    public MutableLiveData<String> getWalletUpdateStatus() {
        return walletUpdateStatus;
    }

    @Override
    public void onTransferSuccess(String receiverID, Long amount) {
        walletUpdateStatus.setValue("success");
    }

    @Override
    public void onTransferFail(Exception e) {
        walletUpdateStatus.setValue("failed");
    }
}
