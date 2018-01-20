package com.example.matt.calisthenicsappv1.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;

public class DisplayRandomRoutineAct extends AppCompatActivity {

    ArrayList<ExerciseObject> passedExerciseObjectList;
    Boolean toggleSuggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_random_routine);

        Bundle bundledObjects = getIntent().getExtras();

        passedExerciseObjectList = (ArrayList<ExerciseObject>) bundledObjects.getSerializable("ExerciseList");

        toggleSuggestions = (Boolean) bundledObjects.get("Suggestions");


        //need to display the objectas e.t.c


    }
}
