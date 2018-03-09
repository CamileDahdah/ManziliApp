package com.example.camilledahdah.manzili.Activities;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.camilledahdah.manzili.R;
import com.example.camilledahdah.manzili.api.speechrecognition.SpeechRecognitionAPI;
import com.example.camilledahdah.manzili.models.Post.SpeechAudio;
import com.example.camilledahdah.manzili.models.Post.SpeechConfiguration;
import com.example.camilledahdah.manzili.models.Post.SpeechRecognitionInfo;
import com.example.camilledahdah.manzili.models.Response.SpeechResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private SpeechRecognitionAPI speechRecognitionAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SpeechRecognitionInfo speechRecognitionInfo = new SpeechRecognitionInfo();
        SpeechConfiguration speechConfiguration = new SpeechConfiguration();
        SpeechAudio speechAudio = new SpeechAudio();

        speechConfiguration.setEncoding("LINEAR16");
        speechConfiguration.setLanguage_code("ar-LB");
        speechConfiguration.setSampleRateHertz("16000");

        speechAudio.setContent("ASSAASSA");

        speechRecognitionInfo.setSpeechAudio(speechAudio);
        speechRecognitionInfo.setSpeechConfiguration(speechConfiguration);



        speechRecognitionAPI = new SpeechRecognitionAPI();

        speechRecognitionAPI.postSpeechTextResult(speechRecognitionInfo, "application/json").enqueue(new Callback<SpeechResponse>() {
            @Override
            public void onResponse(Call<SpeechResponse> call, Response<SpeechResponse> response) {
                if(response.isSuccessful()){
                    SpeechResponse speechResponse = response.body();
                    if(speechResponse != null){
                        speechResponse.getSpeechResultsDataList().get(0).getAlternativeSpeechResultList().get(0);

                    }
                }else{


                }
            }

            @Override
            public void onFailure(Call<SpeechResponse> call, Throwable t) {

            }
        });
    }

}
