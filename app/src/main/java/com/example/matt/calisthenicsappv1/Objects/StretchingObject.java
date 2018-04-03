package com.example.matt.calisthenicsappv1.Objects;

/**
 * Created by Matt on 03/04/2018.
 */

public class StretchingObject {

    private String stretchingName;
    private int holdTime; //this will need to be acknowledged in seconds
    private String muscleGroup; //this may require an associated muscle group object
    private String stretchingURL;

    public StretchingObject() {
    }

    public StretchingObject(String stretchingName, String muscleGroup, int holdTime, String stretchingURL) {
        this.stretchingName = stretchingName;
        this.muscleGroup = muscleGroup;
        this.holdTime = holdTime;
        this.stretchingURL = stretchingURL;
    }

    public String getStretchingName() {
        return stretchingName;
    }

    public void setStretchingName(String stretchingName) {
        this.stretchingName = stretchingName;
    }

    public int getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(int holdTime) {
        this.holdTime = holdTime;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getStretchingURL() {
        return stretchingURL;
    }

    public void setStretchingURL(String stretchingURL) {
        this.stretchingURL = stretchingURL;
    }
}
