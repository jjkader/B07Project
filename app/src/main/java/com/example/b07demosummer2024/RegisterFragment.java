package com.example.b07demosummer2024;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegisterFragment extends LoginFragment {

    private EditText firstNameText, lastNameText, passConfirmText;
    private String first_name, last_name;


    public RegisterFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button buttonRegister = view.findViewById(R.id.buttonRegisterCred);

        firstNameText = (EditText) view.findViewById(R.id.editTextFirstName);
        lastNameText = (EditText) view.findViewById(R.id.editTextLastName);
        emailText = (EditText) view.findViewById(R.id.editTextRegEmail);
        passText = (EditText) view.findViewById(R.id.editTextRegPassword);
        passConfirmText = (EditText) view.findViewById(R.id.editTextConfirmPass);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCredentials();
                // TODO: Send confirmation email, add account to database
            }
        });

        return view;
    }

    @Override
    public boolean checkFields() {
        super.checkFields();
        if (!passConfirmText.getText().toString().equals(passText.getText().toString())) {
            return false;
        }
        // TODO: add check for null and processing for valid/invalid name
        return true;
    }

    @Override
    public void setCredentials() {
        if (checkFields()) {
            first_name = firstNameText.getText().toString();
            last_name = lastNameText.getText().toString();
            email = emailText.getText().toString();
            password = passText.getText().toString();
        }
        // TODO: if checkFields() fails, add message/error handling
    }
}