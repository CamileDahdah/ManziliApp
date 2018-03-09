package com.example.camilledahdah.manzili.api.speechrecognition;

import com.example.camilledahdah.manzili.models.Response.SpeechResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.camilledahdah.manzili.models.Post.SpeechRecognitionInfo;

/**
 * Created by camilledahdah on 3/9/18.
 */

public class SpeechRecognitionAPI {

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private ISpeechRecognition iSpeechRecognitionAPI;

    private final String SPEECHRECOGNITION_BASE_URL = "https://speech.googleapis.com/";
    private static final String API_KEY = "AIzaSyAJpF4Oqx4hEJmefSf6sSwI8-Tg34b76ZI";


    public SpeechRecognitionAPI() {
        Gson gson = new GsonBuilder().create();
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new SpeechRecognitionInterceptor())
                .build();
        retrofit = new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(SPEECHRECOGNITION_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iSpeechRecognitionAPI = retrofit.create(ISpeechRecognition.class);
    }


    public Call<SpeechResponse> postSpeechTextResult(SpeechRecognitionInfo body, String contentType) {
        return iSpeechRecognitionAPI.postSpeechTextResult(body, contentType);
    }


    private static class SpeechRecognitionInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl originalUrl = request.url();
            HttpUrl modifiedUrl = originalUrl
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build();
            Request modifiedRequest = request.newBuilder().url(modifiedUrl).build();
            return chain.proceed(modifiedRequest);
        }
    }


}
