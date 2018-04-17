package com.example.matt.calisthenicsappv1.Activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.matt.calisthenicsappv1.Database.AppDBHandler;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.List;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        //creating connection to database class
        AppDBHandler db = new AppDBHandler(this);
        //checks if exercises are in table, adds default if false
        checkExercises(db);
    }

    //function to add exercises to list if first time run
    public void checkExercises(AppDBHandler db)
    {
        //get all exercises and put into exercise list
        List<ExerciseObject> exerciseList = db.getAllCExercises();

        //if there are no exercises in the list, add exercises
        if (exerciseList.size() == 0)
        {
            Log.d("Insert: ", "No Exercises found, Inserting default exercises..");
            //add default values to sqlite database
            db.setDefaultValues();
            // Reading all contacts
            Log.d("Reading: ", "Reading all exercises..");
            //re-get all exercises after default values written to database
            exerciseList = db.getAllCExercises();
        }
        else
        {
            //else just log that program is reading the entries
            Log.d("Reading: ", "Found exercises, beginning read..");
        }

        for (ExerciseObject cn : exerciseList) {
            String log = "EName: " + cn.getExerciseName() + " ,MGroup: " + cn.getMuscleGroup() + " ,Difficulty: " + cn.getDifficulty() +
                    " ,LowerRepRange: " + String.valueOf(cn.getLowerRepRange()) + " ,UpperRepRange: " + String.valueOf(cn.getUpperRepRange()) +
                    " ,SuggestedTime: " + String.valueOf(cn.getSuggestedTime()) + " ,Selected: " + String.valueOf(cn.isSelected());
            // Writing Contacts to log
            Log.d("Exercise: ", log);
        }
    }

    //onclick method for the custom routine btn
    public void ToSelectPage(View v)
    {
        //creating intent to start new activity
        Intent intent = new Intent(this, selectingExerciseTypesAct.class);
        //creating and passing a string that includes the type of routine selected
        String routineType = "custom";
        intent.putExtra("routineType", routineType);
        Log.d("Passing routine Type:", routineType);
        Log.d("Starting activity:", "selectingExerciseTypesAct");
        startActivity(intent);
    }

    //onclick function for the random routine selection btn
    public void ToRandomSettingPage(View v)
    {
        //creating an intent to start next activity
        Intent intent = new Intent(this, selectingExerciseTypesAct.class);
        //creating and passing a string that includes the type of routine selected
        String routineType = "random";
        intent.putExtra("routineType", routineType);
        Log.d("Passing routine Type:", routineType);
        Log.d("Starting activity:", "selectingExerciseTypesAct");
        startActivity(intent);
    }

    //onclick function for the saved routines btn
    public void toSavedRoutinesAct(View view) {
        //creating the new intent and starting the next activity
        Intent intent = new Intent(this, DisplaySavedRoutinesActivity.class);
        Log.d("Starting activity:","DisplaySavedRoutinesActivity");
        startActivity(intent);
    }
}
