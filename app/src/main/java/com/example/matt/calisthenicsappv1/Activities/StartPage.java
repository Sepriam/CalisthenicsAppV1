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

    private Button changePageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        changePageBtn = (Button) findViewById(R.id.toSelectPageButton);

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

    public void ToSelectPage(View v)
    {
        Intent intent = new Intent(this, SelectObjectsAct.class);
        startActivity(intent);

    }

    public void ToRandomSettingPage(View v)
    {
        Intent intent = new Intent(this, RandomRoutineSettingsAct.class);
        startActivity(intent);
    }

}
