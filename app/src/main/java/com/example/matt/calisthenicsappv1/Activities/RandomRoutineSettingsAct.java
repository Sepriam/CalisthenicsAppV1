package com.example.matt.calisthenicsappv1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;

import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;
import java.util.List;

import com.example.matt.calisthenicsappv1.Database.AppDBHandler;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;

public class RandomRoutineSettingsAct extends AppCompatActivity {

    //creating class int to recall number of exercises selected...
    int numOfExercises = 0;

    //initialising variables for widgets
    Button doneButton;
    EditText exerciseNumberTV;
    CheckBox toggleSuggestionsCB;
    Spinner selectDifficultySpinner;
    TextView displayRandomExercisesTV;

    //defining a boolean to toggle whether the suggested time / ranges should be displayed
    private boolean displaySuggestions = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_routine_settings);

        //initialise the variables to their respective widgets
        doneButton = (Button) findViewById(R.id.RandomSettingsDoneBtn);
        exerciseNumberTV = (EditText) findViewById(R.id.numOfExercisesET);
        toggleSuggestionsCB = (CheckBox) findViewById(R.id.toggleSuggestionsCB);
        selectDifficultySpinner = (Spinner) findViewById(R.id.selectDifficultySpinner);
        displayRandomExercisesTV = (TextView) findViewById(R.id.displayRandomExercisesTV);

        //set the spinner values when the page is created
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.Difficulty_Spinner_Array, android.R.layout.simple_dropdown_item_1line);
        //specifiying the layout used for drop down items
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //applying the adapter to the spinner widget
        selectDifficultySpinner.setAdapter(spinnerAdapter);

        //set on click listener for the checkbox
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


    }

    //on button click
    public void onDoneButtonClick(View v)
    {
        //reset the textview
        displayRandomExercisesTV.setText("");

        //grab the number from the text box associated with number of exercises wanted
        //use this to grab that amount of exercises from the shuffled list
        if (exerciseNumberTV.getText().toString() == null)
        {
            Toast.makeText(getApplicationContext(), "Number of Exercises must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }
        int numOfExercises = Integer.parseInt(exerciseNumberTV.getText().toString());

        //check whether the created int is bigger than 0
        if  (numOfExercises <= 0)
        {
            //got here if the number in edit text is either not implemented or it is not great than 0
            Toast.makeText(getApplicationContext(), "Number of Exercises must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        //get string from the selected spinner
        String difficultySelection = selectDifficultySpinner.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(), "Selected difficulty: " + difficultySelection, Toast.LENGTH_SHORT).show();


        //new database connection
        AppDBHandler db = new AppDBHandler(this);

        //use this to grab the exercises
        List<ExerciseObject> difficultyExerciseList = db.getDifficultyExercises(difficultySelection);

        //--------------------------------------------------------------------------------------------------------------
        //create an if condition to look to see if the number of exercises selected is greater than that of the total exercises in difficultyExerciseList
        if(difficultyExerciseList.size() < numOfExercises)
        {
            //make a toast to show user only x amount of exercises available
            Toast.makeText(getApplicationContext(), "Only " + difficultyExerciseList.size() + " exercises available", Toast.LENGTH_SHORT).show();

            //swap the number of exercises selected to be that of the total size of list from database
            numOfExercises = difficultyExerciseList.size() - 1;
        }

        //shuffle the exercises
        //this shuffle will gaurantee no 2 identical exercises
        Collections.shuffle(difficultyExerciseList);

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


        for(ExerciseObject e : exerciseListToPass)
        {
            displayRandomExercisesTV.append("Exercise: " + e.getExerciseName() + "\n");
        }


        Intent i = new Intent(this, DisplayRandomRoutineAct.class);
        Bundle passBundle = new Bundle();
        passBundle.putSerializable("ExerciseList", exerciseListToPass);
        i.putExtras(passBundle);
        i.putExtra("Suggestions", displaySuggestions);
        startActivity(i);
    }




     /*
     NO LONGER IN USE
    //function to shuffle list
    public void shuffleList(List<ExerciseObject> _exercises, int numOfExercises)
    {

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<numOfExercises; i++) {
            list.add(new Integer(i));
            Toast.makeText(getApplicationContext(), "number of intermediate exercises : " + numOfExercises, Toast.LENGTH_SHORT).show();
        }
        Collections.shuffle(list);

        //cycle through x amount of times
        for (int i = 0; i < numOfExercises-1; i++)
        {
            //swap the exercises around
            swap(_exercises, list.get(i) , list.get(i+1));
        }
    }

    //swap function - used in shuffle function to swap items in list around
    private static void swap(List<ExerciseObject> _e, int i, int change)
    {
        ExerciseObject helper = _e.get(i);
        _e.set(i, _e.get(change));
        _e.set(change, helper);

    }
    */
}
