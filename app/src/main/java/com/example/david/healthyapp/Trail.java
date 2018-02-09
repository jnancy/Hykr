package com.example.david.healthyapp;

public class Trail {
    String name;
    String length;
    String difficulty;
    String summary;
    String high;
    String location;
    String imgMedium;

    Trail(String name, String length, String difficulty, String summary, String high, String location, String imgMedium) {
        this.name=name;
        this.length=length;
        this.difficulty=difficulty;
        this.summary=summary;
        this.high=high;
        this.location=location;
        this.imgMedium=imgMedium;

    }

    public String getName() {
        return name;
    }

//    public String getLength() {
//        return length;
//    }
//    public String getDifficulty() {
//        return length;
//    }
}


