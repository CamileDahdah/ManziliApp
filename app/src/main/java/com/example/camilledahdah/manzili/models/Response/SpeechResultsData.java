package com.example.camilledahdah.manzili.models.Response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by camilledahdah on 3/9/18.
 */

public class SpeechResultsData {

    @SerializedName("alternatives")
    private List<AlternativeSpeechResult> alternativeSpeechResultList;


    public List<AlternativeSpeechResult> getAlternativeSpeechResultList() {
        return alternativeSpeechResultList;
    }

}
