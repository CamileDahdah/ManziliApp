package com.example.camilledahdah.manzili.models.Json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by camilledahdah on 3/12/18.
 */

public class ObjectList {

    @SerializedName("objects")
    private List<ObjectData> objectsList;


    public List<ObjectData> getObjectsList() {
        return objectsList;
    }

    public void setObjectsList(List<ObjectData> objectsList) {
        this.objectsList = objectsList;
    }

}
