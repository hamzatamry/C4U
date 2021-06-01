package com.example.C4U;

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

    public static Boolean isPushedToStack = false;
    private TextRecognizer recognizer;
    TextToSpeech textToSpeech;
    TextToSpeech.OnInitListener listener;
    private static final int REQUEST_IMAGE_CAPTURE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        OcrActivity.isPushedToStack = true;
        super.onCreate(savedInstanceState);

        Intent imgTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(imgTakeIntent, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");

            recognizer = new TextRecognizer.Builder(OcrActivity.this).build();
            Frame frame = new Frame.Builder().setBitmap(imgBitmap).build();
            SparseArray<TextBlock> sparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < sparseArray.size(); i++) {
                TextBlock tb = sparseArray.get(i);
                String res = tb.getValue();
                stringBuilder.append("\n" + res);
            }
            //textView.setText(stringBuilder);
            textToSpeech.speak(stringBuilder.toString(), TextToSpeech.QUEUE_FLUSH, null);
        }
        if (requestCode == 100 && resultCode == RESULT_CANCELED) {
            finish();
        }
        finish();
    }


    protected void onDestroy() {
        super.onDestroy();
        OcrActivity.isPushedToStack = false;
    }
}

