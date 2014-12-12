package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teamhomeplan.homeplan.callback.RegistrationCallback;
import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Utilities;
import com.example.teamhomeplan.homeplan.services.RegistrationService;


public class RegisterActivity extends Activity implements RegistrationCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.btnRegister).setOnClickListener(onRegisterButtonClick);
    }


    private View.OnClickListener onRegisterButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View registerButton) {
            TextView errorLabel = (TextView) findViewById(R.id.lblError);

            String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
            String name = ((EditText) findViewById(R.id.txtName)).getText().toString();
            String password = ((EditText) findViewById(R.id.txtName)).getText().toString();

            if(email.equals("")) {
                errorLabel.setText("Please enter your email");
                return;
            }
            if(name.equals(""))
            {
                errorLabel.setText("Please enter your name");
                return;
            }
            if(password.equals(""))
            {
                errorLabel.setText("Please enter a password");
                return;
            }
            if(!Utilities.isEmailValid(email))
            {
                errorLabel.setText("Please enter a valid email address.");
                return;
            }

            User userToRegister = new User(email, password);
            userToRegister.setName(name);

            //TODO: set the image avatar for registration too. or maybe pass it seperately to the service task.



            RegistrationService registerService = new RegistrationService(RegisterActivity.this, userToRegister);
            registerService.execute();
        }
    };


    @Override
    public void afterRegistrationSuccessful(User registeredUser) {

    }

    @Override
    public void afterRegistrationFailed(ServiceException exception) {
        TextView errorLabel = (TextView) findViewById(R.id.lblError);
        errorLabel.setText(exception.getExceptionMessage());
    }
}
