package com.example.b07demosummer2024;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends LoginFragment {

    private EditText firstNameText, lastNameText, passConfirmText;
    private String first_name, last_name;


    public RegisterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        ImageButton logo = view.findViewById(R.id.planetzeLogo);
        Button buttonRegister = view.findViewById(R.id.buttonRegisterCred);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);

        firstNameText = (EditText) view.findViewById(R.id.editTextFirstName);
        lastNameText = (EditText) view.findViewById(R.id.editTextLastName);
        emailText = (EditText) view.findViewById(R.id.editTextRegEmail);
        passText = (EditText) view.findViewById(R.id.editTextRegPassword);
        passConfirmText = (EditText) view.findViewById(R.id.editTextConfirmPass);

        logo.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               loadFragment(new HomeFragment());
           }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadFragment(new LoginFragment());
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                setCredentials();
                if (!(checkFields())){
                    loadFragment(new RegisterFragment());
                    return;
                }
                if (auth.getCurrentUser() != null){
                    auth.signOut();
                }
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    sendEmail(user);
                                }
                                else {
                                    Toast.makeText(getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                User u = new User(first_name, last_name, email, password);
                DatabaseReference db = FirebaseDatabase.getInstance(
                        "https://b07project-725cc-default-rtdb.firebaseio.com/").getReference();
                addToDatabase(db, u);
                Toast.makeText(getContext(), "Redirecting to Login Page!", Toast.LENGTH_LONG).show();
                loadFragment(new LoginFragment());
            }
        });

        return view;
    }

    @Override
    public boolean checkFields() {
        if (firstNameText.getText().length() == 0 || lastNameText.getText().length() == 0 || !super.checkFields()) {
            return false;
        }
        return passConfirmText.getText().toString().equals(passText.getText().toString());
        // TODO: add check for null and processing for valid/invalid name
    }

    @Override
    public void setCredentials() {
        if (checkFields()) {
            first_name = firstNameText.getText().toString();
            last_name = lastNameText.getText().toString();
            email = emailText.getText().toString();
            password = passText.getText().toString();
        }
        else{
            Toast.makeText(getContext(), "Invalid registration attempt", Toast.LENGTH_LONG).show();
        }
        // TODO: if checkFields() fails, add message/error handling
    }
    public void sendEmail(FirebaseUser user){
        user.sendEmailVerification()
                .addOnCompleteListener(requireActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Sent verification email!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Unable to send verification email!", Toast.LENGTH_LONG).show();
                            loadFragment(new RegisterFragment());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public void addToDatabase(DatabaseReference db, User u){
        DatabaseReference key = db.child("users").push();
        key.setValue(u);
    }
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}