package com.example.camilledahdah.manzili.api.speechrecognition;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.POST;

import com.example.camilledahdah.manzili.models.Post.SpeechRecognitionInfo;
import com.example.camilledahdah.manzili.models.Response.SpeechResponse;

/**
 * Created by camilledahdah on 3/9/18.
 */

public interface ISpeechRecognition {

    @POST("v1/speech:recognize")
    Call<SpeechResponse> postSpeechTextResult(@Body SpeechRecognitionInfo body, @Header("Content-Type") String contentType);

}
