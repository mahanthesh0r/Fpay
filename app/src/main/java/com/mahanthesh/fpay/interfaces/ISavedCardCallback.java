package com.mahanthesh.fpay.interfaces;

import com.mahanthesh.fpay.model.SavedCardModel;

public interface ISavedCardCallback {
    void onSavedCardClick(SavedCardModel savedCardModel);
}
