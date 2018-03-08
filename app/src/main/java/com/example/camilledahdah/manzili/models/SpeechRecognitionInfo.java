package com.example.camilledahdah.manzili.models;

import java.io.Serializable;

/**
 * Created by camilledahdah on 3/9/18.
 */

public class SpeechRecognitionInfo implements Serializable{

    private SpeechConfiguration speechConfiguration;


    private SpeechAudio speechAudio;


    public void setSpeechConfiguration(SpeechConfiguration speechConfigurations) {
    //    this.speechConfiguration = speechConfiguration;
    }

    public void setSpeechAudio(SpeechAudio speechAudio) {
      //  this.speechAudio = speechAudio;
    }

    public SpeechConfiguration getSpeechConfiguration() {
        return speechConfiguration;
    }

    public SpeechAudio getSpeechAudio() {
        return speechAudio;
    }

}
