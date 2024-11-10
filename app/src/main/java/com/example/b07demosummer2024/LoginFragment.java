package com.example.b07demosummer2024;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends Fragment {

    // TODO: Create "Forgot Password" functionality

    EditText emailText, passText;
    String email, password;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button buttonLoginCred = view.findViewById(R.id.buttonLoginCred);

        emailText = (EditText)view.findViewById(R.id.editTextEmail);
        passText = (EditText)view.findViewById(R.id.editTextPassword);

        buttonLoginCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCredentials();
                // TODO: check if email/pass are in the database, login only if found
            }
        });

        return view;
    }

    public boolean checkFields() {
        // TODO: add check for null and processing for valid/invalid email/password
        return true;
    }

    public void setCredentials() {
        if (checkFields()) {
            email = emailText.getText().toString();
            password = passText.getText().toString();
        }
        // TODO: if checkFields() fails, add message/error handling
    }
}