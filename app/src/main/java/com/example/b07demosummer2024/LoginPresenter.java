package com.example.b07demosummer2024;

import android.text.TextUtils;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter {

    private final LoginView view;
    private final LoginModel model;

    public LoginPresenter(LoginView view, LoginModel model){
        this.view = view;
        this.model = model;
    }

    public void login(String email, String password){
        if (!checkFields(email, password)){
            return;
        }
        model.login(email, password, this);
    }

    public void resetPassword(String email){
        if (email.isBlank()){
            view.showText("Please provide an email");
            return;
        }
        model.resetPassword(email, this);
    }

    void onLoginSuccess(){
        view.showText("Logging in");
    }

    void onLoginFailure(String message){
        view.showText(message);
    }

    void onUserDataCheckComplete(boolean hasYearlyData){
        if (hasYearlyData){
            view.openNavigation();
        } else{
            view.openAnnualCarbon();
        }
    }
    void onUserDataCheckError(String s) {
        view.showText(s);
    }

    void onResetPasswordSuccess(){
        view.showText("Reset password email sent.");
    }
    void onResetPasswordFailure(String error) {
        view.showText(error);
    }
    private boolean checkFields(String email, String password) {
        if (email.isBlank()){
            view.showText("Please enter an email");
            return false;
        } else if (!androidx.core.util.PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showText("Please enter a valid email");
            return false;
        } else if (password.isBlank() || password.length() < 6) {
            view.showText("Invalid password");
            return false;
        }
        return true;
    }
}
