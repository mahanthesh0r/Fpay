package com.mahanthesh.fpay.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahanthesh.fpay.model.UserInfo;
import com.mahanthesh.fpay.repository.ProfileRepository;

public class ProfileViewModel extends ViewModel implements ProfileRepository.OnFirestoreTaskComplete {

    private static final String TAG = "ProfileViewModel";

    private ProfileRepository profileRepository = new ProfileRepository(this);
    private MutableLiveData<UserInfo> userInfoLiveData = new MutableLiveData<>();
    private MutableLiveData<String> userInfoMessage = new MutableLiveData<>();
    private MutableLiveData<String> userInfoOnSaveMessage = new MutableLiveData<>();
    private MutableLiveData<UserInfo> receiverInfoLiveData = new MutableLiveData<>();


    public LiveData<UserInfo> getUserInfoLiveData() {
        return userInfoLiveData;
    }

    public MutableLiveData<String> getUserInfoMessage() {
        return userInfoMessage;
    }

    public MutableLiveData<String> getUserInfoOnSaveMessage() {
        return userInfoOnSaveMessage;
    }

    public MutableLiveData<UserInfo> getReceiverInfoLiveData() {
        return receiverInfoLiveData;
    }

    public void setUserInfo(UserInfo userInfo){
        profileRepository.setUserData(userInfo);
    }

    public ProfileViewModel(){
        profileRepository.getUserData();

    }

    public void getReceiverUserData(String uid){
        profileRepository.getReceiverUserData(uid);
    }


    @Override
    public void userDataAdded(UserInfo userInfo) {
        userInfoLiveData.setValue(userInfo);

    }

    @Override
    public void onGetReceiverData(UserInfo userInfo){
        receiverInfoLiveData.setValue(userInfo);
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
