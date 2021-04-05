package com.example.ocrcam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static android.speech.RecognizerIntent.EXTRA_LANGUAGE;
import static android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL;
import static android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{


    private Button ocrBt;
    private Button geoBt;
    private Button moneyBt;
    private TextView moneyVal;
    private Button voiceBt;
    private TextView textView;
    private Intent speechIntent;
    private SpeechRecognizer speechRec;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        moneyBt = findViewById(R.id.moneyBt);
        moneyBt.setOnClickListener(this::getMoneyValue);
        //
        ocrBt = findViewById(R.id.ocrBt);
        geoBt = findViewById(R.id.geoBt);

        voiceBt = findViewById(R.id.voiceBt);
        textView = findViewById(R.id.moneyVal);


        ocrBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OcrActivity.class);
                startActivity(intent);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
        }

        speechRec = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
        speechRec.setRecognitionListener(new RecognitionListener() {

            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
                textView.setText("Listenning ...");
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {
                textView.setText(""+error);
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> res = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String listString = "";

                for (String s : res)
                {
                    listString += s + " ";
                }
                textView.setText(listString);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(EXTRA_LANGUAGE_MODEL,LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(EXTRA_LANGUAGE,"fr-FR");

        voiceBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechRec.startListening(speechIntent);
            }
        });

        moneyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoneyDetectActivity.class);
                startActivity(intent);
            }
        });


        geoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GeoActivity.class);
                startActivity(intent);
            }
        });
    }

    //TAZI
    @SuppressLint("SetTextI18n")
    public void getMoneyValue(View view) {
        Intent imgTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(imgTakeIntent,100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + System.currentTimeMillis(), null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new MoneyValue(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        this.textView.setText(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }

}