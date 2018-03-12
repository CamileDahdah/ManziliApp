package com.example.camilledahdah.manzili.Activities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieDrawable;
import com.example.camilledahdah.manzili.ArabicText;
import com.example.camilledahdah.manzili.HandleJsonFile;
import com.example.camilledahdah.manzili.R;
import com.example.camilledahdah.manzili.ReadImageSequences;
import com.example.camilledahdah.manzili.WavRecorder;
import com.example.camilledahdah.manzili.api.speechrecognition.SpeechRecognitionAPI;
import com.example.camilledahdah.manzili.models.Post.SpeechAudio;
import com.example.camilledahdah.manzili.models.Post.SpeechConfiguration;
import com.example.camilledahdah.manzili.models.Post.SpeechRecognitionInfo;
import com.example.camilledahdah.manzili.models.Response.SpeechResponse;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import omrecorder.AudioChunk;
import omrecorder.AudioRecordConfig;
import omrecorder.OmRecorder;
import omrecorder.PullTransport;
import omrecorder.PullableSource;
import omrecorder.Recorder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    final int REQUEST_MICROPHONE = 1;
    TextView speechText;
    boolean microphonePermission;
    Recorder recorder ;
    SpeechAudio speechAudio;
    Button speakButton;
    SpeechRecognitionInfo speechRecognitionInfo;
    boolean isRecording;
    TextView youSaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainn);

        initializeVariables();

        checkMicrophonePermission();

        ReadImageSequences readImageSequences = new ReadImageSequences(this);
        readImageSequences.animateImages("Chair");

        initializeSpeechInfo();

        initializeRecorder();

        final myThread resultThread = new myThread(speechAudio, recorder, speechRecognitionInfo, this, speechText);

            speakButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (microphonePermission) {

                        if (!isRecording) {
                            isRecording = true;
                            initializeRecorder();
                            speakButton.setBackgroundResource(R.drawable.speak_button);
                            speakButton.setText("");
                            youSaid.setText("");
                            speakButton.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.buttonanim));
                            recorder.startRecording();


                        } else {
                            speakButton.clearAnimation();
                            isRecording = false;
                            speakButton.setBackgroundResource(R.drawable.blackbutton);
                            speakButton.setText("Tap");
                            resultThread.setRecorder(recorder);
                            resultThread.run();

                        }

                    }else{
                        checkMicrophonePermission();

                    }

                }

            });

        }



    private void checkMicrophonePermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_MICROPHONE);
        }else{
            microphonePermission = true;
        }

    }

    private void animateVoice(float v) {
        //Log.d("voice", "voice: " + v);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        if(requestCode == REQUEST_MICROPHONE){

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                microphonePermission = true;
            }

        }

    }

    private void initializeVariables() {

        isRecording = false;
        microphonePermission = false;
        youSaid = findViewById(R.id.you_said);
        speechText = findViewById(R.id.speech_text);
        speakButton = findViewById(R.id.speak_button);
        Typeface englishTypeFace = Typeface.createFromAsset(getAssets(), "fonts/gogono_cocoa_mochi.ttf");
        Typeface arabicTypeFace = Typeface.createFromAsset(getAssets(), "fonts/b_arabics.ttf");

        speakButton.setBackgroundResource(R.drawable.blackbutton);
        speakButton.setText("Tap");
        youSaid.setText("");

        speakButton.setTypeface(englishTypeFace);
        speechText.setTypeface(arabicTypeFace);
    }

    public void initializeSpeechInfo(){

        speechRecognitionInfo = new SpeechRecognitionInfo();
        SpeechConfiguration speechConfiguration = new SpeechConfiguration();
        speechAudio = new SpeechAudio();

        speechConfiguration.setEncoding("LINEAR16");
        speechConfiguration.setLanguage_code("ar-LB");
        speechConfiguration.setSampleRateHertz("16000");

        speechRecognitionInfo.setSpeechAudio(speechAudio);
        speechRecognitionInfo.setSpeechConfiguration(speechConfiguration);


    }


    public void initializeRecorder(){

        recorder = OmRecorder.wav(
                new PullTransport.Default(new PullableSource.Default(
                        new AudioRecordConfig.Default(
                                MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                                AudioFormat.CHANNEL_IN_MONO, 16000
                        )
                ), new PullTransport.OnAudioChunkPulledListener() {
                    @Override
                    public void onAudioChunkPulled(AudioChunk audioChunk) {
                        animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
                    }
                }), new File(getFilesDir(), "testing.wav"));

    }

    private class myThread implements Runnable {

        SpeechAudio speechAudio;
        Recorder recorder;
        private SpeechRecognitionAPI speechRecognitionAPI;
        Context context;
        private SpeechRecognitionInfo speechRecognitionInfo;
        private  TextView speechText;

        public myThread(SpeechAudio speechAudio, Recorder recorder, SpeechRecognitionInfo speechRecognitionInfo, Context context, TextView speechText) {

            this.speechAudio = speechAudio;
            this.recorder = recorder;
            this.speechRecognitionInfo = speechRecognitionInfo;
            this.context = context;
            this.speechText = speechText;

            speechRecognitionAPI = new SpeechRecognitionAPI();

        }

        public void setRecorder(Recorder recorder) {
            this.recorder = recorder;
        }

        @Override
        public void run () {

            try {

                recorder.stopRecording();

            } catch (IOException e) {
                e.printStackTrace();
            }


            String base64Speech = "";

            try {

                byte[] bytes = FileUtils.readFileToByteArray(new File(context.getFilesDir(), "testing.wav"));
                base64Speech = Base64.encodeToString(bytes, Base64.NO_WRAP);//STUPID BUG Base64.NO_WRAP

            } catch (IOException e) {
                e.printStackTrace();
            }

            speechAudio.setContent(base64Speech);

            speechRecognitionAPI = new SpeechRecognitionAPI();

            speechRecognitionAPI.postSpeechTextResult(speechRecognitionInfo, "application/json").enqueue(new Callback<SpeechResponse>() {

                @Override
                public void onResponse(Call<SpeechResponse> call, Response<SpeechResponse> response) {

                    if (response.isSuccessful()) {

                        SpeechResponse speechResponse = response.body();

                        if (speechResponse != null && speechResponse.getSpeechResultsDataList() != null) {

                            final String text = speechResponse.getSpeechResultsDataList().get(0).getAlternativeSpeechResultList().get(0).getTranscript();

                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    HandleJsonFile handleJsonFile = new HandleJsonFile(context);
                                    String correctWord = handleJsonFile.getCurrentObjectData().getLbArabicWord();

                                    ArabicText.TextResponse textResponse = ArabicText.detectArabicWords(text, correctWord);

                                    if(!textResponse.isCorrect()) {
                                        youSaid.setText("Incorrect Word");
                                    }else{
                                        youSaid.setText("Correct Word");
                                    }
                                    speechText.setText(Html.fromHtml( textResponse.getFinalTextResult() ));

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

    }

}