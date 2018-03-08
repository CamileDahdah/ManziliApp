package com.example.camilledahdah.manzili.models;

/**
 * Created by camilledahdah on 3/9/18.
 */

public class SpeechConfiguration {

    String encoding;
    String sampleRateHertz;
    String language_code;

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setSampleRateHertz(String sampleRateHertz) {
        this.sampleRateHertz = sampleRateHertz;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getSampleRateHertz() {
        return sampleRateHertz;
    }

    public String getLanguage_code() {
        return language_code;
    }
}
