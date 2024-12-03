package com.example.b07demosummer2024;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.View;
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


public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameText, lastNameText, emailText, passText, passConfirmText;
    private String first_name, last_name, email, password;


    public RegisterActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton logo = findViewById(R.id.planetzeLogo);
        Button buttonRegister = findViewById(R.id.buttonRegisterCred);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        firstNameText = (EditText) findViewById(R.id.editTextFirstName);
        lastNameText = (EditText) findViewById(R.id.editTextLastName);
        emailText = (EditText) findViewById(R.id.editTextRegEmail);
        passText = (EditText) findViewById(R.id.editTextRegPassword);
        passConfirmText = (EditText) findViewById(R.id.editTextConfirmPass);

        logo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://planetze.io/"));
                startActivity(browserIntent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(myIntent);
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                setCredentials();
                if (auth.getCurrentUser() != null){
                    auth.signOut();
                }
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    sendEmail(user);
                                    User u = new User(first_name, last_name, email, password);
                                    DatabaseReference db = FirebaseDatabase.getInstance(
                                            "https://b07project-725cc-default-rtdb.firebaseio.com/").getReference();
                                    addToDatabase(db, u, user.getUid());
                                    Toast.makeText(RegisterActivity.this, "Redirecting to Login Page!", Toast.LENGTH_LONG).show();
                                    Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(myIntent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private boolean checkFields() {
        if (firstNameText.getText().length() == 0) {
            Toast.makeText(RegisterActivity.this, "Please enter a first name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lastNameText.getText().length() == 0) {
            Toast.makeText(RegisterActivity.this, "Please enter a last name", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(emailText.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches()) { // check valid email
            Toast.makeText(RegisterActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(passText.getText().toString()) || passText.getText().toString().length() < 6) {
            Toast.makeText(RegisterActivity.this, "Password must be at least 7 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!passConfirmText.getText().toString().equals(passText.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Passwords must match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void setCredentials() {
        if (checkFields()) {
            first_name = firstNameText.getText().toString();
            last_name = lastNameText.getText().toString();
            email = emailText.getText().toString();
            password = passText.getText().toString();
        }
        else{
            // Restart Registration
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
    private void sendEmail(FirebaseUser user){
        user.sendEmailVerification()
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Sent verification email!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Unable to send verification email!", Toast.LENGTH_LONG).show();
                            // Restart Registration
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    }
                });

    }
    private void addToDatabase(DatabaseReference db, User u, String uid){
        DatabaseReference key = db.child("users").child(uid);
        key.setValue(u);
        DatabaseReference habits_key = key.child("Habits");
        habits_key.child("1").setValue(-1);
        habits_key.child("2").setValue(-1);
        habits_key.child("3").setValue(-1);
        habits_key.child("4").setValue(-1);
        habits_key.child("5").setValue(-1);
        habits_key.child("6").setValue(-1);
        habits_key.child("7").setValue(-1);
        habits_key.child("8").setValue(-1);
        habits_key.child("9").setValue(-1);
        habits_key.child("10").setValue(-1);
    }
}