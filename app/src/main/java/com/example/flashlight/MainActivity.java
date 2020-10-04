package com.example.flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
ImageButton imageButton;
boolean state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.toff);


        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                runFlashlight();


            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                Toast.makeText(MainActivity.this, "Camera permission is required", Toast.LENGTH_SHORT);

            }
            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void runFlashlight() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(!state) {

                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String cameraId = cameraManager.getCameraIdList()[0];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode(cameraId, true);
                            state = true;
                            imageButton.setImageResource(R.drawable.torch_on);
                        }
                    } catch (CameraAccessException e) {
                    }
                }
                    else
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                            try {
                                String cameraId = cameraManager.getCameraIdList()[0];
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    cameraManager.setTorchMode(cameraId, true);
                                    state = false;
                                    imageButton.setImageResource(R.drawable.torch_off);
                                }
                            } catch (@SuppressLint("NewApi") CameraAccessException e) {
                                e.printStackTrace();
                            }
                    }

                }
            }
        });
    }
}