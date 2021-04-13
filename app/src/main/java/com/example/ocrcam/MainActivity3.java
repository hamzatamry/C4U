package com.example.ocrcam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import static android.speech.RecognizerIntent.EXTRA_LANGUAGE;
import static android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL;
import static android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM;

public class MainActivity3 extends AppCompatActivity {
    private Button talkbtn;
    private Intent speechIntent;
    private SpeechRecognizer speechRec;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    private String[] options = {"ocr", "argent", "localisation"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        talkbtn=findViewById(R.id.voice_btn);
//        talkbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity3.this, OcrActivity.class);
//                startActivity(intent);
//            }
//        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
        }

        speechRec = SpeechRecognizer.createSpeechRecognizer(MainActivity3.this);
        speechRec.setRecognitionListener(new RecognitionListener() {

            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
//                textView.setText("Listenning ...");
                Toast toast = Toast.makeText(getApplicationContext(),"Listenning....",Toast.LENGTH_SHORT);
                toast.show();
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
//                textView.setText(""+error);
            }

            @Override
            public void onResults(Bundle results) {
                Toast toast;
                int duration = Toast.LENGTH_SHORT;
                ArrayList<String> res = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                Context context = getApplicationContext();

                for (String s : res)
                {
                    switch (s.toLowerCase()){
                        case "ocr":
                            Intent intent1 = new Intent(MainActivity3.this, OcrActivity.class);
                            startActivity(intent1);
                            toast = Toast.makeText(context, s, duration);
                            toast.show();
                            break;
                        case "argent":
                            Intent intent2 = new Intent(MainActivity3.this, MoneyDetectActivity.class);
                            startActivity(intent2);
                            toast = Toast.makeText(context, s, duration);
                            toast.show();
                            break;
                        case "localisation":
                            Intent intent3 = new Intent(MainActivity3.this, GeoActivity.class);
                            startActivity(intent3);
                            toast = Toast.makeText(context, s, duration);
                            toast.show();
                            break;
                    }

                }
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

        talkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechRec.startListening(speechIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.param:
                Intent intent = new Intent(MainActivity3.this, Parametre.class);
                startActivity(intent);
                return true;
            case R.id.help:
                Toast.makeText(this,"Help",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}