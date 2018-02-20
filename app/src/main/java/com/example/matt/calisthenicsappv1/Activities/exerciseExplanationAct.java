package com.example.matt.calisthenicsappv1.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.matt.calisthenicsappv1.R;

public class exerciseExplanationAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_explanation);


        /*
        Todo:
        get extra from intent that passes string values of the exercise selected from previous page's listview

        use string values to search through the database for the exercise corresponding to the one passed

        Grab the video url and the tips from the exercise in database
         */

    }
}
