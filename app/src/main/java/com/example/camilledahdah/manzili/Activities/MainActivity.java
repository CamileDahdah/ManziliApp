package com.example.camilledahdah.manzili.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.example.camilledahdah.manzili.R;
import com.example.camilledahdah.manzili.WavRecorder;
import com.example.camilledahdah.manzili.api.speechrecognition.SpeechRecognitionAPI;
import com.example.camilledahdah.manzili.models.Post.SpeechAudio;
import com.example.camilledahdah.manzili.models.Post.SpeechConfiguration;
import com.example.camilledahdah.manzili.models.Post.SpeechRecognitionInfo;
import com.example.camilledahdah.manzili.models.Response.SpeechResponse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private SpeechRecognitionAPI speechRecognitionAPI;
    final int REQUEST_MICROPHONE = 1;
    TextView speechText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeVariables();


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_MICROPHONE);
        }

        final WavRecorder wavRecorder = new WavRecorder("path_to_file.wav", this);

        wavRecorder.startRecording();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                wavRecorder.stopRecording();

                SpeechRecognitionInfo speechRecognitionInfo = new SpeechRecognitionInfo();
                SpeechConfiguration speechConfiguration = new SpeechConfiguration();
                SpeechAudio speechAudio = new SpeechAudio();

                speechConfiguration.setEncoding("LINEAR16");
                speechConfiguration.setLanguage_code("ar-LB");
                speechConfiguration.setSampleRateHertz("16000");


                File file = new File(wavRecorder.getFilename());
                int size = (int) file.length();
                byte[] bytes = new byte[size];

                try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                    buf.read(bytes, 0, bytes.length);
                    buf.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                String base64Speech = Base64.encodeToString(bytes, Base64.DEFAULT);

                Log.d("s", "succcess! " + base64Speech);
                speechAudio.setContent(base64Speech);
                speechRecognitionInfo.setSpeechAudio(speechAudio);
                speechRecognitionInfo.setSpeechConfiguration(speechConfiguration);

                speechRecognitionAPI = new SpeechRecognitionAPI();

                speechRecognitionAPI.postSpeechTextResult(speechRecognitionInfo, "application/json").enqueue(new Callback<SpeechResponse>() {

                    @Override
                    public void onResponse(Call<SpeechResponse> call, Response<SpeechResponse> response) {

                        if (response.isSuccessful()) {

                            SpeechResponse speechResponse = response.body();

                            if (speechResponse != null && speechResponse.getSpeechResultsDataList() != null) {

                                final String text = speechResponse.getSpeechResultsDataList().get(0).getAlternativeSpeechResultList().get(0).getTranscript();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        speechText.setText(text);
                                    }
                                });

                            }

                        } else {


                        }
                    }

                    @Override
                    public void onFailure(Call<SpeechResponse> call, Throwable t) {

                    }
                });

            }
        }).start();


    }


    private void InitializeVariables() {
        speechText = findViewById(R.id.speech_text);
    }

}
