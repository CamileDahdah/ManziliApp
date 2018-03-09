package com.example.camilledahdah.manzili.models.Post;

import com.google.gson.annotations.SerializedName;

/**
 * Created by camilledahdah on 3/9/18.
 */

public class SpeechAudio {

    @SerializedName("content")
    String content;

    public void setContent(String content) {
        this.content = content;
    }


    public String getContent() {
        return content;
    }

}
