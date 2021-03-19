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
import com.mahanthesh.fpay.model.TransactionModel;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private OnFirestoreTaskComplete onFirestoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firebaseFirestore.collection("User").document(FirebaseAuth.getInstance().getUid()).collection("transactions");

    public TransactionRepository(OnFirestoreTaskComplete onFirestoreTaskComplete){
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;

    }

    public void getTransaction(){
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    onFirestoreTaskComplete.onGetTransactions(task.getResult().toObjects(TransactionModel.class));
                } else {
                    onFirestoreTaskComplete.onError(task.getException());
                }
            }
        });

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots != null){
                    List<TransactionModel> transactionModels = new ArrayList<>();
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        if(doc != null){
                            transactionModels.add(doc.toObject(TransactionModel.class));
                            onFirestoreTaskComplete.onGetTransactions(transactionModels);
                        }
                    }
                }
                if(e != null){
                    onFirestoreTaskComplete.onError(e);
                }
            }
        });
    }



    public void setTransaction(TransactionModel transactionModel){
        collectionReference.add(transactionModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                onFirestoreTaskComplete.onSaveTransactionSuccess(documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onFirestoreTaskComplete.onError(e);
            }
        });
    }

    public interface OnFirestoreTaskComplete{
        void onSaveTransactionSuccess(String id);
        void onGetTransactions(List<TransactionModel> transactionModelList);
        void onError(Exception e);
    }
}
