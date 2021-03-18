package com.mahanthesh.fpay.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mahanthesh.fpay.model.TransactionModel;

import java.util.List;

public class TransactionRepository {

    private OnFirestoreTaskComplete onFirestoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firebaseFirestore.collection("User").document(FirebaseAuth.getInstance().getUid()).collection("transactions");

    public TransactionRepository(OnFirestoreTaskComplete onFirestoreTaskComplete){
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;

    }

    public void setTransaction(TransactionModel transactionModel){
        collectionReference.add(transactionModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                onFirestoreTaskComplete.onSaveTransactionSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onFirestoreTaskComplete.onError(e);
            }
        });
    }

    public interface OnFirestoreTaskComplete{
        void onSaveTransactionSuccess();
        //void onGetTransactions(List<TransactionModel> transactionModelList);
        void onError(Exception e);
    }
}
