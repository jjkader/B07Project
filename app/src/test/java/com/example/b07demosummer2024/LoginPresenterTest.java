package com.example.b07demosummer2024;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit Tests for LoginPresenter.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    LoginModel model;

    @Mock
    LoginView view;

    LoginPresenter presenter;

    @Before
    public void init() {
        presenter = new LoginPresenter(view, model);
    }

    @Test
    public void testEmptyEmailLogin() {
        String email = "";
        String password = "testing";

        presenter.login(email, password);

        verify(view).showText("Please enter an email");
        verifyZeroInteractions(model);
    }

    @Test
    public void testInvalidEmailLogin() {
        String email = "invalid.com";
        String password = "testing";

        presenter.login(email, password);

        verify(view).showText("Please enter a valid email");
        verifyZeroInteractions(model);
    }

    @Test
    public void testEmptyPasswordLogin() {
        String email = "testing123@gmail.com";
        String password = "";

        presenter.login(email, password);

        verify(view).showText("Invalid password");
        verifyZeroInteractions(model);
    }

    @Test
    public void testValidCredentialLogin() {
        String email = "testing123@gmail.com";
        String password = "testing";

        presenter.login(email, password);

        verify(model).login(email, password, presenter);
    }

    @Test
    public void testEmptyEmailResetPassword() {
        presenter.resetPassword("");

        verify(view).showText("Please provide an email");
        verifyZeroInteractions(model);
    }

    @Test
    public void testNonEmptyEmailResetPassword() {
        String email = "testing123@gmail.com";

        presenter.resetPassword(email);

        verify(model).resetPassword(email, presenter);
    }

    @Test
    public void testLoginSuccess() {
        presenter.onLoginSuccess();

        verify(view).showText("Logging in");
    }

    @Test
    public void testLoginFailure() {
        String errorMessage = "Login Failed";

        presenter.onLoginFailure(errorMessage);

        verify(view).showText(errorMessage);
    }

    @Test
    public void testUserDataCheckSuccess() {
        boolean hasYearlyData = true;

        presenter.onUserDataCheckComplete(hasYearlyData);

        verify(view).openNavigation();
    }

    @Test
    public void testUserDataCheckFailure() {
        boolean hasYearlyData = false;

        presenter.onUserDataCheckComplete(hasYearlyData);

        verify(view).openAnnualCarbon();
    }

    @Test
    public void testUserDataCheckError() {
        String errorMessage = "Failed to retrieve data";

        presenter.onUserDataCheckError(errorMessage);

        verify(view).showText(errorMessage);
    }

    @Test
    public void testResetPasswordSuccess() {
        presenter.onResetPasswordSuccess();

        verify(view).showText("Reset password email sent.");
    }

    @Test
    public void testResetPasswordFailure() {
        String errorMessage = "Error resetting password";

        presenter.onResetPasswordFailure(errorMessage);

        verify(view).showText(errorMessage);
    }
}