package com.example.b07demosummer2024;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07demosummer2024.AnnualCarbon.AnnualCarbonActivity;

public class LoginView extends AppCompatActivity {
    private EditText emailText, passText;
    private String email, password;
    private TextView noticeText;

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton logo = findViewById(R.id.planetzeLogo);
        Button buttonLoginCred = findViewById(R.id.buttonLoginCred);
        Button buttonPassReset = findViewById(R.id.buttonPassReset);
        emailText = findViewById(R.id.editTextEmail);
        passText = findViewById(R.id.editTextPassword);
        noticeText = findViewById(R.id.textNotice);
        presenter = new LoginPresenter(this, new LoginModel());

        logo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://planetze.io/"));
                startActivity(browserIntent);
            }
        });

        buttonLoginCred.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                email = emailText.getText().toString();
                password = passText.getText().toString();
                presenter.login(email, password);
            }
        });

        buttonPassReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                email = emailText.getText().toString();
                presenter.resetPassword(email);
            }
        });
    }
    public void showText(String message){
        noticeText.setText(message);
    }
    public void openAnnualCarbon() {
        Intent myIntent = new Intent(LoginView.this,
                AnnualCarbonActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        LoginView.this.startActivity(myIntent);
        finish();
    }
    public void openNavigation() {
        Intent myIntent = new Intent(
                LoginView.this, NavigationActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        LoginView.this.startActivity(myIntent);
        finish();
    }
}