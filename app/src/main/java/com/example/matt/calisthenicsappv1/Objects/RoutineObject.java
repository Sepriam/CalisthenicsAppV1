package com.example.matt.calisthenicsappv1.Objects;

/**
 * Created by Matt on 20/03/2018.
 */

public class RoutineObject {

    private String RoutineName;
    private Integer R_ID;
    private String[] ExerciseNames;

    //default constructor
    public RoutineObject() {

    }

    //value constructor
    public RoutineObject(String routineName, Integer r_ID, String[] exerciseNames) {
        RoutineName = routineName;
        R_ID = r_ID;
        ExerciseNames = exerciseNames;
    }

    public String getRoutineName() {
        return RoutineName;
    }

    public void setRoutineName(String routineName) {
        RoutineName = routineName;
    }

    public Integer getR_ID() {
        return R_ID;
    }

    public void setR_ID(Integer r_ID) {
        R_ID = r_ID;
    }

    public String[] getExerciseNames() {
        return ExerciseNames;
    }

    public void setExerciseNames(String[] exerciseNames) {
        ExerciseNames = exerciseNames;
    }
}

