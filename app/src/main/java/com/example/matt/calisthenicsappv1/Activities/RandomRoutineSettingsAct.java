package com.example.matt.calisthenicsappv1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import com.example.matt.calisthenicsappv1.Database.AppDBHandler;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;

public class RandomRoutineSettingsAct extends AppCompatActivity {

    int numOfExercises = 0;

    Button doneButton;
    EditText exerciseNumberTV;
    CheckBox toggleSuggestionsCB;
    //another initiator here for the spinner

    private boolean displaySuggestions = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_routine_settings);

        doneButton = (Button) findViewById(R.id.RandomSettingsDoneBtn);
        exerciseNumberTV = (EditText) findViewById(R.id.numOfExercisesET);
        toggleSuggestionsCB = (CheckBox) findViewById(R.id.toggleSuggestionsCB);

        toggleSuggestionsCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if display suggestions is true then switch to false, else switch to true
                //only works if default the checkbox is unchecked
                if(displaySuggestions)
                    displaySuggestions = false;
                else
                    displaySuggestions = true;
            }
        });

        //random page settings needs the following:
        /*
        Select difficulty
        Select number of exercises
        Time / repetitions suggestions (checkbox)
        Select sets (not required)
        ---------------------------
        future implementation would be good to have a :
        -- create interval timer (one time use)
        -- choose from default interval timers
         */

        //implement drop down list (spinner)
        //pass result to next activity so it can load the correct exercises into view

        //
    }

    //on button click
    public void onDoneButtonClick(View v)
    {

        //get string from the selected spinner
        String difficultySelection;

        //new database connection
        AppDBHandler db = new AppDBHandler(this);

        //use this to grab the exercises
        List<ExerciseObject> difficultyExerciseList = db.getDifficultyExercises(difficultySelection);

        //shuffle the list of exercise objects
        shuffleList(difficultyExerciseList);

        //grab the number from the textbox associated with number of exercises wanted
        //use this to grab that amount of exercises from the shuffled list
        int numOfExercises = Integer.parseInt(exerciseNumberTV.getText().toString());

        //initiate a new arraylist as size will be determined at runtime from user's input
        ArrayList<ExerciseObject> exerciseListToPass =  new ArrayList<>();

        //transfer the first x amount of exercises into new exercise list
        for (int i = 0; i < numOfExercises; i++)
        {
            //note -- not very efficient for memory usage
            //this will transfer only x amount
            exerciseListToPass.add(difficultyExerciseList.get(i));
        }

        //clear the difficultyExerciseList just ot clear memory..
        difficultyExerciseList.clear();



        //Intent intent = new Intent(this, SelectObjectsAct.class);
        //startActivity(intent);
    }


    public void shuffleList(List<ExerciseObject> _exercises)
    {
        int n = _exercises.size();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++)
        {
            int change = i + random.nextInt(n-1);
            swap(_exercises, i, change);
        }
    }

    private static void swap(List<ExerciseObject> _e, int i, int change)
    {
        ExerciseObject helper = _e.get(i);
        _e.set(i, _e.get(change));
        _e.set(change, helper);

    }
}
