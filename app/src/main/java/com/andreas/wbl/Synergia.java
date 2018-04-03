package com.andreas.wbl;

/**
 * Created by Andreas on 11/3/2018.
 */

public class Synergia {

    //fields
    private int id;
    private String synergiaName;
    //private boolean epistatis;

    //default constructor
    public Synergia(int id, String synergiaName) {
        this.id = id;
        this.synergiaName = synergiaName;
        //this.epistatis = epistatis;
    }

    //methods set,get
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSynergiaName() {
        return synergiaName;
    }

    public void setSynergiaName(String synergiaName) {
        this.synergiaName = synergiaName;
    }

//    public boolean getEpistatis(){
//        return epistatis;
//    }
//
//    public void setEpistatis(boolean epistatis){
//        this.epistatis = epistatis;
//    }

}