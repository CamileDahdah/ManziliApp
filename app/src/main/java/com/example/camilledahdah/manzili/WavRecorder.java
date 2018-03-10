package com.example.camilledahdah.manzili;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Base64;
import android.util.Log;

import org.apache.commons.io.FileUtils;

public class WavRecorder {

    private static final int RECORDER_BPP = 16;
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.wav";
    private static final int RECORDER_SAMPLERATE = 16000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    short[] audioData;

    private AudioRecord recorder = null;
    private int bufferSize = 0;
    private Thread recordingThread = null;
    private boolean isRecording = false;
    int[] bufferData;
    int bytesRecorded;
    Context context;

    public WavRecorder(Context context) {
        bufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
                RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING) * 2;


        audioData = new short[bufferSize]; // short array that pcm data is put
        // into.

        this.context = context;

        initializeTempFilename();


    }


    private void initializeTempFilename() {

        File file = new File(context.getFilesDir(), AUDIO_RECORDER_FOLDER);

        if (!file.exists()) {
            file.mkdirs();
        }

        File tempFile = new File(file.toString(), AUDIO_RECORDER_TEMP_FILE);

        if (tempFile.exists()) {
            tempFile.delete();
        }

        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (tempFile.exists()) {
            Log.d("s","s");
        }

    }

    public String getTempFileName() {

        File file = new File(context.getFilesDir(), AUDIO_RECORDER_FOLDER);

        File tempFile = new File(file.toString(), AUDIO_RECORDER_TEMP_FILE);

        return (tempFile.toString());
    }

    public void startRecording() {

        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                RECORDER_SAMPLERATE, RECORDER_CHANNELS,
                RECORDER_AUDIO_ENCODING, bufferSize);

        int i = recorder.getState();

        if (i == 1) {
            recorder.startRecording();
        }

        isRecording = true;

        recordingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                writeAudioDataToFile();
            }
        }, "AudioRecorder Thread");

        recordingThread.start();
    }

    private void writeAudioDataToFile() {

        byte data[] = new byte[bufferSize];
        String filename = getTempFileName();

        int read = 0;

            while (isRecording) {
                read = recorder.read(data, 0, bufferSize);

                if (read > 0) {

                }

                if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                    try {
                        FileUtils.writeByteArrayToFile(new File(filename), data, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("ow", "jOWW!");
                    }
                }
            }

    }

    public void stopRecording() {
        if (null != recorder) {
            isRecording = false;

            int i = recorder.getState();

            if (i == 1) {
                recorder.stop();
            }

            recorder.release();

            recorder = null;
            recordingThread = null;
        }

    }


}