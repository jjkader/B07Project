package com.example.b07demosummer2024;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseError;

public class LoginFragment extends Fragment{
    EditText emailText, passText;
    String email, password;
    private FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        
        Button buttonLoginCred = view.findViewById(R.id.buttonLoginCred);
        Button buttonPassReset = view.findViewById(R.id.buttonPassReset);

        emailText = (EditText)view.findViewById(R.id.editTextEmail);
        passText = (EditText)view.findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();

        buttonLoginCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCredentials();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    return;
                }
                // TODO: check if email/pass are in the database, login only if found
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    loadFragment(new HomeFragment()); // TODO: Should replace HomeFragment with the next app fragment
                                } else {
                                    Toast.makeText(getContext(), "Invalid email or password",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
               return;
            }
        });

        buttonPassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailText.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Please Provide Email", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

                    usersRef.orderByChild("email").equalTo(email)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        // TODO: Send the user a reset password email
                                        resetPassword();
                                    } else {
                                        Toast.makeText(getContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println("Error: " + databaseError.getMessage());
                                }
                            });
                    // TODO: check if email exists and if it does send auth code and redirect
                }
            }
        });
        return view;
    }


    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    public boolean checkFields() {
        // TODO: add check for null and processing for valid/invalid email/password
        if (TextUtils.isEmpty(passText.getText().toString())|| TextUtils.isEmpty(emailText.getText().toString()) || passText.getText().toString().length() < 6){
            return false;
        }
        return isValidEmail(emailText.getText().toString());
    }

    public void setCredentials() {
        if (checkFields()) {
            email = emailText.getText().toString();
            password = passText.getText().toString();
            return;
        }
        // TODO: if checkFields() fails, add message/error handling
        Toast.makeText(getContext(), "Please enter valid credentials", Toast.LENGTH_SHORT).show();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void resetPassword(){
        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Reset password email sent", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}