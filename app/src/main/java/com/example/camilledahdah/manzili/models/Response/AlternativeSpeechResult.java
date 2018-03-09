package com.example.camilledahdah.manzili.models.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by camilledahdah on 3/9/18.
 */

class AlternativeSpeechResult {

    @SerializedName("transcript")
    private String transcript;

    @SerializedName("confidence")
    private Double confidence;


    public String getTranscript() {
        return transcript;
    }

    public Double getConfidence() {
        return confidence;
    }

}
