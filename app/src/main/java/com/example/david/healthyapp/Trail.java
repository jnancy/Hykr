package com.example.david.healthyapp;

public class Trail {
    String name;
    String length;
    String difficulty;
    String summary;
    String high;
    String location;

    Trail(String name, String length, String difficulty, String summary, String high, String location) {
        this.name=name;
        this.length=length;
        this.difficulty=difficulty;
        this.summary=summary;
        this.high=high;
        this.location=location;

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


