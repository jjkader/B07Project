package com.example.b07demosummer2024;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import android.widget.EditText;
import android.widget.TextView;

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

    @Mock
    TextView noticeText;

    @Mock
    EditText textEmail, textPass;

    @Test
    public void testEmptyResetPassword() {
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.resetPassword("");

        verify(view).showText("Please enter a valid email");
        verifyNoMoreInteractions(model);
    }
}