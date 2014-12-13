package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamhomeplan.homeplan.callback.RegistrationCallback;
import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Utilities;
import com.example.teamhomeplan.homeplan.tasks.RegistrationTask;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RegisterActivity extends Activity implements RegistrationCallback {
    private static final int SELECT_PHOTO = 100;
    private String selectedImageEncoded = null;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private View loader;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.btnRegister).setOnClickListener(onRegisterButtonClick);

        ((TextView) findViewById(R.id.lblError)).setText("");

        //Placeholder drawable.
        Drawable d = getResources().getDrawable(R.drawable.person_placeholder);
        ((ImageView) findViewById(R.id.register_imgView_Avatar)).setImageDrawable(d);

        //profile picture click handler
        findViewById(R.id.register_btnChooseAvatar).setOnClickListener(onChooseAvatarClick);

        this.loader = findViewById(R.id.registerLoader);
    }

    protected View.OnClickListener onChooseAvatarClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {

                        Uri selectedImage = data.getData();
                        Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                        ((ImageView) findViewById(R.id.register_imgView_Avatar)).setImageBitmap(image);
                        this.selectedImageEncoded = Utilities.encodeTobase64(image);
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, ex.toString());
                    }
                }
                break;
        }

    }

    private View.OnClickListener onRegisterButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View registerButton) {
            TextView errorLabel = (TextView) findViewById(R.id.lblError);

            String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
            String name = ((EditText) findViewById(R.id.txtName)).getText().toString();
            String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();
            String passConfirm = ((EditText) findViewById(R.id.txtRegisterPasswordConfirm)).getText().toString();

            if (email.equals("")) {
                errorLabel.setText("Please enter your email");
                return;
            }
            if (name.equals("")) {
                errorLabel.setText("Please enter your name");
                return;
            }
            if (password.equals("")) {
                errorLabel.setText("Please enter a password");
                return;
            }

            if (passConfirm.equals("")) {
                errorLabel.setText("Please confirm your password");
                return;
            }

            if (!passConfirm.equals(password)) {
                errorLabel.setText("Your passwords don't match.");
                return;
            }
            if (!Utilities.isEmailValid(email)) {
                errorLabel.setText("Please enter a valid email address.");
                return;
            }

            loader.setVisibility(View.VISIBLE);
            User userToRegister = new User(email, password);
            userToRegister.setName(name);

            RegistrationTask registerService = new RegistrationTask(RegisterActivity.this, userToRegister, selectedImageEncoded);
            registerService.execute();
        }
    };

    @Override
    public void afterRegistrationSuccessful(User registeredUser) {
        loader.setVisibility(View.GONE);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("User", registeredUser);
        this.setResult(1, resultIntent);

        this.finish();
    }

    @Override
    public void afterRegistrationFailed(ServiceException exception) {
        loader.setVisibility(View.GONE);
        TextView errorLabel = (TextView) findViewById(R.id.lblError);
        errorLabel.setText(exception.getExceptionMessage());
    }
}
