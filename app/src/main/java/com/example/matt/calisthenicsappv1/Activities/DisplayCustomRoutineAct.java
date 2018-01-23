package com.example.matt.calisthenicsappv1.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.matt.calisthenicsappv1.Adapters.DisplayRandomExercisesAndSuggestionAdapter;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;

public class DisplayCustomRoutineAct extends AppCompatActivity {

    ArrayList<ExerciseObject> passedExerciseObjectList;

    ListView displayCustomRoutineListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_custom_routine);

        //Initialising the ListView
        displayCustomRoutineListview = (ListView)findViewById(R.id.DisplayCustomRoutineListView);

        //Retrieving the bundled content
        Bundle bundledObjects = getIntent().getExtras();
        //Copying arraylist to global arraylist for class
        passedExerciseObjectList = (ArrayList<ExerciseObject>) bundledObjects.getSerializable("CheckedExerciseList");

        //create a list adapter for the Listview
        ListAdapter trueListAdapter = new DisplayRandomExercisesAndSuggestionAdapter(this,
                R.layout.customlv_display_exercise_and_suggestions,
                passedExerciseObjectList);

        //assign custom adapter to listview
        displayCustomRoutineListview.setAdapter(trueListAdapter);


    }
}
