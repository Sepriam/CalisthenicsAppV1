package com.example.matt.calisthenicsappv1;

import android.util.Log;

/**
 * Created by Matt on 07/01/2018.
 */

public class ExerciseObject {

    /*
    Requirements:
    1) Variables:
    1.1 - String : ExerciseName
    1.2 - String : MuscleGroup ((Enum))
    1.3 - String : Difficulty ((Enum))
    1.4 - Int : LowerRepRange
    1.5 - Int : UpperRepRange
    1.6 - Int : suggestedTime ((For Isometric Movements)) -- Seconds
    1.7 - Boolean : IsSelected (default - false)


    2) Get and Set methods for each variable


    3) Constructor for the an Exercise Object

    3.0 - Default Constructor (No Params)

    3.1 - Default Constructor
    3.1.1 - Param 1 : _ExerciseName
    3.1.2 - Param 2 : _MuscleGroup
    3.1.3 - Param 3 : _Difficulty
    3.1.4 - Param 4 : _lowerRepRange
    3.1.5 - Param 5 : _upperRepRange
    3.1.6 - Param 6 : _time
    3.1.7 - Param 7 : _selected
    3.1.8 - Assign all parameter values to object's variable values

    3.2 - NoRepRangeConstructor
    3.2.1 - Param 1 : _ExerciseName
    3.2.2 - Param 2 : _MuscleGroup
    3.2.3 - Param 3 : _Difficulty
    3.2.4 - Param 4 : _time
    3.2.5 - Param 5 : _selected
    3.2.6 - Assign parameter values to object's variable values
    3.2.7 - Assign default value 0 to variables: LowerRepRange & UpperRepRange


    3.3 - NoTimeValueConstructor
    3.3.1 - Param 1 : _ExerciseName
    3.3.2 - Param 2 : _MuscleGroup
    3.3.3 - Param 3 : _Difficulty
    3.3.4 - Param 4 : _lowerRepRange
    3.3.5 - Param 5 : _upperRepRange
    3.3.6 - Param 6 : _selected
    3.3.7 - Assign parameter values to object's variable values
    3.3.8 - Assign default value 0 to variable : suggestedTime

     */

    private String ExerciseName;
    private String MuscleGroup;
    private String Difficulty;
    private int LowerRepRange;
    private int UpperRepRange;
    private int SuggestedTime;  //Suggested Time in Seconds
    private boolean IsSelected;


    //Default constructor with all values assigned
    public ExerciseObject(String _exerciseName, String _muscleGroup,
                          String _difficulty, int _lowerRepRange,
                          int _upperRepRange, int _suggestedTime)
    {
        //Default Constructor
        this.ExerciseName = _exerciseName;
        this.MuscleGroup = _muscleGroup;
        this.Difficulty = _difficulty;
        this.LowerRepRange = _lowerRepRange;
        this.UpperRepRange = _upperRepRange;
        this.SuggestedTime = _suggestedTime;

        //Set IsSelected to False by default
        this.IsSelected = false;

    }


    public ExerciseObject()
    {
        //Default constructor if empty exercise object needed
    }


    //Default constructor if rep ranges not given
    public ExerciseObject(String _exerciseName, String _muscleGroup,
                          String _difficulty, int _suggestedTime)
    {
        this.ExerciseName = _exerciseName;
        this.MuscleGroup = _muscleGroup;
        this.Difficulty = _difficulty;
        this.SuggestedTime = _suggestedTime;

        //Set lowerRepRange to default 0
        this.LowerRepRange = 0;
        //set upperRepRange to default 0
        this.UpperRepRange = 0;
        //set IsSelected to default false
        this.IsSelected = false;
    }


    //default constructor if no time specified
    public ExerciseObject(String _exerciseName, String _muscleGroup,
                          String _difficulty, int _lowerRepRange,
                          int _upperRepRange)
    {
        this.ExerciseName = _exerciseName;
        this.MuscleGroup = _muscleGroup;
        this.Difficulty = _difficulty;
        this.LowerRepRange = _lowerRepRange;
        this.UpperRepRange = _upperRepRange;

        //Set SuggestedTime to default 0
        this.SuggestedTime = 0;
        //Set IsSelected to False by default
        this.IsSelected = false;
    }


    //Default constructor that receives all objects as strings from table and converts to desired variable as necessary
    public ExerciseObject(String _exerciseName, String _muscleGroup,
                          String _difficulty, String _lowerRepRange,
                          String _upperRepRange, String _suggestedTime,
                          String _selected)
    {


        this.ExerciseName = _exerciseName;
        this.MuscleGroup = _muscleGroup;
        this.Difficulty = _difficulty;

        //converts the strings to ints
        this.LowerRepRange = Integer.parseInt(_lowerRepRange);
        this.UpperRepRange = Integer.parseInt(_upperRepRange);
        this.SuggestedTime = Integer.parseInt(_suggestedTime);

        //set IsSelected to default false as we will not need to select just one CURRENTLY
        this.IsSelected = false;

        String log = "Name: "+_exerciseName+ " ,MuscleGroup: " + _muscleGroup + " ,Difficulty: " + _difficulty + " ,Lower Rep Range: " + _lowerRepRange
                + " ,Upper Rep Range: " + _upperRepRange + " ,SuggestedTime: " + _suggestedTime;
        // Writing Contacts to log
        Log.d("Exercise: ", log);

    }


    public String getExerciseName() {
        return ExerciseName;
    }

    public void setExerciseName(String exerciseName) {
        ExerciseName = exerciseName;
    }

    public String getMuscleGroup() {
        return MuscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        MuscleGroup = muscleGroup;
    }

    public String getDifficulty() {
        return Difficulty;
    }

    public void setDifficulty(String difficulty) {
        Difficulty = difficulty;
    }

    public int getLowerRepRange() {
        return LowerRepRange;
    }

    public void setLowerRepRange(int lowerRepRange) {
        LowerRepRange = lowerRepRange;
    }

    public int getUpperRepRange() {
        return UpperRepRange;
    }

    public void setUpperRepRange(int upperRepRange) {
        UpperRepRange = upperRepRange;
    }

    public int getSuggestedTime() {
        return SuggestedTime;
    }

    public void setSuggestedTime(int suggestedTime) {
        SuggestedTime = suggestedTime;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }
}
