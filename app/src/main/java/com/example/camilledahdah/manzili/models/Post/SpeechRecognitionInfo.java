package com.example.camilledahdah.manzili.models.Post;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by camilledahdah on 3/9/18.
 */

public class SpeechRecognitionInfo implements Serializable{

    @SerializedName("config")
    private SpeechConfiguration speechConfiguration;

    @SerializedName("audio")
    private SpeechAudio speechAudio;


    public void setSpeechConfiguration(SpeechConfiguration speechConfiguration) {
        this.speechConfiguration = speechConfiguration;
    }

    public void setSpeechAudio(SpeechAudio speechAudio) {
        this.speechAudio = speechAudio;
    }

    public SpeechConfiguration getSpeechConfiguration() {
        return speechConfiguration;
    }

    public SpeechAudio getSpeechAudio() {
        return speechAudio;
    }

}
