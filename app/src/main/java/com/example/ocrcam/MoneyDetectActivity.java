package com.example.ocrcam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class MoneyDetectActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    public static Boolean isPushedToStack = false;
    private final Bundle queryBundle = new Bundle();
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MoneyDetectActivity.isPushedToStack = true;

        super.onCreate(savedInstanceState);
        Intent imgTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(imgTakeIntent, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.FRENCH);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            Uri tempUri = getImageUri(getApplicationContext(), imgBitmap);
            queryBundle.putString("queryString", getRealPathFromURI(tempUri));
            if (LoaderManager.getInstance(this).getLoader(0) != null) {
                LoaderManager.getInstance(this).initLoader(0, queryBundle, this);
            }
            LoaderManager.getInstance(this).restartLoader(0, queryBundle, this);
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
        if (data == null) {
            textToSpeech.speak("Upload error or server down", TextToSpeech.QUEUE_FLUSH, null);
        }
        textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
        finish();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MoneyDetectActivity.isPushedToStack = false;
    }
}