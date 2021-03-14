package com.mahanthesh.fpay.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahanthesh.fpay.model.UserInfo;
import com.mahanthesh.fpay.repository.FirebaseRepository;

public class ProfileViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private static final String TAG = "ProfileViewModel";

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);
    private MutableLiveData<UserInfo> userInfoLiveData = new MutableLiveData<>();
    private MutableLiveData<String> userInfoMessage = new MutableLiveData<>();
    private MutableLiveData<String> userInfoOnSaveMessage = new MutableLiveData<>();


    public LiveData<UserInfo> getUserInfoLiveData() {
        return userInfoLiveData;
    }

    public MutableLiveData<String> getUserInfoMessage() {
        return userInfoMessage;
    }

    public MutableLiveData<String> getUserInfoOnSaveMessage() {
        return userInfoOnSaveMessage;
    }

    public void setUserInfo(UserInfo userInfo){
        firebaseRepository.setUserData(userInfo);
    }

    public ProfileViewModel(){
        firebaseRepository.getUserData();

    }


    @Override
    public void userDataAdded(UserInfo userInfo) {
        userInfoLiveData.setValue(userInfo);

    }

    @Override
    public void userDataSaved() {
        userInfoOnSaveMessage.setValue("Data Saved");
    }

    @Override
    public void onFetchError() {
        Log.d(TAG, "onFetchError: ");
        userInfoMessage.setValue("null");

    }

    @Override
    public void onSaveError(Exception e) {
        userInfoOnSaveMessage.setValue(e.getLocalizedMessage());
    }
}
