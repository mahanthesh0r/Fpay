package com.mahanthesh.fpay.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfirmTopupViewModel extends ViewModel {

    private static final String TAG = "ConfirmTopupViewModel";
    private MutableLiveData<Integer> topupValue = new MutableLiveData<>();

    public LiveData<Integer> getTopupValue() {
        return topupValue;
    }

    public void setTopupValue(int value){
        topupValue.setValue(value);
    }


}
