package com.andreas.wbl;

/**
 * Created by Andreas on 11/3/2018.
 */

public class Synergia {

    //fields
    private int id;
    private String synergioName;
    //private boolean epistatis;

    //default constructor
    public Synergia(int id, String synergioName) {
        this.id = id;
        this.synergioName = synergioName;
        //this.epistatis = epistatis;
    }

    //methods set,get
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSynergioName() {
        return synergioName;
    }

    public void setSynergioName(String synergioName) {
        this.synergioName = synergioName;
    }

//    public boolean getEpistatis(){
//        return epistatis;
//    }
//
//    public void setEpistatis(boolean epistatis){
//        this.epistatis = epistatis;
//    }

}