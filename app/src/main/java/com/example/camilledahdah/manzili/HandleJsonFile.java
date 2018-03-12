package com.example.camilledahdah.manzili;

import android.content.Context;

import com.example.camilledahdah.manzili.models.Json.ObjectData;
import com.example.camilledahdah.manzili.models.Json.ObjectList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by camilledahdah on 3/12/18.
 */

public class HandleJsonFile {

    private Context context;
    private String writePath;
    String fileName = "Objects.json";
    int currentObject = 0;

    ObjectList objectList;

    public HandleJsonFile(Context context){
        this.context = context;
        writePath =  context.getFilesDir().getAbsolutePath() + fileName;
        loadJSONFromAsset();
    }

    public void loadJSONFromAsset() {

        String json = null;

        try {

            InputStream is = context.getAssets().open("Json/" + fileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            Gson gson = new Gson();

            objectList = gson.fromJson(json, ObjectList.class);



        } catch (IOException ex) {
            ex.printStackTrace();
        }



    }

    public ObjectList getObjectList() {
        return objectList;
    }

    public void setObjectList(ObjectList objectList) {
        this.objectList = objectList;
    }

    public ObjectData getObjectData(String id){

        int counter = 0;

        for (ObjectData objectData: objectList.getObjectsList()) {

            if(objectData.getId() == id){
                currentObject = counter;
                return objectData;
            }

            counter++;
        }

        return null;
    }

    public ObjectData getCurrentObjectData(){

        return objectList.getObjectsList().get(currentObject);

    }

    public void setVisitedObject(boolean visited){

        objectList.getObjectsList().get(currentObject).setVisited(visited);

    }


}
