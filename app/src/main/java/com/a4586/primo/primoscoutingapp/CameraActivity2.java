package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class CameraActivity2 extends AppCompatActivity {

    static final int REQUEST_CODE_PICTURE = 101;
    ImageView viewImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera2);

        Button btnCamera = (Button) findViewById(R.id.btnCamera);
        viewImage = (ImageView)findViewById(R.id.imageView);



        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_PICTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50,bos);
            viewImage.setImageBitmap(bitmap);

    }
}
