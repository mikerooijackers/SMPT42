package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teamhomeplan.homeplan.callback.AuthenticateCallback;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.services.AuthenticateUserService;


/**
 * Activity for logging in to the application.
 */
public class LoginActivity extends Activity implements AuthenticateCallback {

    private LoginActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.context = this;

        //Set the click listener for the login button
        Button loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(loginButtonClickListener);

        //Set the register button click handler
        findViewById(R.id.btnRegister).setOnClickListener(registerButtonClickListener);

    }

    private View.OnClickListener loginButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View loginButton) {
            TextView errorText = (TextView) findViewById(R.id.txtError);

            String email = ((EditText) findViewById(R.id.txtLoginEmail)).getText().toString();
            String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

            if (email == null) {
                errorText.setText("Username must be entered");
                return;
            }

            if (password == null) {
                errorText.setText("Password must be entered");
                return;
            }


            User userToRegister = new User(email, password);

            AuthenticateUserService authenticationService = new AuthenticateUserService(context, userToRegister);
            authenticationService.execute();
        }
    };

    private View.OnClickListener registerButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    };


    @Override
    public void afterSuccesfullyAuthenticated(User user) {
        //TODO: Show the main screen.
    }

    @Override
    public void afterAuthenticationFailed(ServiceException error) {
        TextView errorText = (TextView) findViewById(R.id.txtError);
        ((EditText) findViewById(R.id.txtPassword)).setText("");

        errorText.setText(error.getExceptionMessage());
    }
}
