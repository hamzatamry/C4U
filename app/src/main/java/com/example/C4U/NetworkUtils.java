package com.example.C4U;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class NetworkUtils {
    private static String value=null;


    static String getMoneyValue(String queryString){
        File photo =new File(queryString);
        RequestBody filePart=RequestBody.create(
                MediaType.parse(queryString),
                photo
        );
        MultipartBody.Part file=MultipartBody.Part.createFormData("file",photo.getName(),filePart);
        //Retrofit instance
        Retrofit retrofit=RetrofitClientInstance.getRetrofitInstance();
        //get client and call object for the request
        UserClient client=retrofit.create(UserClient.class);

        //execute the request
        Call<String> call=client.uploadPhotoMoney(file);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call,
                                   Response<String> response) {
                System.out.println("-----1------->> "+value);
                value=response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                value ="Upload error or server down";
            }

        });
        System.out.println("-----out------->> "+value);
        return value;
    }


    static String getColorValue(String queryString){
        File photo =new File(queryString);
        RequestBody filePart=RequestBody.create(
                MediaType.parse(queryString),
                photo
        );
        MultipartBody.Part file=MultipartBody.Part.createFormData("file",photo.getName(),filePart);
        //Retrofit instance
        Retrofit retrofit=RetrofitClientInstance.getRetrofitInstance();
        //get client and call object for the request
        UserClient client=retrofit.create(UserClient.class);

        //execute the request
        Call<String> call=client.uploadPhotoColor(file);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call,
                                   Response<String> response) {
                value = response.body();
                new RequestTask().execute("https://www.thecolorapi.com/id?hex="+value);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                value ="Upload error or server down";
            }

        });
        System.out.println("------------>> "+value);
        return value;
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
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JsonParser parser=new JsonParser();
            JsonObject json= (JsonObject) parser.parse(result);
            value= json.get("name").getAsJsonObject().get("value").getAsString();
        }
    }
}
