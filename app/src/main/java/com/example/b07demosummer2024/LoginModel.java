package com.example.b07demosummer2024;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginModel {

    private final FirebaseAuth mAuth;
    private final DatabaseReference database;

    public LoginModel(){
        this.mAuth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.
                getInstance("https://b07project-725cc-default-rtdb.firebaseio.com/")
                .getReference();
    }

    public void login(String email, String password, LoginPresenter presenter){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null){
                    presenter.onLoginSuccess(user);
                } else{
                    presenter.onLoginFailure("User not found after successful login");
                }
            } else{
                presenter.onLoginFailure("Invalid email or password.");
            }
        });
    }

    public void checkUserData(String uid, LoginPresenter presenter){
        database.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot){
                if (snapshot.exists()){
                    boolean hasYearlyData = snapshot.child("Yearly Data").exists();
                    presenter.onUserDataCheckComplete(hasYearlyData);
                } else{
                    presenter.onUserDataCheckComplete(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError error){
                presenter.onUserDataCheckError("Error retrieving user data: " + error.getMessage());
            }
        });
    }

    public void resetPassword(String email, LoginPresenter presenter){
        database.child("users").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    mAuth.sendPasswordResetEmail(email);
                    presenter.onResetPasswordSuccess();
                } else{
                    presenter.onResetPasswordFailure("Email does not exists on database");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                presenter.onResetPasswordFailure("Error with resetting your email");
            }
        });
    }
}
