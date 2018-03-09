package com.example.camilledahdah.manzili.models.Post;

import com.google.gson.annotations.SerializedName;

/**
 * Created by camilledahdah on 3/9/18.
 */

public class SpeechConfiguration {

    @SerializedName("encoding")
    String encoding;

    @SerializedName("sampleRateHertz")
    String sampleRateHertz;

    @SerializedName("language_code")
    String languageCode;

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setSampleRateHertz(String sampleRateHertz) {
        this.sampleRateHertz = sampleRateHertz;
    }

    public void setLanguage_code(String language_code) {
        this.languageCode = language_code;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getSampleRateHertz() {
        return sampleRateHertz;
    }

    public String getLanguage_code() {
        return languageCode;
    }
}
