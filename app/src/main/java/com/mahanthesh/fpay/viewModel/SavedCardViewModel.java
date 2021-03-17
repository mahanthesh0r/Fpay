package com.mahanthesh.fpay.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahanthesh.fpay.model.SavedCardModel;
import com.mahanthesh.fpay.repository.SavedCardRepository;

import java.util.List;

public class SavedCardViewModel extends ViewModel implements SavedCardRepository.OnFirestoreTaskComplete {

    private SavedCardRepository savedCardRepository = new SavedCardRepository(this);
    private MutableLiveData<List<SavedCardModel>> savedCardLiveData = new MutableLiveData<>();
    private MutableLiveData<String> savedCardMessage = new MutableLiveData<>();

    public LiveData<String> getSavedCardMessage() {
        return savedCardMessage;
    }

    public LiveData<List<SavedCardModel>> getSavedCardLiveData() {
        return savedCardLiveData;
    }

    public SavedCardViewModel(){
        savedCardRepository.getSavedCards();
    }

    public void postSavedCard(SavedCardModel savedCardModel){
        savedCardRepository.postSavedCards(savedCardModel);
    }


    @Override
    public void onGetSavedCards(List<SavedCardModel> savedCardModelList) {
        savedCardLiveData.setValue(savedCardModelList);
    }

    @Override
    public void onError(Exception e) {
        savedCardMessage.setValue("Error  " + e.getLocalizedMessage());
    }

    @Override
    public void onCardSaved() {
        savedCardMessage.setValue("Card Saved");
    }

    @Override
    public void onCardSaveError() {
        savedCardMessage.setValue("Card Save Error");

    }
}
