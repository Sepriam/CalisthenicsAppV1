package com.example.matt.calisthenicsappv1.Activities;

import android.app.Activity;
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

import com.example.matt.calisthenicsappv1.Objects.muscleGroupObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;

import com.example.matt.calisthenicsappv1.Database.AppDBHandler;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;

public class RandomRoutineSettingsAct extends AppCompatActivity {

    //creating class int to recall number of exercises selected...
    int numOfExercises = 0;

    public static int REQUEST_CODE = 1;

    //initialising variables for widgets
    Button doneButton, selectMuscleGroupsBtn;
    EditText exerciseNumberTV;
    CheckBox toggleSuggestionsCB, toggleMuscleGroupsCB;
    Spinner selectDifficultySpinner;
    TextView displayRandomExercisesTV;

    //string array for the muscleGroups selected (if any)
    ArrayList<String> _selectedMuscleGroups = new ArrayList<>();

    //exerciseObject Arraylist to share exercises in

    //defining a boolean to toggle whether the suggested time / ranges should be displayed
    private boolean displaySuggestions = false;

    //defining variable to see if the user want to choose specific muscle groups
    private boolean chooseSpecificMuscleGroups = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_routine_settings);

        //initialise the variables to their respective widgets
        doneButton = (Button) findViewById(R.id.RandomSettingsDoneBtn);
        selectMuscleGroupsBtn = (Button) findViewById(R.id.selectMuscleGroupsBtn);
        exerciseNumberTV = (EditText) findViewById(R.id.numOfExercisesET);
        toggleSuggestionsCB = (CheckBox) findViewById(R.id.toggleSuggestionsCB);
        toggleMuscleGroupsCB =  (CheckBox) findViewById(R.id.selectMuscleGroupsCB);
        selectDifficultySpinner = (Spinner) findViewById(R.id.selectDifficultySpinner);

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

        //set on click listener for the checkbox
        toggleMuscleGroupsCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if display suggestions is true then switch to false, else switch to true
                //only works if default the checkbox is unchecked
                if(chooseSpecificMuscleGroups) {
                    //set boolean to false
                    chooseSpecificMuscleGroups = false;
                    //Disable button to select objects page
                    selectMuscleGroupsBtn.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Selected All Muscle Groups", Toast.LENGTH_SHORT).show();
                }
                else {
                    chooseSpecificMuscleGroups = true;
                    selectMuscleGroupsBtn.setEnabled(true);
                }
            }
        });


    }

    //on button click
    public void onDoneButtonClick(View v)
    {
        startNextActivity();
    }

    public void selectSpecificMuscleGroupsActSwitch(View view) {

        //starting new activity for the user to select muscle groups
        Intent i = new Intent(this, selectMuscleGroupsAct.class);
        startActivityForResult(i, REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //checking request code is correct
        if (requestCode == REQUEST_CODE)
        {

            Toast.makeText(getApplicationContext(),
                    "GOT HERE 1", Toast.LENGTH_LONG).show();

            //checking if result code is correct
            if(resultCode == RESULT_OK)
            {
                _selectedMuscleGroups.clear();

                Toast.makeText(getApplicationContext(),
                        "GOT HERE 2", Toast.LENGTH_LONG).show();

                //getting the string array from other activity
                ArrayList<String> resultArray = data.getStringArrayListExtra("_result");

                //not going to check if empty as this is dealt with on the next button press
                _selectedMuscleGroups.addAll(resultArray);

                chooseSpecificMuscleGroups = true;

                startNextActivity();
            }
        }
    }

    //Checks on widgets before activity swap
    private boolean checksBeforeSwap()
    {
        //grab the number from the text box associated with number of exercises wanted
        //use this to grab that amount of exercises from the shuffled list
        if (exerciseNumberTV.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please enter Value for Number of Exercises", Toast.LENGTH_SHORT).show();
            return false;
        }

        //assigning value to numOfExercises
        numOfExercises = Integer.parseInt(exerciseNumberTV.getText().toString());

        if  (numOfExercises <= 0)
        {
            //got here if the number in edit text is either not implemented or it is not great than 0
            Toast.makeText(getApplicationContext(), "Number of Exercises must be greater than 0", Toast.LENGTH_SHORT).show();
            return false;
        }

        //check whether user wants to define own muscleGroups (via CB boolean state)
        if (chooseSpecificMuscleGroups)
        {

            if (_selectedMuscleGroups.size() == 0)
            {
                //make a toast to show user only x amount of exercises available
                Toast.makeText(getApplicationContext(), "No Selected Muscle Groups, Selecting All.", Toast.LENGTH_SHORT).show();
                _selectedMuscleGroups = _returnMuscleGroupsList();
            }
        }
        else
        {
            _selectedMuscleGroups = _returnMuscleGroupsList();
        }

        return true;
    }

    //return the complete list of muscle groups
    private ArrayList<String> _returnMuscleGroupsList()
    {
        //creating an arraylist to return selected muscle groups if any
        ArrayList<String> muscleGroupsSelected = new ArrayList<>();

        //add all the muscle groups to list (not accurate to hard-code in)
        //in future would need to change this to find all types of musclegroup and add it to list
        muscleGroupsSelected.add("Shoulders");
        muscleGroupsSelected.add("Chest");
        muscleGroupsSelected.add("Back");
        muscleGroupsSelected.add("Arms");
        muscleGroupsSelected.add("Core");
        muscleGroupsSelected.add("Legs");

        return muscleGroupsSelected;
    }


    private ArrayList<ExerciseObject> _returnMuscleGroupAndExerciseList(ArrayList<ExerciseObject> _difficultyList )
    {
        //Should not have to pass the array of selected muscleGroups as it's global variable

        ArrayList<ExerciseObject> returnArrayList = new ArrayList<>();

        //loop through all exercises passed
        for (int i = 0; i < _difficultyList.size(); i++)
        {
            //for each exercise passed, loop through all selected muscle groups
            for (int j = 0; j < _selectedMuscleGroups.size(); j++)
            {
                //check if the exercises' muscle group is equal to any of those required
                if (_difficultyList.get(i).getMuscleGroup().equals(_selectedMuscleGroups.get(j)))
                {
                    //if the musclegroup is equal to any of those selected, add it to the array list
                    returnArrayList.add(_difficultyList.get(i));
                }
            }
        }

        return returnArrayList;

    }


    private ArrayList<ExerciseObject> _returnMultipleDifficultiesEList(String _chosenDifficulty)
    {
        //creating an arraylist of Exercise objects that will be returned
        ArrayList<ExerciseObject> toBeReturned = new ArrayList<>();
        //temporary array list for transferring 2+ arraylists
        ArrayList<ExerciseObject> tempArrayList = new ArrayList<>();

        AppDBHandler db = new AppDBHandler(this);

        switch(_chosenDifficulty)
        {
            case "Easy":
                toBeReturned = db.getDifficultyExercises("Easy");
                break;

            case "Easy + Intermediate":
                toBeReturned = db.getDifficultyExercises("Easy");
                tempArrayList =  db.getDifficultyExercises("Intermediate");
                for (int i = 0; i < tempArrayList.size(); i++)
                {
                    toBeReturned.add(tempArrayList.get(i));
                }
                //little memory management
                tempArrayList.clear();
                break;

            case "Intermediate":
                toBeReturned = db.getDifficultyExercises("Intermediate");
                break;

            case "Intermediate + Advanced":
                toBeReturned = db.getDifficultyExercises("Intermediate");
                tempArrayList =  db.getDifficultyExercises("Advanced");
                for (int i = 0; i < tempArrayList.size(); i++)
                {
                    toBeReturned.add(tempArrayList.get(i));
                }
                //little memory management
                tempArrayList.clear();
                break;

            case "Advanced":
                toBeReturned = db.getDifficultyExercises("Advanced");
                break;
        }

        //all cases will break and return here
        //saves on having every case return the arraylist
        return toBeReturned;


    }


    private void startNextActivity()
    {
        //new database connection
        AppDBHandler db = new AppDBHandler(this);

        //if any of the checks return false then don't continue the method
        if (!checksBeforeSwap())
            return;

        //get string from the selected spinner
        String difficultySelection = selectDifficultySpinner.getSelectedItem().toString();

        //calls function to return all exercises of specific difficulty
        ArrayList<ExerciseObject> difficultyExerciseList = _returnMultipleDifficultiesEList(difficultySelection);

        //assigning value to numOfExercises
        numOfExercises = Integer.parseInt(exerciseNumberTV.getText().toString());

        //defining arrayLsit to store the right exercises with selected muscle group and difficulty.
        ArrayList<ExerciseObject> muscleGroupAndDifficultyList = _returnMuscleGroupAndExerciseList(difficultyExerciseList);

        //shuffle the array list of exercises with correct musclegroup and difficulty
        Collections.shuffle(muscleGroupAndDifficultyList);

        //create an if condition to look to see if the number of exercises selected is greater than that of the total exercises in difficultyExerciseList
        if(muscleGroupAndDifficultyList.size() < numOfExercises)
        {
            //make a toast to show user only x amount of exercises available
            Toast.makeText(getApplicationContext(), "Only " + difficultyExerciseList.size() + " exercises available", Toast.LENGTH_SHORT).show();

            //swap the number of exercises selected to be that of the total size of list from database
            numOfExercises = muscleGroupAndDifficultyList.size();
        }

        //defining the last arraylist
        ArrayList<ExerciseObject> exerciseListToPass = new ArrayList<>();


        //--------------------------------------------------------------------------------------------
        //create function to return correct number of easy / intermediate exercises


        //loop to add first x amount from correct array list to new arraylist
        for(int i = 0; i < numOfExercises; i++)
        {
            exerciseListToPass.add(muscleGroupAndDifficultyList.get(i));
        }

        //Garbage collection stuff
        muscleGroupAndDifficultyList.clear();
        difficultyExerciseList.clear();

        //create new intent
        Intent i = new Intent(this, DisplayRandomRoutineAct.class);
        //create a new bundle to encompass ArrayList<ExerciseObject> to be passed
        Bundle passBundle = new Bundle();
        //put the arraylist<exerciseObject> into bundle
        passBundle.putSerializable("ExerciseList", exerciseListToPass);
        //add this to intent putExtras
        i.putExtras(passBundle);
        //adding the boolean to show the user wants the additional suggested reps / time
        i.putExtra("Suggestions", displaySuggestions);
        //starting the next activity
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
