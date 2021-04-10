package com.mahanthesh.fpay.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class WalletBalanceRepository {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference colRef = firebaseFirestore.collection("User");
    private DocumentReference userDocRef = firebaseFirestore.collection("User").document(FirebaseAuth.getInstance().getUid());
    private OnFirestoreTaskComplete onFirestoreTaskComplete;
    private static final String TAG = "WalletBalanceRepository";

    public WalletBalanceRepository(OnFirestoreTaskComplete onFirestoreTaskComplete){
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }


    public void getWalletBalance(){
       userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Long amount = (Long) documentSnapshot.get("wallet_balance");
                    onFirestoreTaskComplete.onGetWalletBalance(amount);
                } else {
                    onFirestoreTaskComplete.onError();
                }
           }
       });

       userDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
               if(e == null){
                   if(documentSnapshot.exists()){
                       Long amount = (Long) documentSnapshot.get("wallet_balance");
                       onFirestoreTaskComplete.onGetWalletBalance(amount);
                   } else {
                       onFirestoreTaskComplete.onError();
                   }
               } else{
                   Log.d(TAG, "onEvent: " + e);
                   onFirestoreTaskComplete.onError();
               }
           }
       });
    }

    public void setWalletBalance(final Long amount){
        userDocRef.update("wallet_balance", FieldValue.increment(amount)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                    onFirestoreTaskComplete.onSetWalletBalance(amount);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               onFirestoreTaskComplete.onSetError(e);
            }
        });
    }



    public interface OnFirestoreTaskComplete {
        void onGetWalletBalance(Long amount);
        void onError();
        void onSetWalletBalance(Long amount);
        void onSetError(Exception e);
    }
}
