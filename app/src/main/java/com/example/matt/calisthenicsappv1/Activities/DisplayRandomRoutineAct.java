package com.example.matt.calisthenicsappv1.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matt.calisthenicsappv1.Adapters.DisplayRandomExercisesAdapter;
import com.example.matt.calisthenicsappv1.Adapters.DisplayRandomExercisesAndSuggestionAdapter;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;

public class DisplayRandomRoutineAct extends AppCompatActivity {

    ArrayList<ExerciseObject> passedExerciseObjectList;
    Boolean toggleSuggestions;

    ListView displayRandomRoutineListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_random_routine);

        //Initialising the ListView
        displayRandomRoutineListview = (ListView)findViewById(R.id.DisplayRandomRoutineListView);

        //Retrieving the bundled content
        Bundle bundledObjects = getIntent().getExtras();
        //Copying arraylist to global arraylist for class
        passedExerciseObjectList = (ArrayList<ExerciseObject>) bundledObjects.getSerializable("ExerciseList");

        /*
        Small check to see if the objects passed -- Did as of 20/01/18 -- 13:00
        if (passedExerciseObjectList.size() == 0)
        {
            Toast.makeText(getApplicationContext(), "did not pass any objects", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "passed " + passedExerciseObjectList.size() + " Objects", Toast.LENGTH_SHORT).show();
        }*/

        //Copying boolean value passed into a global boolean for the class
        toggleSuggestions = (Boolean) bundledObjects.get("Suggestions");
        //Check to see whether the boolean value passed correctly
        Toast.makeText(getApplicationContext(), "Boolean value: " + toggleSuggestions.toString(), Toast.LENGTH_SHORT).show();


        //check to find which adapter to assign to listview
        if (toggleSuggestions)
        {
            displayTrueListViewLayout(passedExerciseObjectList);
        }
        else
        {
            displayFalseListViewLayout(passedExerciseObjectList);
        }

    }

    public void displayTrueListViewLayout(ArrayList<ExerciseObject> _trueExercises)
    {
        //create a list adapter for the Listview
        ListAdapter trueListAdapter = new DisplayRandomExercisesAndSuggestionAdapter(this,
                R.layout.customlv_display_exercise_and_suggestions,
                passedExerciseObjectList);

        //assign custom adapter to listview
        displayRandomRoutineListview.setAdapter(trueListAdapter);
    }

    public void displayFalseListViewLayout(ArrayList<ExerciseObject> _falseExercises)
    {
        //create a list adapter for the Listview
        ListAdapter falseListAdapter = new DisplayRandomExercisesAdapter(this,
                R.layout.customlv_display_exercise,
                passedExerciseObjectList);

        //assign custom adapter to listview
        displayRandomRoutineListview.setAdapter(falseListAdapter);
    }
}
