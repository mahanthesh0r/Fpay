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

public class ProfileRepository {

    private OnFirestoreTaskComplete onFirestoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference userRef = firebaseFirestore.collection("User");
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference userDocRef;



    public ProfileRepository(OnFirestoreTaskComplete onFirestoreTaskComplete){
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void getUserData(){
        if(firebaseUser != null) {
            userDocRef = firebaseFirestore.collection("User").document(firebaseUser.getUid());


            userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);
                    if (userInfo != null) {
                        onFirestoreTaskComplete.userDataAdded(userInfo);
                    } else {
                        onFirestoreTaskComplete.onFetchError();
                    }
                }
            });
        }
    }

    public void getReceiverUserData(String uid){
        userDocRef = firebaseFirestore.collection("User").document(uid);
        userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);
                if (userInfo != null) {
                    onFirestoreTaskComplete.onGetReceiverData(userInfo);
                } else {
                    onFirestoreTaskComplete.onFetchError();
                }
            }
        });
    }

    public void setUserData(UserInfo userInfo){
        if(firebaseUser != null) {
            userRef.document(firebaseUser.getUid())
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
    }

    public interface OnFirestoreTaskComplete {
        void userDataAdded(UserInfo userInfo);
        void userDataSaved();
        void onGetReceiverData(UserInfo userInfo);
        void onFetchError();
        void onSaveError(Exception e);

    }
}
