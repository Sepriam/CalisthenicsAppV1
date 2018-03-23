package com.example.matt.calisthenicsappv1.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.calisthenicsappv1.Adapters.DisplayRandomExercisesAdapter;
import com.example.matt.calisthenicsappv1.Adapters.DisplayRandomExercisesAndSuggestionAdapter;
import com.example.matt.calisthenicsappv1.Database.AppDBHandler;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;

public class DisplayRandomRoutineAct extends AppCompatActivity {

    TextView textView ;

    Button start, pause, reset, lap ;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    Handler handler;

    int Seconds, Minutes, MilliSeconds ;

    ArrayList<ExerciseObject> passedExerciseObjectList;
    Boolean toggleSuggestions;

    ListView displayRandomRoutineListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_random_routine);

        initiateWidgets();

        setTimerOnClickListeners();

        //Retrieving the bundled content
        Bundle bundledObjects = getIntent().getExtras();
        //Copying arraylist to global arraylist for class
        passedExerciseObjectList = (ArrayList<ExerciseObject>) bundledObjects.getSerializable("ExerciseList");

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

    public void initiateWidgets()
    {
        //Initialising the ListView
        displayRandomRoutineListview = (ListView)findViewById(R.id.DisplayRandomRoutineListView);
        textView = (TextView)findViewById(R.id.textView);
        start = (Button)findViewById(R.id.button);
        pause = (Button)findViewById(R.id.button2);
        reset = (Button)findViewById(R.id.button3);
    }

    public void setTimerOnClickListeners()
    {
        handler = new Handler() ;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                reset.setEnabled(true);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                textView.setText("00:00:00");

            }
        });
    }


    public void displayTrueListViewLayout(ArrayList<ExerciseObject> _trueExercises)
    {
        //create a list adapter for the Listview
        ListAdapter trueListAdapter = new DisplayRandomExercisesAndSuggestionAdapter(this,
                R.layout.customlv_display_exercise_and_suggestions,
                passedExerciseObjectList);

        //assign custom adapter to listview
        displayRandomRoutineListview.setAdapter(trueListAdapter);

        //setting on click listener
        displayRandomRoutineListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getBaseContext(), exerciseExplanationAct.class);
                ExerciseObject tempObject = passedExerciseObjectList.get(position);
                intent.putExtra("itemPassed", tempObject);
                startActivity(intent);
            }
        });
    }

    public void displayFalseListViewLayout(ArrayList<ExerciseObject> _falseExercises)
    {
        //create a list adapter for the Listview
        ListAdapter falseListAdapter = new DisplayRandomExercisesAdapter(this,
                R.layout.customlv_display_exercise,
                passedExerciseObjectList);

        //assign custom adapter to listview
        displayRandomRoutineListview.setAdapter(falseListAdapter);

        //setting on click listener
        displayRandomRoutineListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getBaseContext(), exerciseExplanationAct.class);
                ExerciseObject tempObject = passedExerciseObjectList.get(position);
                intent.putExtra("itemPassed", tempObject);
                startActivity(intent);
            }
        });
    }


    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            textView.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };


    public void saveRoutine(View view) {

        //creating new view
        View newView = (LayoutInflater.from(DisplayRandomRoutineAct.this)).inflate(R.layout.newroutinename_prompt, null);

        //creating a new dialog window for prompot
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(DisplayRandomRoutineAct.this);
        alertBuilder.setView(newView);

        //assiging editText variable to widget
        final EditText userInput = (EditText) newView.findViewById(R.id.newroutinenameprompt_ET);

        //setting an onclick method for the positiveAction button
        alertBuilder.setCancelable(true).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tempName = userInput.getText().toString();
                createNewRoutine(tempName);
                Toast.makeText(getApplicationContext(), "Saved Routine", Toast.LENGTH_LONG).show();
            }
        });

        Dialog dialog = alertBuilder.create();
        dialog.show();
    }


    public void createNewRoutine(String _routineName)
    {
        AppDBHandler db = new AppDBHandler(this);

        db.addNewRoutine(_routineName, passedExerciseObjectList);

        db.close();
    }

}
