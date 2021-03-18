package com.mahanthesh.fpay.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahanthesh.fpay.model.TransactionModel;
import com.mahanthesh.fpay.repository.TransactionRepository;

public class TransactionViewModel extends ViewModel implements TransactionRepository.OnFirestoreTaskComplete {

    private TransactionRepository transactionRepository = new TransactionRepository(this);
    private MutableLiveData<String> onSuccessMessage = new MutableLiveData<>();
    private MutableLiveData<String> onErrorMessage = new MutableLiveData<>();

    public MutableLiveData<String> getOnSuccessMessage() {
        return onSuccessMessage;
    }

    public MutableLiveData<String> getOnErrorMessage() {
        return onErrorMessage;
    }

    public void saveTransaction(TransactionModel transactionModel){
        transactionRepository.setTransaction(transactionModel);
    }



    @Override
    public void onSaveTransactionSuccess() {
        onSuccessMessage.setValue("Success");
    }

    @Override
    public void onError(Exception e) {
        onErrorMessage.setValue(e.getLocalizedMessage());
    }
}
