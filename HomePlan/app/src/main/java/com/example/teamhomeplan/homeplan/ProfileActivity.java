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

import com.example.teamhomeplan.homeplan.callback.ImageDownloadedCallback;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Session;
import com.example.teamhomeplan.homeplan.helper.Utilities;
import com.example.teamhomeplan.homeplan.tasks.DownloadImageTask;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProfileActivity extends ActionBarActivity implements ImageDownloadedCallback {
    private static final int SELECT_PHOTO = 100;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    Bitmap newProfilePicture;

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
        if((avatarUrl != null) && !avatarUrl.equals(""))
        {
            DownloadImageTask imgService = new DownloadImageTask(this, avatarUrl);
            imgService.execute();
        } else {
            avatarImage.setImageDrawable(getResources().getDrawable(R.drawable.person_placeholder));
        }

        EditText personName = (EditText) findViewById(R.id.txt_Profile_Name);
        personName.setText(Session.authenticatedUser.getName());

        //Set the click listener on the select button
        findViewById(R.id.profile_change_avatar).setOnClickListener(onSelectAvatarClicked);
    }

    protected View.OnClickListener onSelectAvatarClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
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
}
