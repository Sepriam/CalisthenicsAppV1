package com.example.matt.calisthenicsappv1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.matt.calisthenicsappv1.R;
import com.example.matt.calisthenicsappv1.Activities.*;

public class selectingExerciseTypesAct extends AppCompatActivity {

    //creating 3 buttons for each decision from this activity
    Button calistheincsSelectBtn, weightedSelectBtn, allSelectBtn;

    //global string variable to retrieve and pass the routine type passsed to activity
    public String routineType;
    //only possibilities for the routine type are 'custom' or 'random'

    //creating static string to pass specific string values across to next activities
    private static String passCalisthenicsString = "calisthenics";
    private static String passWeightedString = "weighted";
    private static String passAllExercisesString = "allExercises";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_routine_type);

        routineType = getIntent().getStringExtra("routineType");
        if (routineType != null) {
            Log.d("Retrieved routineType: ", routineType + ". Successful.");
        }
        initiateWidgets();
    }


    //function to assign widgets to their respective variables in act
    private void initiateWidgets()
    {
        calistheincsSelectBtn = (Button)findViewById(R.id.selectCalisthenicsExercises_btn);
        weightedSelectBtn = (Button)findViewById(R.id.selectWeightedExercises_btn);
        allSelectBtn = (Button)findViewById(R.id.selectAllExercises_btn);
    }


    //onclick functionality for the select Calisthenics btn
    public void selectCalisthenicsRoutine(View view) {
        passToNextAct(passCalisthenicsString);
    }

    //onclick functionality for the select random btn
    public void selectWeightedRoutine(View view) {
        passToNextAct(passWeightedString);
    }

    //onclick functionality for the select all exercises btn
    public void selectAllRoutine(View view) {
        passToNextAct(passAllExercisesString);
    }

    //function to choose next activity and pass correct extras in intent
    private void passToNextAct(String _exerciseType)
    {
        //creating a new intent
        Intent intent;
        //check what value is associated with routineType String
        if (routineType.equals("custom"))
        {
            //creating intent to change activity to selectObjectsAct
            intent = new Intent(this, SelectObjectsAct.class);
            //passing exerciseType selected by user
            intent.putExtra("exerciseType", _exerciseType);
            Log.d("Passing exercise Type:", _exerciseType);
            startActivity(intent);

        }
        else if (routineType.equals("random"))
        {
            //creating intent to change to next activity: RandomRoutineSettingsAct
            intent = new Intent(this, RandomRoutineSettingsAct.class);
            //passing exerciseType selected by user
            intent.putExtra("exerciseType", _exerciseType);
            Log.d("Passing exercise Type:", _exerciseType);
            startActivity(intent);
        }
        else
        {
            //if here, there was an error in the routineType
            Log.d("Error:", "No routineType selected");
        }
    }
}


