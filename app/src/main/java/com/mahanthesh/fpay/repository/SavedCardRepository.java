package com.mahanthesh.fpay.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mahanthesh.fpay.model.SavedCardModel;

import java.util.ArrayList;
import java.util.List;

public class SavedCardRepository {

    private OnFirestoreTaskComplete onfirestoreTaskComplete;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firebaseFirestore.collection("User").document(FirebaseAuth.getInstance().getUid()).collection("saved_cards");

    public SavedCardRepository(OnFirestoreTaskComplete onFirestoreTaskComplete){
        this.onfirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void getSavedCards(){
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    onfirestoreTaskComplete.onGetSavedCards(task.getResult().toObjects(SavedCardModel.class));

                } else {
                        onfirestoreTaskComplete.onError(task.getException());
                }
            }
        });

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots != null){
                    List<SavedCardModel> savedCardModels = new ArrayList<>();
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        if(doc != null){
                            savedCardModels.add(doc.toObject(SavedCardModel.class));
                            onfirestoreTaskComplete.onGetSavedCards(savedCardModels);
                        }
                    }
                }
                if(e != null){
                    onfirestoreTaskComplete.onError(e);
                }
            }
        });
    }

    public void postSavedCards(SavedCardModel savedCardModel){
        collectionReference.add(savedCardModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                onfirestoreTaskComplete.onCardSaved();
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onfirestoreTaskComplete.onCardSaveError();
                    }
                });

    }

    public interface OnFirestoreTaskComplete {
        void onGetSavedCards(List<SavedCardModel> savedCardModelList);
        void onError(Exception e);
        void onCardSaved();
        void onCardSaveError();
    }
}
