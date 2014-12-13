package com.example.teamhomeplan.homeplan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamhomeplan.homeplan.callback.ImageDownloadedCallback;
import com.example.teamhomeplan.homeplan.callback.RegistrationCallback;
import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Session;
import com.example.teamhomeplan.homeplan.helper.Utilities;
import com.example.teamhomeplan.homeplan.tasks.DownloadImageTask;
import com.example.teamhomeplan.homeplan.tasks.UpdateUserProfileTask;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProfileActivity extends ActionBarActivity implements ImageDownloadedCallback, RegistrationCallback {
    private static final int SELECT_PHOTO = 100;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    Bitmap newProfilePicture = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sign out if not authenticated.
        if (!Session.isAuthenticated()) {
            Session.signOut(this);
        }


        setTitle("Your profile");
        setContentView(R.layout.activity_profile);

        String avatarUrl = Utilities.getAvatarUrl(Session.authenticatedUser);
        ImageView avatarImage = (ImageView) findViewById(R.id.img_Profile);
        if ((avatarUrl != null) && !avatarUrl.equals("")) {
            DownloadImageTask imgService = new DownloadImageTask(this, avatarUrl);
            imgService.execute();
        } else {
            avatarImage.setImageDrawable(getResources().getDrawable(R.drawable.person_placeholder));
        }

        EditText personName = (EditText) findViewById(R.id.txt_Profile_Name);
        personName.setText(Session.authenticatedUser.getName());

        //Set the click listener on the select button
        findViewById(R.id.profile_change_avatar).setOnClickListener(onSelectAvatarClicked);

        //Set the click handler on save changes
        findViewById(R.id.btn_profile_saveChanges).setOnClickListener(onSaveChangesClicked);
    }

    protected View.OnClickListener onSelectAvatarClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        }
    };

    private View.OnClickListener onSaveChangesClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            User user = Session.authenticatedUser;
            TextView errorLabel = (TextView) findViewById(R.id.profile_lblError);

            String newName = ((EditText) findViewById(R.id.txt_Profile_Name)).getText().toString();
            if (newName.equals("")) {
                errorLabel.setText("Your name can't be blank.");
                return;
            }

            user.setName(newName);

            UpdateUserProfileTask task = new UpdateUserProfileTask(ProfileActivity.this, user, newProfilePicture);
            task.execute();
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                        Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                        newProfilePicture = image;
                        ImageView profileImage = (ImageView) findViewById(R.id.img_Profile);
                        profileImage.setImageBitmap(image);
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, ex.toString());
                        //TODO: handle
                    }
                }
                break;
        }
    }

    @Override
    public void onAfterImageDownloaded(Bitmap image) {
        ImageView avatarImage = (ImageView) findViewById(R.id.img_Profile);
        avatarImage.setImageBitmap(image);
    }

    @Override
    public void onAfterDownloadFailed(ServiceException ex) {
        //TODO: handle the exception
    }

    @Override
    public void afterRegistrationSuccessful(User registeredUser) {
        Session.authenticatedUser = registeredUser;
        finish();
    }

    @Override
    public void afterRegistrationFailed(ServiceException exception) {
        TextView errorLabel = (TextView) findViewById(R.id.profile_lblError);
        errorLabel.setText(exception.getExceptionMessage());
    }
}
