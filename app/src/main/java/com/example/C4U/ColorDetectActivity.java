package com.example.C4U;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ColorDetectActivity extends AppCompatActivity {

    public static Boolean isPushedToStack = false;
    private final Bundle queryBundle = new Bundle();
    private static TextToSpeech textToSpeech;
    private static String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ColorDetectActivity.isPushedToStack = true;
        super.onCreate(savedInstanceState);

        int permission1 = ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        if (permission1 != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                    102
            );
        }

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
            Uri tempUri = getImageUri(getApplicationContext(), imgBitmap);
            queryBundle.putString("queryString", getRealPathFromURI(tempUri));
            String queryString = queryBundle.getString("queryString");

            File photo = new File(queryString);
            RequestBody filePart = RequestBody.create(
                    MediaType.parse(queryString),
                    photo
            );
            MultipartBody.Part file = MultipartBody.Part.createFormData("file", photo.getName(), filePart);
            //Retrofit instance
            Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
            //get client and call object for the request
            UserClient client = retrofit.create(UserClient.class);
            //execute the request
            Call<String> call = client.uploadPhotoColor(file);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        new RequestTask().execute("https://www.thecolorapi.com/id?hex=" + response.body());
                    } else {
                        value = "Upload error or server down";
                        textToSpeech.speak(value, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    value = "Problem due to internet connexion or storage permissions";
                    textToSpeech.speak(value, TextToSpeech.QUEUE_FLUSH, null);
                }
            });
        }
        if (requestCode == 100 && resultCode == RESULT_CANCELED) {
            finish();
        }
        finish();
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

    static class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(result);
            value = json.get("name").getAsJsonObject().get("value").getAsString();
            textToSpeech.setLanguage(Locale.ENGLISH);
            textToSpeech.speak("This is " + value + " color", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ColorDetectActivity.isPushedToStack = false;
    }
}