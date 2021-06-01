package com.example.C4U;

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
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Locale;

import static android.speech.RecognizerIntent.EXTRA_LANGUAGE;
import static android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL;
import static android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM;

public class VoiceActivity extends AppCompatActivity {
    private Button talkbtn;
    private Intent speechIntent;
    private SpeechRecognizer speechRec;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    private final static int REQUEST_CODE = 1;
    TextToSpeech textToSpeech;
    TextToSpeech.OnInitListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        listener = status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        };

        textToSpeech = new TextToSpeech(getApplicationContext(), listener);
        textToSpeech.setLanguage(Locale.ENGLISH);

        talkbtn = findViewById(R.id.voice_btn);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
        }

        speechRec = SpeechRecognizer.createSpeechRecognizer(VoiceActivity.this);
        speechRec.setRecognitionListener(new RecognitionListener() {

            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
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
                ArrayList<String> res = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                Context context = getApplicationContext();

                for (String s : res) {
                    switch (s.toLowerCase()) {
                        case "text":
                            Intent intent1 = new Intent(VoiceActivity.this, OcrActivity.class);
                            startActivity(intent1);
                            break;
                        case "money":
                            Intent intent2 = new Intent(VoiceActivity.this, MoneyDetectActivity.class);
                            startActivity(intent2);
                            break;
                        case "localisation":
                            Intent intent3 = new Intent(VoiceActivity.this, GeoActivity.class);
                            startActivityForResult(intent3, REQUEST_CODE);
                            break;
                        case "color":
                            Intent intent4 = new Intent(VoiceActivity.this, ColorDetectActivity.class);
                            startActivity(intent4);
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
        speechIntent.putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(EXTRA_LANGUAGE, "fr-FR");

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
                Intent intent = new Intent(VoiceActivity.this, Parametre.class);
                startActivity(intent);
                return true;
            case R.id.help:
                textToSpeech.setLanguage(Locale.ENGLISH);
                String help = "Press the Talk button, then say text, money, localisation or color";
                textToSpeech.speak(help, TextToSpeech.QUEUE_FLUSH, null);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        textToSpeech = new TextToSpeech(this, listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("loc")) {
                String localisation = data.getExtras().getString("loc");
                textToSpeech.speak(localisation, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }
}