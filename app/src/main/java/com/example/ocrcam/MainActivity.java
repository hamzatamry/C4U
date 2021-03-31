package com.example.ocrcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class MainActivity extends AppCompatActivity {

    private ImageView imgView;
    private Button TakePicButt;
    private TextRecognizer recognizer;
    private TextView textView;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);
        TakePicButt = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        TakePicButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(imgTakeIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(imgTakeIntent,100);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);


            recognizer = new TextRecognizer.Builder(MainActivity.this).build();
            Frame frame = new Frame.Builder().setBitmap(imgBitmap).build();
            SparseArray<TextBlock> sparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i<sparseArray.size(); i++){
                TextBlock tb = sparseArray.get(i);
                String res = tb.getValue();
                stringBuilder.append(res);
            }
            textView.setText(stringBuilder);
        }
    }


}