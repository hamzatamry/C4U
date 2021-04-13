package com.example.ocrcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.Locale;

public class OcrActivity extends AppCompatActivity {


    private ImageView imgView;
    private Button TakePicButt;
    private TextRecognizer recognizer;
    private TextView textView;
    private Button ttsButton;
    TextToSpeech textToSpeech;
    TextToSpeech.OnInitListener listener;
    private static final int REQUEST_IMAGE_CAPTURE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocr_activity);

        Intent imgTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(imgTakeIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(imgTakeIntent,100);
        }
        
        imgView = findViewById(R.id.imageView);
        TakePicButt = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        ttsButton = findViewById(R.id.button2);

        listener = status -> {
            if(status != TextToSpeech.ERROR){
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        };
        textToSpeech = new TextToSpeech(getApplicationContext(), listener);
        ttsButton.setOnClickListener(v -> {
            String ttsSpeech = textView.getText().toString();
            textToSpeech.speak(ttsSpeech,TextToSpeech.QUEUE_FLUSH,null);

        });

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
    public void onPause() {
        super.onPause();
        textToSpeech.shutdown();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);


            recognizer = new TextRecognizer.Builder(OcrActivity.this).build();
            Frame frame = new Frame.Builder().setBitmap(imgBitmap).build();
            SparseArray<TextBlock> sparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i<sparseArray.size(); i++){
                TextBlock tb = sparseArray.get(i);
                String res = tb.getValue();
                stringBuilder.append("\n"+res);
            }
            textView.setText(stringBuilder);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        textToSpeech = new TextToSpeech(this, listener);
    }
}

