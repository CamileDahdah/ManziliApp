package com.example.camilledahdah.manzili.models.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by camilledahdah on 3/9/18.
 */

public class SpeechResponse {

    @SerializedName("results")
    List<SpeechResultsData> speechResultsDataList;


    public List<SpeechResultsData> getSpeechResultsDataList() {
        return speechResultsDataList;
    }
}
