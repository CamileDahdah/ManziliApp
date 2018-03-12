package com.example.camilledahdah.manzili.models.Json;

/**
 * Created by camilledahdah on 3/12/18.
 */

public class ObjectData {

    private String id;
    private String lbArabicWord;
    private boolean visited ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLbArabicWord() {
        return lbArabicWord;
    }

    public void setLbArabicWord(String lbArabicWord) {
        this.lbArabicWord = lbArabicWord;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

}
