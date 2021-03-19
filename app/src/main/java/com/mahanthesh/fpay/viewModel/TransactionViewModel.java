package com.mahanthesh.fpay.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahanthesh.fpay.model.TransactionModel;
import com.mahanthesh.fpay.repository.TransactionRepository;

import java.util.List;

public class TransactionViewModel extends ViewModel implements TransactionRepository.OnFirestoreTaskComplete {

    private TransactionRepository transactionRepository = new TransactionRepository(this);
    private MutableLiveData<String> onSuccessMessage = new MutableLiveData<>();
    private MutableLiveData<String> onErrorMessage = new MutableLiveData<>();
    private MutableLiveData<List<TransactionModel>> transactionListViewModel = new MutableLiveData<>();


    public MutableLiveData<List<TransactionModel>> getTransactionListViewModel() {
        return transactionListViewModel;
    }

    public MutableLiveData<String> getOnSuccessMessage() {
        return onSuccessMessage;
    }

    public MutableLiveData<String> getOnErrorMessage() {
        return onErrorMessage;
    }

    public void saveTransaction(TransactionModel transactionModel){
        transactionRepository.setTransaction(transactionModel);
    }

    public TransactionViewModel(){
        transactionRepository.getTransaction();
    }

    @Override
    public void onSaveTransactionSuccess(String id) {
        onSuccessMessage.setValue(id);
    }

    @Override
    public void onGetTransactions(List<TransactionModel> transactionModelList) {
        transactionListViewModel.setValue(transactionModelList);
    }

    @Override
    public void onError(Exception e) {
        onErrorMessage.setValue(e.getLocalizedMessage());
    }
}
