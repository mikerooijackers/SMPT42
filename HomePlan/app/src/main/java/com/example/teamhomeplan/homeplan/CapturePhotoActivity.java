package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.teamhomeplan.homeplan.callback.CapturePhotoCallback;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Utilities;
import com.example.teamhomeplan.homeplan.tasks.CapturePhotoTask;


/**
 * Created by Edwin on 19-Jan-15.
 */
public class CapturePhotoActivity extends Activity implements CapturePhotoCallback {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Bitmap photo;
    private String selectedImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_photo);

        imageView = (ImageView) findViewById(R.id.imageView);

        Button btnCapture = (Button) findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    selectedImage = Utilities.encodeTobase64(photo);
                } catch (Exception ex) {
                   ex.printStackTrace();
                }

                if (!selectedImage.equals("")) {
                    CapturePhotoTask task = new CapturePhotoTask(CapturePhotoActivity.this, selectedImage);
                    task.execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Maak eerst een foto!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

    @Override
    public void afterPhotoCapturedSuccess() {
        this.finish();
    }

    @Override
    public void afterPhotoCapturedFailed(ServiceException exception) {
        Toast.makeText(getApplicationContext(), "Er ging iets fout met het verwerken", Toast.LENGTH_SHORT).show();
    }
}
