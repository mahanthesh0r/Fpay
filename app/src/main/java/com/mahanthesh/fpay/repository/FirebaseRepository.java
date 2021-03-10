package com.mahanthesh.fpay.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mahanthesh.fpay.model.UserInfo;

public class FirebaseRepository {

    private OnFirestoreTaskComplete onFirestoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference userRef = firebaseFirestore.collection("User");
    private DocumentReference userDocRef = firebaseFirestore.collection("User").document(FirebaseAuth.getInstance().getUid());



    public FirebaseRepository(OnFirestoreTaskComplete onFirestoreTaskComplete){
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void getUserData(){
        userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);
                if(userInfo != null){
                    onFirestoreTaskComplete.userDataAdded(userInfo);
                }
            }
        });
    }

    public void setUserData(UserInfo userInfo){
        userRef.document(FirebaseAuth.getInstance().getUid())
                .set(userInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        onFirestoreTaskComplete.userDataSaved();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onFirestoreTaskComplete.onSaveError(e);

                    }
                });
    }

    public interface OnFirestoreTaskComplete {
        void userDataAdded(UserInfo userInfo);
        void userDataSaved();
        void onFetchError(Exception e);
        void onSaveError(Exception e);

    }
}
