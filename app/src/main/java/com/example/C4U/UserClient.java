package com.example.C4U;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserClient {
    @Multipart
    @POST("/money")
    Call<String> uploadPhotoMoney(
            @Part() MultipartBody.Part photo
    );

    @Multipart
    @POST("/color")
    Call<String> uploadPhotoColor(
            @Part() MultipartBody.Part photo
    );
}