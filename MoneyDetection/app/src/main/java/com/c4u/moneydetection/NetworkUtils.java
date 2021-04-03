package com.c4u.moneydetection;

import android.util.Log;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        Call<String> call=client.uploadPhoto(file);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call,
                                   Response<String> response) {
                value=response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                value ="Upload error";
            }

        });
        return value;






//        HttpURLConnection urlConnection = null;
//        BufferedReader reader = null;
//        String value = null;
//
//        try {
//            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon().build();
//            URL requestURL = new URL(builtURI.toString());
////            URL requestURL = new URL(BOOK_BASE_URL);
//            urlConnection = (HttpURLConnection) requestURL.openConnection();
//            urlConnection.setRequestMethod("POST");
//            urlConnection.connect();
//            InputStream inputStream = urlConnection.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder builder = new StringBuilder();
//            builder.append(reader.readLine());
//            if (builder.length() == 0) {
//                return null;
//            }
//            value = builder.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        Log.d("From networkUtil", value);
//        return value;
    }
}
