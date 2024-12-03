package com.example.b07demosummer2024;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.b07demosummer2024.AnnualCarbon.AnnualCarbonActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;

public class LoginActivity extends AppCompatActivity {
    private EditText emailText, passText;
    private String email, password;
    private FirebaseAuth mAuth;

    public LoginActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton logo = findViewById(R.id.planetzeLogo);
        Button buttonLoginCred = findViewById(R.id.buttonLoginCred);
        Button buttonPassReset = findViewById(R.id.buttonPassReset);

        emailText = (EditText)findViewById(R.id.editTextEmail);
        passText = (EditText)findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();

        logo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://planetze.io/"));
                startActivity(browserIntent);
            }
        });

        buttonLoginCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCredentials();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    FirebaseUser user = auth.getCurrentUser();
                                    String uid = user.getUid();
                                    DatabaseReference db = FirebaseDatabase.getInstance(
                                            "https://b07project-725cc-default-rtdb.firebaseio.com/")
                                            .getReference();
                                    DatabaseReference userRef = db.child("users").child(uid);

                                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                DataSnapshot yearlyData = snapshot.child("Yearly Data");
                                                if (yearlyData.exists()) {
                                                    // Returning user, send to navigation
                                                    openNavigation();
                                                } else {
                                                    // no annual data
                                                    openAnnualCarbon();
                                                }
                                            } else {
                                                // no user data (should not occur)
                                                openAnnualCarbon();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(LoginActivity.this,
                                                    "Error Authenticating User", Toast.LENGTH_SHORT).show();
                                        }
                                    });



                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid email or password",
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
                    Toast.makeText(LoginActivity.this, "Please Provide Email", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

                    usersRef.orderByChild("email").equalTo(email)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        resetPassword();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println("Error: " + databaseError.getMessage());
                                }
                            });
                }
            }
        });
    }

    private void openAnnualCarbon() {
        Intent myIntent = new Intent(LoginActivity.this,
                AnnualCarbonActivity.class);
        LoginActivity.this.startActivity(myIntent);
        finish();
    }

    private void openNavigation() {
        Intent myIntent = new Intent(
                LoginActivity.this, NavigationActivity.class);
        LoginActivity.this.startActivity(myIntent);
        finish();
    }

    private boolean checkFields() {
        if (TextUtils.isEmpty(emailText.getText().toString())) {
            Toast.makeText(LoginActivity.this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches()) { // check valid email
            Toast.makeText(LoginActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(passText.getText().toString()) || passText.getText().toString().length() < 6) {
            Toast.makeText(LoginActivity.this, "Password must be at least 7 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setCredentials() {
        if (checkFields()) {
            email = emailText.getText().toString();
            password = passText.getText().toString();
        }
    }

    private void resetPassword(){
        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(LoginActivity.this, "Reset password email sent", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}