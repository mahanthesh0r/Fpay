package com.mahanthesh.fpay.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

public class TransferFromWalletRepository {


    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference colRef = firebaseFirestore.collection("User");
    private OnFirestoreTaskComplete onFirestoreTaskComplete;

    public TransferFromWalletRepository(OnFirestoreTaskComplete onFirestoreTaskComplete){
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void updateWalletBalance(String senderID, final String receiverID, final Long amount){

        //Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        //Debit for sender
        DocumentReference senderRef = colRef.document(senderID);
        batch.update(senderRef,"wallet_balance", FieldValue.increment(-amount));

        //Credit for receiver
        DocumentReference receiverRef = colRef.document(receiverID);
        batch.update(receiverRef,"wallet_balance",FieldValue.increment(amount));

        //Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onFirestoreTaskComplete.onTransferSuccess(receiverID, amount);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onFirestoreTaskComplete.onTransferFail(e);
            }
        });
    }

    public interface OnFirestoreTaskComplete {
        void onTransferSuccess(String receiverID,Long amount);
        void onTransferFail(Exception e);
    }

}
