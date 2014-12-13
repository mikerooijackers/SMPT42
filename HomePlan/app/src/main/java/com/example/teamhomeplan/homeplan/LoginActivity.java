package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.teamhomeplan.homeplan.callback.AuthenticateCallback;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.helper.Session;
import com.example.teamhomeplan.homeplan.tasks.AuthenticateUserTask;
import com.pnikosis.materialishprogress.ProgressWheel;


/**
 * Activity for logging in to the application.
 */
public class LoginActivity extends Activity implements AuthenticateCallback {

    private LoginActivity context;
    private final int REQUEST_CODE = 1;
    private RelativeLayout loader;

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

        RelativeLayout loader = (RelativeLayout) findViewById(R.id.loginLoaded);
        this.loader =loader;
    }

    private View.OnClickListener loginButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View loginButton) {
            TextView errorText = (TextView) findViewById(R.id.txtError);

            String email = ((EditText) findViewById(R.id.txtLoginEmail)).getText().toString();
            String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

            if (email.equals("")) {
                errorText.setText("Username must be entered");
                return;
            }

            if (password.equals("")) {
                errorText.setText("Password must be entered");
                return;
            }


            User userToRegister = new User(email, password);

            loader.setVisibility(View.VISIBLE);
            AuthenticateUserTask authenticationService = new AuthenticateUserTask(context, userToRegister);
            authenticationService.execute();
        }
    };

    private View.OnClickListener registerButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == REQUEST_CODE) {
            User newUser = data.getParcelableExtra("User");
            if (newUser != null) {
                ((EditText) findViewById(R.id.txtLoginEmail)).setText(newUser.getEmail());
                ((TextView) findViewById(R.id.txtError)).setText("Woohoo. Succesfully registered!");
            }
        }

    }

    @Override
    public void afterSuccesfullyAuthenticated(User user) {
        //TODO: Show the main screen.
        Session.authenticatedUser = user;

        Intent intent = new Intent(this, HomeProfileActivity.class);
        startActivity(intent);
        loader.setVisibility(View.GONE);
        finish();
    }

    @Override
    public void afterAuthenticationFailed(ServiceException error) {
        loader.setVisibility(View.GONE);
        TextView errorText = (TextView) findViewById(R.id.txtError);
        ((EditText) findViewById(R.id.txtPassword)).setText("");

        errorText.setText(error.getExceptionMessage());
    }
}
