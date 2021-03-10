package com.mahanthesh.fpay.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahanthesh.fpay.model.UserInfo;
import com.mahanthesh.fpay.repository.FirebaseRepository;

public class ProfileViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);
    private MutableLiveData<UserInfo> userInfoLiveData = new MutableLiveData<>();

    public LiveData<UserInfo> getUserInfoLiveData() {
        return userInfoLiveData;
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

    }

    @Override
    public void onFetchError(Exception e) {

    }

    @Override
    public void onSaveError(Exception e) {

    }
}
