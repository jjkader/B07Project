package com.example.b07demosummer2024;

import android.text.TextUtils;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter {

    private final LoginView view;
    private final LoginModel model;

    public LoginPresenter(LoginView view){
        this.view = view;
        this.model = new LoginModel();
    }

    public void login(String email, String password){
        if (!checkFields(email, password)){
            return;
        }
        model.login(email, password, this);
    }

    public void resetPassword(String email){
        if (TextUtils.isEmpty(email)){
            view.showToast("Please provide an email");
            return;
        }
        model.resetPassword(email, this);
    }

    public void onLoginSuccess(FirebaseUser user){
        view.showToast("Logging in");
        model.checkUserData(user.getUid(), this);
    }

    public void onLoginFailure(String message){
        view.showToast(message);
    }

    public void onUserDataCheckComplete(boolean hasYearlyData){
        if (hasYearlyData){
            view.openNavigation();
        } else{
            view.openAnnualCarbon();
        }
    }
    public void onUserDataCheckError(String s) {
        view.showToast(s);
    }

    public void onResetPasswordSuccess(){
        view.showToast("Reset password email sent.");
    }
    public void onResetPasswordFailure(String error) {
        view.showToast(error);
    }
    public boolean checkFields(String email, String password) {
        if (TextUtils.isEmpty(email)){
            view.showToast("Please enter an email");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showToast("Please enter a valid email");
            return false;
        } else if (TextUtils.isEmpty(password) || password.length() < 6) {
            view.showToast("Password must be at least 7 characters long");
            return false;
        }
        return true;
    }
}
