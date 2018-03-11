package com.example.camilledahdah.manzili;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.example.camilledahdah.manzili.Activities.MainActivity;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camilledahdah on 3/11/18.
 */

public class ReadImageSequences extends Activity{


    Context context;
    ImageView imageView;

    // File representing the folder that you select using a FileChooser
    static String dir = "";
    List<String> imageDirNames = null;
    String dirName;
    // array of supported extensions (use a List if you prefer)
    static final String[] EXTENSIONS = new String[]{
            "gif", "png", "bmp" // and other formats you need
    };


    public ReadImageSequences(Context context){

        this.context  = context;

        dir = "Sequences";
        imageView = ((Activity) context).findViewById(R.id.spriteImage);
    }

    private void testImages(String dirName) {

        try {
            imageDirNames = Arrays.asList(context.getAssets().list(dir));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (imageDirNames != null) { // make sure it's a directory

                List<String> imageFileNames = null;

                try {
                    imageFileNames =  Arrays.asList( context.getAssets().list(dir + "/" + dirName) );
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(imageFileNames != null) {

                int current = 0;
                int length = imageFileNames.size();
                String fileName = "";

                while (true) {

                    for (current = 0; current < length; current++) {

                        fileName = imageFileNames.get(current);

                        Animate(fileName);

                    }

                    for (current = length - 1; current > 0; current--) {

                        fileName = imageFileNames.get(current);

                        Animate(fileName);

                    }

                }
            }
        }
    }


    private void Animate(String fileName) {

        if (fileName.endsWith(".png")) {

            try {
                InputStream inputStream = context.getAssets().open(dir + "/" + dirName + "/" + fileName);

                if (inputStream == null) {
                    //tem.out.println("file is not png");

                } else {


                }

                final Bitmap img = BitmapFactory.decodeStream(inputStream);
                inputStream.close();

                if(img != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(img);
                        }
                    });

                }

                try {
                    Thread.sleep (1000 / 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            //System.out.println("file is not png");

        }
    }





    public void animateImages(final String dirName){

        setDirName(dirName);

        new Thread(new Runnable() {
            @Override
            public void run() {
                testImages(dirName);
            }
        }).start();
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getDirName() {
        return dirName;
    }

}

