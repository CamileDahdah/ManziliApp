package com.example.camilledahdah.manzili.api.speechrecognition;

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

public class SpeechRecognitionAPI {

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private SpeechRecognitionAPI speechRecognitionAPI;

    private final String SPEECHRECOGNITION_BASE_URL = "https://speech.googleapis.com/v1/";

    public SpeechRecognitionAPI() {
        Gson gson = new GsonBuilder().create();
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new OpenWeatherInterceptor())
                .build();
        retrofit = new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(SPEECHRECOGNITION_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        speechRecognitionAPI = retrofit.create(SpeechRecognitionAPI.class);
    }

  //  public Call<SpeechRecognitionInfo> getSpeechTextResult(String key) {
    //    return speechRecognitionAPI.getSpeechTextResult(key);
    //}

    private static class OpenWeatherInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl originalUrl = request.url();
            HttpUrl modifiedUrl = originalUrl
                    .newBuilder()
                    .addQueryParameter("units", "metric")
                    .addQueryParameter("APPID", "33e206167fdb92a1ed1ef5d2b7d42995")
                    .build();
            Request modifiedRequest = request.newBuilder().url(modifiedUrl).build();
            return chain.proceed(modifiedRequest);
        }
    }


}
