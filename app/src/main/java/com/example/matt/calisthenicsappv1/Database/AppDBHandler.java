package com.example.matt.calisthenicsappv1.Database;

import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.Objects.RoutineObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 07/01/2018.
 */

public class AppDBHandler extends SQLiteOpenHelper{

    /*
    Requirements:

    1) Functions for database:
    1.1 - Create SQLite database
    1.1.1 - execSQL(Table creation Functionality)
    1.2 - Update SQLite database

    2) Functions for Table:
    2.1 - Create Table & Column Headers
    2.1.1 - Create column header for exerciseObject variables
    2.2 - Create Functions to add & remove objects to database
    2.3 - Create Function to update the object in table ((IF check then update isChecked Value))
    2.4 - Create Function to get all the objects in database
    2.5 - Create Function to get all the objects of specific muscle group from database


    NOTE -- This may be acceptable to create in a separate java class
    3) First Time Load Table & Content Creation Functions:
    3.1 - Create a list of all exercises from exercise index excel spreadsheet
    3.2 - Import List into tables

    4) Table Queries
    4.1 - Asking for specific muscle Group
    4.1.1 - Return ExerciseObject list
    4.1.2 - Takes MuscleGroup as String in Parameters
    4.2 - Asking for specific difficulty
    4.2.1 - Returns ExerciseObject List
    4.2.2 - Takes Difficulty as String in parameters
    4.3 - Asking for specific difficulty and muscle group
    4.3.1 - Returns ExerciseObject List
    4.3.2 - Takes muscle group and difficulty as strings in parameters
    4.3.3 - Check if both Strings match each entry
    4.4 - Asking for all entries for full-body workout
    4.4.1 - Returns ExerciseObject List
    4.4.2 - Takes Difficulty as String in parameters
    4.5 - Asking for all entries that have been selected
    4.5.1 - Returns ExerciseObject List
    4.5.2 - Takes no parameters
    4.5.3 - Search all entries for isSelected value - 'true'


     */

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 19;

    // Database Name
    private static final String DATABASE_NAME = "EXERCISE_LIST_TEST_DB";//"exerciseDatabase.db";

    // Exercise table name
    private static final String TABLE_EXERCISES = "exercises";
    // Routine table name
    private static final String TABLE_ROUTINES = "routines";

    // Exercise Table Columns names
    // primary key for table
    private static final String KEY_NAME = "name";
    private static final String KEY_MUSCLE_GROUP = "muscle_group";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_LOWER_REP_RANGE = "lower_rep_range";
    private static final String KEY_UPPER_REP_RANGE = "upper_rep_range";
    private static final String KEY_SUGGESTED_TIME = "suggested_time";
    private static final String KEY_SELECTED = "selected";
    private static final String KEY_TIPS = "tips";
    private static final String KEY_LINK = "videoURL";

    //Workout Table Column Names
    //primary key for table
    private static final String KEY_ROUTINE_ID = "r_id";
    private static final String KEY_ROUTINE_NAME = "routine_name";
    //will be foreign key to exercise table's key_name
    private static final String KEY_EXERCISE_NAME = "exercise_name";


    private static final String KEY_DEFAULT_ROUTINE_NAME = "Created Routine";


    public AppDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_EXERCISES + "("
                + KEY_NAME + " TEXT," + KEY_MUSCLE_GROUP + " TEXT,"
                + KEY_DIFFICULTY + " TEXT," + KEY_LOWER_REP_RANGE + " TEXT,"
                + KEY_UPPER_REP_RANGE + " TEXT," + KEY_SUGGESTED_TIME + " TEXT,"
                + KEY_SELECTED + " TEXT, " + KEY_TIPS + " TEXT, " + KEY_LINK + " TEXT" + ")";

        String CREATE_ROUTINE_TABLE = "CREATE TABLE " + TABLE_ROUTINES + "("
                + KEY_ROUTINE_ID + " INTEGER PRIMARY KEY, " + KEY_ROUTINE_NAME + " TEXT, "
                + KEY_EXERCISE_NAME + " TEXT" + ")";


        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_ROUTINE_TABLE);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTINES);

        // Create tables again
        onCreate(db);

    }


    private void AddExerciseToDB(ExerciseObject _newexercise)
    {
        //open connection to db to write to it
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //VALUE ORDER: NAME > MUSCLE_GROUP, DIFFICULTY, LOWER_REP_RANGE, UPPER_REP_RANGE, SUGGESTED_TIME, SELECTED

        //NOTED -- All the values passed should be strings
        //Must convert to string: LowerRepRange, UpperRepRange, SuggestedTime and isSelected
        values.put(KEY_NAME, _newexercise.getExerciseName());
        values.put(KEY_MUSCLE_GROUP, _newexercise.getMuscleGroup());
        values.put(KEY_DIFFICULTY, _newexercise.getDifficulty());
        values.put(KEY_LOWER_REP_RANGE, String.valueOf(_newexercise.getLowerRepRange()));
        values.put(KEY_UPPER_REP_RANGE, String.valueOf(_newexercise.getUpperRepRange()));
        values.put(KEY_SUGGESTED_TIME, String.valueOf(_newexercise.getSuggestedTime()));
        values.put(KEY_SELECTED, String.valueOf(_newexercise.isSelected()));
        //newly added
        values.put(KEY_TIPS, _newexercise.getTips());
        values.put(KEY_LINK, _newexercise.getVideoURL());


        String log = "EName: " + _newexercise.getExerciseName() + " ,MGroup: " + _newexercise.getMuscleGroup() + " ,Difficulty: " + _newexercise.getDifficulty() +
                " ,LowerRepRange: " + String.valueOf(_newexercise.getLowerRepRange()) + " ,UpperRepRange: " + String.valueOf(_newexercise.getUpperRepRange()) +
                " ,SuggestedTime: " + String.valueOf(_newexercise.getSuggestedTime()) + " ,Selected: " + String.valueOf(_newexercise.isSelected() + " ,Tips: " + _newexercise.getTips() +
                " ,Video URL: " + _newexercise.getVideoURL());
        // Writing Contacts to log
        Log.d("Exercise: ", "Exercise Input to Table: \n" +log);

        //inserting row
        db.insert(TABLE_EXERCISES, null, values);
        db.close();
    }


    public void setDefaultValues()
    {
        //Shoulder Values
        AddExerciseToDB(new ExerciseObject("Handstand Press ups", "Shoulders", "Advanced","6", "12", "0", "false", "1a" , "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Handstand Kick-Ups", "Shoulders", "Advanced","6", "12", "0", "false", "1b", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Pike Hold", "Shoulders", "Easy","0" , "0", "30", "false", "1c", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Elevated Pike Hold", "Shoulders", "Intermediate","0", "0", "30", "false", "1d", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Pike Push-ups", "Shoulders", "Intermediate","6", "12", "0", "false", "1e", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Wall Walks", "Shoulders", "Advanced","3", "7", "0", "false", "1f", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Elevated Pike Push-ups", "Shoulders", "Intermediate","6", "12", "0", "false", "1g", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Handstand Hold", "Shoulders", "Intermediate","0", "0", "45", "false", "1h", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Handstand Shoulder Taps", "Shoulders", "Advanced","8", "16", "0", "false", "1i", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Straight Arm Plank Shoulder Taps", "Shoulders", "Easy","0", "0", "30", "false", "1j", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Shoulder To Shoulder", "Shoulders", "Intermediate","4", "10", "0", "false", "1k", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("One-Arm Handstand Hold", "Shoulders", "Advanced","0", "0", "20", "false", "1l", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Handstand Press", "Shoulders", "Advanced","3", "7", "0", "false", "1m", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Handstand Press Negative", "Shoulders", "Intermediate","3", "7", "0", "false", "1n", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Wall Plank", "Shoulders", "Intermediate","0", "0", "30", "false", "1o", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Low-to-High Plank", "Shoulders", "Easy","0", "0", "30", "false", "1p", "zBUx6zTxr98"));

        AddExerciseToDB(new ExerciseObject("Tuck Planche Hold", "Shoulders", "Advanced","0", "0", "10", "false", "1q", "zBUx6zTxr98"));

        /*
        @weights:
        bumbell shoulder press
        military press

        @rings:
        hanging inverted shoulder shrugs
        skin-the-cat
         */


        //Chest Values
        AddExerciseToDB(new ExerciseObject("Push-ups", "Chest", "Easy","6", "12", "0", "false", "2a", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Dips", "Chest", "Intermediate","6", "12", "0", "false", "2b", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Bench Dips", "Chest", "Easy","6", "12", "0", "false", "2c", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("TypeWriter Push-ups", "Chest", "Advanced","6", "12", "0", "false", "2d", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Straight Bar Dips", "Chest", "Intermediate","6", "12", "0", "false", "2e", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Korean Dips", "Chest", "Advanced","6", "12", "0", "false", "2f", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Wide Push-ups", "Chest", "Intermediate","6", "12", "0", "false", "2g", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Jump Negative Dips", "Chest", "Easy","6", "12", "0", "false", "2h", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Russian Dips", "Chest", "Advanced","6", "12", "0", "false", "2i", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Incline Push-ups", "Chest", "Easy","6", "12", "0", "false", "2j", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Decline Push-ups", "Chest", "Intermediate","6", "12", "0", "false", "2k", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Explosive Push-ups", "Chest", "Intermediate","6", "12", "0", "false", "2l", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Diamond Push-ups", "Chest", "Intermediate","6", "12", "0", "false", "2m", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Spiderman Push-ups", "Chest", "Intermediate","6", "12", "0", "false", "2n", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Side-To-Side Push-ups", "Chest", "Intermediate","6", "12", "0", "false", "2o", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Slow Dips", "Chest", "Intermediate","1", "4", "0", "false", "2p", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Slow Push-ups", "Chest", "Intermediate","1", "4", "0", "false", "2r", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Tuck Planche Push-ups", "Chest", "Advanced","4", "10", "0", "false", "2s", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Hindu Push-ups", "Chest", "Intermediate","6", "12", "0", "false", "2t", "_AsPY1bQx70"));

        AddExerciseToDB(new ExerciseObject("Single Arm Push-ups", "Chest", "Advanced","6", "12", "0", "false", "2u", "_AsPY1bQx70"));

        /*
Rings:
Ring dips

Band:
Resistance band Push-ups
Resistance Band Dips

Weight Belt:
Weighted Dips

Weights:
Bench Press
         */


        //Back Values
        AddExerciseToDB(new ExerciseObject("Pull-ups", "Back", "Intermediate","6", "12", "0", "false", "3a", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("One-arm Pull-ups", "Back", "Advanced","6", "12", "0", "false", "3b", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Australian Pull-ups", "Back", "Easy","6", "12", "0", "false", "3c", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Archer Australian Pull-ups", "Back", "Intermediate","6", "12", "0", "false", "3d", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Australian Chin-ups", "Back", "Easy","6", "12", "0", "false", "3e", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Single Arm Australian Pull-ups", "Back", "Intermediate","6", "12", "0", "false", "3f", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Chin-ups", "Back", "Intermediate","6", "12", "0", "false", "3g", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Pull-up hold", "Back", "Easy","0", "0", "30", "false", "3h", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Chin-up Hold", "Back", "Easy","0", "0", "30", "false", "3i", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Archer Australian Pull-ups", "Back", "Intermediate","6", "12", "0", "false", "3j", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Slow Pull-ups", "Back", "Intermediate","1", "4", "0", "false", "3k", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Slow Chin-ups", "Back", "Intermediate","1", "4", "0", "false", "3l", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Archer Pull-ups", "Back", "Advanced","6", "12", "0", "false", "3m", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Jump Negative Pull-ups", "Back", "Easy","6", "12", "0", "false", "3n", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Jump Negative Chin-ups", "Back", "Easy","6", "12", "0", "false", "3o", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Muscle-ups", "Back", "Advanced","4", "10", "0", "false", "3p", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Negative Muscle-ups", "Back", "Intermediate","6", "12", "0", "false", "3q", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Commando Pull-ups", "Back", "Intermediate","6", "12", "0", "false", "3r", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Single Arm Negative Commando Pull-ups", "Back", "Intermediate","4", "8", "0", "false", "3s", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Head Bangers", "Back", "Advanced","6", "12", "0", "false", "3t", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Tucked Front Lever Pull-ups", "Back", "Advanced","6", "12", "0", "false", "3u", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("L-sit Pull-ups", "Back", "Advanced","6", "12", "0", "false", "3v", "GfCqMv--ncA"));

        AddExerciseToDB(new ExerciseObject("Supermans", "Back", "Easy","6", "12", "0", "false", "3w", "GfCqMv--ncA"));

        /*
        Bent over barbell row
        bent over dumbell row
        deadlift
        band assisted pull ups
        band assisted muscle-ups
        Scapular shrugs
        ring pull-ups
        Supermans (weighted)
         */

        //Arms
        AddExerciseToDB(new ExerciseObject("Skull Crushers", "Arms", "Intermediate","6", "12", "0", "false", "4a", "gA-NDZb29I4"));

        AddExerciseToDB(new ExerciseObject("Sphinx Push-ups", "Arms", "Easy","6", "12", "0", "false", "4b", "gA-NDZb29I4"));

        AddExerciseToDB(new ExerciseObject("Bar Curl", "Arms", "Intermediate", "6", "12", "0", "false", "4c", "gA-NDZb29I4"));

        /*
Skull Crushers
Tricep Extensions (floor)

Resistance band Push-ups
Resistance bands tricep pull-downs
Resistance Band Bicep Curl

Close tricep Extension

Single Arm Bench dips

Bear Crawl
Bar Curls

Assisted One-arm Chins-ups

Rope Climbs
Rope climbs (floor)

         */


        //Core

        AddExerciseToDB(new ExerciseObject("Crunches", "Core", "Easy","8", "15", "0", "false", "5a", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Chair Crunches", "Core", "Easy","6", "12", "0", "false", "5b", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Star Crunches", "Core", "Easy","6", "12", "0", "false", "5c", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Bicycles", "Core", "Easy","0", "0", "30", "false", "5d", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Criucifix", "Core", "Easy","6", "12", "0", "false", "5e", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Crucifix Hold", "Core", "Easy","0", "0", "30", "false", "5f", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Boat Hold", "Core", "Easy","0", "0", "30", "false", "5g", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("V-Sit Hold", "Core", "Intermediate","0", "0", "30", "false", "5h", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("V-ups", "Core", "Intermediate","6", "12", "0", "false", "5i", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Leg Raises (floor)", "Core", "Easy","6", "12", "0", "false", "5j", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Lower Abdominal Raises", "Core", "Easy","6", "12", "0", "false", "5k", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Plank", "Core", "Easy","0", "0", "30", "false", "5l", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Plank Up-And-Down", "Core", "Easy","0", "0", "30", "false", "5m", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Plank Side-To-Side", "Core", "Easy","0", "0", "30", "false", "5n", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Plank Knees-To-Elbow", "Core", "Easy","0", "0", "30", "false", "5o", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Toe Touches", "Core", "Easy","0", "0", "30", "false", "5p", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Russian Twitst", "Core", "Easy","6", "12", "0", "false", "5q", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Flutter Kicks", "Core", "Easy","0", "0", "30", "false", "5r", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Windshield Wipers", "Core", "Advanced","0", "0", "30", "false", "5s", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Side Plank Hold", "Core", "Easy","0", "0", "30", "false", "5t", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Side Plank Raises", "Core", "Easy","0", "0", "30", "false", "5u", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Hanging Leg Raises", "Core", "Intermediate","6", "12", "0", "false", "5v", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Hanging Knee Raises", "Core", "Easy","6", "12", "0", "false", "5w", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Hanging Corner Raises", "Core", "Easy","6", "12", "0", "false", "5x", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Front Lever Raises", "Core", "Advanced","6", "12", "0", "false", "5y", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Tuck Planche Raises", "Core", "Advanced","6", "12", "0", "false", "5z", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("L-Sit Hold", "Core", "Intermediate","0", "0", "20", "false", "5aa", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Tucked L-sit Hold", "Core", "Easy","0", "0", "30", "false", "5ab", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Toes-To-Bar", "Core", "Advanced","6", "12", "0", "false", "5ac", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Back Lever Drop", "Core", "Advanced","3", "6", "0", "false", "5ad", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Dragon Flag", "Core", "Advanced","2", "5", "0", "false", "5ae", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Bar Crunches", "Core", "Easy","6", "12", "0", "false", "5af", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Legs Down Hold", "Core", "Easy","0", "0", "30", "false", "5ag", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Inverted Hanging Deadlift", "Core", "Advanced","6", "12", "0", "false", "5ah", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Pull-Over", "Core", "Intermediate","2", "8", "0", "false", "5ai", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Skin-The-Cat", "Core", "Intermediate","6", "12", "0", "false", "5aj", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Hollow Body Hold", "Core", "Intermediate", "0", "0", "30", "false", "5ak", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Dead Bugs", "Core", "Intermediate", "0", "0", "45", "false", "5al", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Half Wipers", "Core", "Intermediate", "6", "12", "30", "false", "5am", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Raised Leg Circles", "Core", "Intermediate", "0", "0", "30", "false", "5an", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Reverse Crunch", "Core", "Easy", "6", "12", "30", "false", "5ao", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Side Crunch", "Core", "Easy", "0", "0", "30", "false", "5ap", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("JackKnifes", "Core", "Easy", "0", "0", "30", "false", "5aq", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Side Jackknifes", "Core", "Intermediate", "0", "0", "30", "false", "5ar", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("Knee To Elbow Sit-ups", "Core", "Intermediate", "0", "0", "30", "false", "5as", "fPTEhaZUd6k"));

        AddExerciseToDB(new ExerciseObject("V-sit Rotations", "Core", "Intermediate", "0", "0", "30", "false", "5at", "fPTEhaZUd6k"));

        /*bar crunches
ab wheel
hip raises
legs down hold
upside down deadlift
pull-over
skin-the-cat
plank open-and-close
wall plank
Animal flow side jump - handstands
Wall Plank knees to elbow
Wall mountain climbers
Lateral Plank Walk
Hollow Hold
         */

        //Legs
        AddExerciseToDB(new ExerciseObject("Squats", "Legs", "Easy","6", "12", "0", "false", "6a", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Sumo Squats", "Legs", "Easy","6", "12", "0", "false", "6b", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Bulgarian Split Squats", "Legs", "Easy","10", "20", "0", "false", "6c", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Jump Squats", "Legs", "Intermediate","6", "12", "0", "false", "6d", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Squat Kicks", "Legs", "Easy","6", "10", "0", "false", "6e", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Wall Sit", "Legs", "Intermediate","6", "12", "0", "false", "6f", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Assisted Pistol Squats", "Legs", "Intermediate","3", "8", "0", "false", "6g", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Pistol Squats", "Legs", "Advanced","3", "8", "0", "false", "6h", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Broad Jumps", "Legs", "Intermediate","0", "0", "30", "false", "6i", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Lunges", "Legs", "Easy","10", "20", "0", "false", "6j", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Alternating Lunges", "Legs", "Intermediate","0", "0", "30", "false", "6k", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Box Jumps", "Legs", "Intermediate","5", "10", "30", "false", "6l", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Box Step-ups", "Legs", "Easy","10", "20", "30", "false", "6m", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Calf Raises", "Legs", "Easy","10", "20", "0", "false", "6n", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Calf Raise Hold", "Legs", "Easy","0", "0", "20", "false", "6o", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Crab Walks", "Legs", "Intermediate","0", "0", "20", "false", "6p", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Side Squats", "Legs", "Intermediate","6", "12", "0", "false", "6r", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Tip-toe Wall Sit", "Legs", "Intermediate","0", "0", "30", "false", "6s", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Knee Ups", "Legs", "Intermediate","5", "10", "0", "false", "6t", "PMivT7MJ41M"));

        AddExerciseToDB(new ExerciseObject("Depth Jumps", "Legs", "Intermediate","0", "0", "30", "false", "6u", "PMivT7MJ41M"));


    }

    //If one item needs to be returned from the exercise table
    public ExerciseObject getExercise(String _exerciseName)
    {
        //get a readable item from the database
        SQLiteDatabase db = this.getReadableDatabase();

        //
        Cursor cursor = db.query(TABLE_EXERCISES, new String[] {KEY_NAME,
                        KEY_MUSCLE_GROUP, KEY_DIFFICULTY, KEY_LOWER_REP_RANGE,
                        KEY_UPPER_REP_RANGE, KEY_SUGGESTED_TIME, KEY_SELECTED, KEY_TIPS, KEY_LINK}, KEY_NAME + "=?",
                new String[] {String.valueOf(_exerciseName)},null,null,null,null);
        //If the result of this query done not come up empty
        if(cursor != null)
        {
            //point cursor to the item returned from the query
            cursor.moveToFirst();
        }

        //From the query result, assign each string to correct parameter position
        //NAME > MUSCLE_GROUP > DIFFICULTY > LOWER_REP_RANGE > UPPER_REP_RANGE > SUGGESTED_TIME > SELECTED > TIPS > VIDEO_URL
        ExerciseObject eObject = new ExerciseObject(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));

        //close entry to database
        db.close();

        //return this object
        return eObject;

    }


    // Getting All Exercises
    public List<ExerciseObject> getAllCExercises() {
        List<ExerciseObject> exerciseList = new ArrayList<ExerciseObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXERCISES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //moveToFirst will 'move' cursor to first item in database
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //creating a new ExerciseObject
                ExerciseObject eObject = new ExerciseObject();

                //Setting the variables of object to current query row
                eObject.setExerciseName(cursor.getString(0));
                eObject.setMuscleGroup(cursor.getString(1));
                eObject.setDifficulty(cursor.getString(2));
                eObject.setLowerRepRange(Integer.parseInt(cursor.getString(3)));
                eObject.setUpperRepRange(Integer.parseInt(cursor.getString(4)));
                eObject.setSuggestedTime(Integer.parseInt(cursor.getString(5)));
                //Note -- ExerciseObject Constructor does not require isSelected as it set default False
                eObject.setTips(cursor.getString(7));
                eObject.setVideoURL(cursor.getString(8));

                //Adding the exerciseObject to the list
                exerciseList.add(eObject);

            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }

        //close entry to database
        db.close();

        // return exercise list
        return exerciseList;
    }


    // Getting All Exercises
    public List<ExerciseObject> getAllSelectedExercises() {
        List<ExerciseObject> exerciseList = new ArrayList<ExerciseObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXERCISES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //moveToFirst will 'move' cursor to first item in database
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //creating a new ExerciseObject
                ExerciseObject eObject = new ExerciseObject();

                //Setting the variables of object to current query row
                eObject.setExerciseName(cursor.getString(0));
                eObject.setMuscleGroup(cursor.getString(1));
                eObject.setDifficulty(cursor.getString(2));
                eObject.setLowerRepRange(Integer.parseInt(cursor.getString(3)));
                eObject.setUpperRepRange(Integer.parseInt(cursor.getString(4)));
                eObject.setSuggestedTime(Integer.parseInt(cursor.getString(5)));
                eObject.setSelected(Boolean.parseBoolean(cursor.getString(6)));
                eObject.setTips(cursor.getString(7));
                eObject.setVideoURL(cursor.getString(8));

                if (eObject.isSelected())
                {
                    //Adding the exerciseObject to the list
                    exerciseList.add(eObject);
                }


            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }

        //close entry to database
        db.close();

        // return exercise list
        return exerciseList;
    }


    // Getting All Exercises with specific MuscleGroup
    public List<ExerciseObject> getAllCExercises(String _muscleGroup) {
        List<ExerciseObject> exerciseList = new ArrayList<ExerciseObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXERCISES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //moveToFirst will 'move' cursor to first item in database
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //creating a new ExerciseObject
                ExerciseObject eObject = new ExerciseObject();

                //Setting the variables of object to current query row
                eObject.setExerciseName(cursor.getString(0));
                eObject.setMuscleGroup(cursor.getString(1));
                eObject.setDifficulty(cursor.getString(2));
                eObject.setLowerRepRange(Integer.parseInt(cursor.getString(3)));
                eObject.setUpperRepRange(Integer.parseInt(cursor.getString(4)));
                eObject.setSuggestedTime(Integer.parseInt(cursor.getString(5)));
                //Note -- ExerciseObject Constructor does not require isSelected as it set default False
                eObject.setTips(cursor.getString(7));
                eObject.setVideoURL(cursor.getString(8));


                //if the muscleGroup of exercise is same as one passed in params, then add to list
                 if (eObject.getMuscleGroup().equals(_muscleGroup))
                 {
                     //Adding the exerciseObject to the list
                     exerciseList.add(eObject);
                 }


            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }

        //close entry to database
        db.close();

        // return exercise list
        return exerciseList;
    }


    //Returning all exercises that are of one of the passed muscle groups
    public ArrayList<ExerciseObject> returnAllMuscleGroupExercises(ArrayList<String> _muscleGroups)
    {
        ArrayList<ExerciseObject> exerciseList = new ArrayList<ExerciseObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXERCISES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //moveToFirst will 'move' cursor to first item in database
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //creating a new ExerciseObject
                ExerciseObject eObject = new ExerciseObject();

                //Setting the variables of object to current query row
                eObject.setExerciseName(cursor.getString(0));
                eObject.setMuscleGroup(cursor.getString(1));
                eObject.setDifficulty(cursor.getString(2));
                eObject.setLowerRepRange(Integer.parseInt(cursor.getString(3)));
                eObject.setUpperRepRange(Integer.parseInt(cursor.getString(4)));
                eObject.setSuggestedTime(Integer.parseInt(cursor.getString(5)));
                //Note -- ExerciseObject Constructor does not require isSelected as it set default False

                eObject.setTips(cursor.getString(7));
                eObject.setVideoURL(cursor.getString(8));

                //cycle through each of the ArrayList<String> elements
                for (int i = 0; i < _muscleGroups.size(); i++)
                {
                    //check if exercises' muscle group is equal to that of one selected
                    if(eObject.getMuscleGroup() == _muscleGroups.get(i))
                    {
                        //if so, add this exercise to the list of exercises

                        //Adding the exerciseObject to the list
                        exerciseList.add(eObject);
                    }
                }

            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }

        //close entry to database
        db.close();

        // return exercise list
        return exerciseList;
    }


    // Updating single exercise
    public int updateExercise(ExerciseObject exerciseObject) {
        //allow editing on database
        SQLiteDatabase db = this.getWritableDatabase();

        //Functionality included within SQLite
        ContentValues values = new ContentValues();

        //putting the information back into the database in correct order
        values.put(KEY_MUSCLE_GROUP, exerciseObject.getMuscleGroup());
        values.put(KEY_DIFFICULTY, exerciseObject.getDifficulty());
        values.put(KEY_LOWER_REP_RANGE, String.valueOf(exerciseObject.getLowerRepRange()));
        values.put(KEY_UPPER_REP_RANGE, String.valueOf(exerciseObject.getUpperRepRange()));
        values.put(KEY_SUGGESTED_TIME, String.valueOf(exerciseObject.getSuggestedTime()));
        values.put(KEY_SELECTED, String.valueOf(exerciseObject.isSelected()));
        values.put(KEY_TIPS, exerciseObject.getTips());
        values.put(KEY_LINK, exerciseObject.getVideoURL());

        //close entry to database
        db.close();

        // updating row
        return db.update(TABLE_EXERCISES, values, KEY_NAME + " = ?",
                new String[] { exerciseObject.getExerciseName() });
    }


    //Updating IsSelected Value
    public void updateIsSelected(ExerciseObject exerciseObject)
    {
        //allow editing on database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, exerciseObject.getExerciseName());
        cv.put(KEY_MUSCLE_GROUP, exerciseObject.getMuscleGroup());
        cv.put(KEY_DIFFICULTY, exerciseObject.getDifficulty());
        cv.put(KEY_LOWER_REP_RANGE, String.valueOf(exerciseObject.getLowerRepRange()));
        cv.put(KEY_UPPER_REP_RANGE, String.valueOf(exerciseObject.getUpperRepRange()));
        cv.put(KEY_SUGGESTED_TIME, String.valueOf(exerciseObject.getSuggestedTime()));
        cv.put(KEY_TIPS, exerciseObject.getTips());
        cv.put(KEY_LINK, exerciseObject.getVideoURL());

        if(!exerciseObject.isSelected())
        {
            cv.put(KEY_SELECTED, "true");
            exerciseObject.setSelected(true);
        }
        else
        {
            cv.put(KEY_SELECTED, "false");
            exerciseObject.setSelected(false);
        }

        String log = "Exercise Name: " + exerciseObject.getExerciseName() + " ,isSelected new Value:"
                + exerciseObject.isSelected();
        // Writing Contacts to log
        Log.d("Updated_Selected:", log);


        db.update(TABLE_EXERCISES, cv, KEY_NAME + " = ?", new String[] {exerciseObject.getExerciseName()});

        //DON'T NEED TO RETURN ANYTHING AS JUST UPDATING DATABASE WITH 1 NEW VALUE
        db.close();
    }


    //retrieving all exercises across multiple muscle groups
    public List<ExerciseObject> getAllMuscleGroupExercises(String[] _muscleGroups)
    {
        //todo:
        //go to sql database
        //check if each exercise has muscle group within string array passed
        //retrieve al these and place into list of exercise objects

        List<ExerciseObject> exerciseList = new ArrayList<ExerciseObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXERCISES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //moveToFirst will 'move' cursor to first item in database
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //creating a new ExerciseObject
                ExerciseObject eObject = new ExerciseObject();

                //Setting the variables of object to current query row
                eObject.setExerciseName(cursor.getString(0));
                eObject.setMuscleGroup(cursor.getString(1));
                eObject.setDifficulty(cursor.getString(2));
                eObject.setLowerRepRange(Integer.parseInt(cursor.getString(3)));
                eObject.setUpperRepRange(Integer.parseInt(cursor.getString(4)));
                eObject.setSuggestedTime(Integer.parseInt(cursor.getString(5)));
                //Note -- ExerciseObject Constructor does not require isSelected as it set default False

                eObject.setTips(cursor.getString(7));
                eObject.setVideoURL(cursor.getString(8));

                //if the muscleGroup of exercise is same as one passed in params, then add to list
                for (int i = 0 ; i< _muscleGroups.length - 1; i++) {
                    if (eObject.getMuscleGroup().equals(_muscleGroups[i])) {
                        //Adding the exerciseObject to the list
                        exerciseList.add(eObject);


                        //note -- not the most efficient way of doing the search, sufficient for now though!9.,
                    }
                }


            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }

        //close entry to database
        db.close();

        // return exercise list
        return exerciseList;


    }


    //retrieving all the exercises from sql database that have specific difficulty setting
    //need to log these for now....
    public ArrayList<ExerciseObject> getDifficultyExercises(String _difficulty)
    {
        ArrayList<ExerciseObject> exerciseList = new ArrayList<ExerciseObject>();

        if (_difficulty != "Easy" || _difficulty != "Intermediate" || _difficulty != "Advanced")
        {
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_EXERCISES;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            //moveToFirst will 'move' cursor to first item in database
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    //creating a new ExerciseObject
                    ExerciseObject eObject = new ExerciseObject();

                    //Setting the variables of object to current query row
                    eObject.setExerciseName(cursor.getString(0));
                    eObject.setMuscleGroup(cursor.getString(1));
                    eObject.setDifficulty(cursor.getString(2));
                    eObject.setLowerRepRange(Integer.parseInt(cursor.getString(3)));
                    eObject.setUpperRepRange(Integer.parseInt(cursor.getString(4)));
                    eObject.setSuggestedTime(Integer.parseInt(cursor.getString(5)));
                    //Note -- ExerciseObject Constructor does not require isSelected as it set default False

                    eObject.setTips(cursor.getString(7));
                    eObject.setVideoURL(cursor.getString(8));

                    //go through each exercise
                    //if exercise's difficult matches that passed, then add to list
                    if (eObject.getDifficulty().contains(_difficulty))
                    {
                        exerciseList.add(eObject);
                    }


                } while (cursor.moveToNext());
                //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
            }

            //close entry to database
            db.close();

            // return exercise list
            return exerciseList;

        }
        else
        {
            //do the exact same thing, just log that exercise X does not have the right difficulty wording


            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_EXERCISES;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            //moveToFirst will 'move' cursor to first item in database
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    //creating a new ExerciseObject
                    ExerciseObject eObject = new ExerciseObject();

                    //Setting the variables of object to current query row
                    eObject.setExerciseName(cursor.getString(0));
                    eObject.setMuscleGroup(cursor.getString(1));
                    eObject.setDifficulty(cursor.getString(2));
                    eObject.setLowerRepRange(Integer.parseInt(cursor.getString(3)));
                    eObject.setUpperRepRange(Integer.parseInt(cursor.getString(4)));
                    eObject.setSuggestedTime(Integer.parseInt(cursor.getString(5)));
                    //Note -- ExerciseObject Constructor does not require isSelected as it set default False

                    eObject.setTips(cursor.getString(7));
                    eObject.setVideoURL(cursor.getString(8));

                    //go through each exercise
                    //if exercise's difficult matches that passed, then add to list
                    if (eObject.getDifficulty().contains(_difficulty))
                    {
                        exerciseList.add(eObject);
                    }


                } while (cursor.moveToNext());
                //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
            }

            //close entry to database
            db.close();

            // return exercise list
            return exerciseList;
        }
    }


    //create a query that will return all exercises who's Selected value is true
    public void returnTrueExercises()
    {
       //query
        /*
        for each exercise, update column x with x

        UDPATE <table> SET <column 1> = <value 1>, <column 2> = <value 2>;
         */
        SQLiteDatabase db = this.getWritableDatabase();
        String resetQuery = "UPDATE " + TABLE_EXERCISES + " SET " + KEY_SELECTED + " = 'false'";
        db.execSQL(resetQuery);
    }


    //creating a new routine
    public void addNewRoutine(String _routineName, ArrayList<ExerciseObject> exerciseObjects)
    {
        //get names of all exercise objects in list
        String listOfExerciseNames = "";

        //looping through list of objects passed to get their names
        for(ExerciseObject e : exerciseObjects)
        {
            //separate with comma, can get string array back via comma separators
            listOfExerciseNames += e.getExerciseName() + ",";
        }



        //assign name to routine.. if blank, assume default name value
        String routineName;
        if (_routineName.equals(""))
        {
            routineName = KEY_DEFAULT_ROUTINE_NAME;
        }
        else
            routineName = _routineName;

        //add to database
        //open connection to db to write to it
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        /*
         String CREATE_ROUTINE_TABLE = "CREATE TABLE " + TABLE_ROUTINES + "("
                + KEY_ROUTINE_ID + " NOT NULL INTEGER PRIMARY KEY," + KEY_ROUTINE_NAME + " TEXT,"
                + KEY_EXERCISE_NAME + " TEXT" + ")";
          */

        //Assigning actual values to the rows
        //note -- no id added as it will be auto added on creation
        values.put(KEY_ROUTINE_NAME, routineName);
        values.put(KEY_EXERCISE_NAME, listOfExerciseNames);

        //inserting row
        db.insert(TABLE_ROUTINES, null, values);
        db.close();
    }


    //overloaded create new routine function -- if the object is already created but needs name change
    public void addNewRoutine(RoutineObject _routineObject, String _newRoutineName)
    {
        //add to database
        //open connection to db to write to it
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        /*
         String CREATE_ROUTINE_TABLE = "CREATE TABLE " + TABLE_ROUTINES + "("
                + KEY_ROUTINE_ID + " NOT NULL INTEGER PRIMARY KEY," + KEY_ROUTINE_NAME + " TEXT,"
                + KEY_EXERCISE_NAME + " TEXT" + ")";
          */

        //Assigning actual values to the rows
        //note -- no id added as it will be auto added on creation
        values.put(KEY_ROUTINE_NAME, _newRoutineName);

        //creating tempString for storing exercise names;
        String tempExerciseNames = "";

        for (String exerciseName : _routineObject.getExerciseNames())
        {
            //re-assigning the comma back between the strings
            tempExerciseNames += exerciseName + ",";
        }

        values.put(KEY_EXERCISE_NAME, tempExerciseNames);

        //inserting row
        db.insert(TABLE_ROUTINES, null, values);
        db.close();
    }


    //returning all the routineObjects from database
    public ArrayList<RoutineObject> returnAllRoutines()
    {
        ArrayList<RoutineObject> routineList = new ArrayList<RoutineObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ROUTINES;

        //making connection to database that allows us write privileges
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //moveToFirst will 'move' cursor to first item in database
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //creating a new routine objects
                RoutineObject rObject = new RoutineObject();

                /*
                ORDER:
                0 : ID (INTEGER)
                1 : RoutineName (STRING)
                2 : ExerciseNames (STRING) <-- Comma separated string
                 */

                //setting object variables to values returned from query
                rObject.setR_ID(cursor.getInt(0));
                rObject.setRoutineName(cursor.getString(1));

                //need to convert string to string[] before assignment
                String tempExerciseNames = cursor.getString(2);

                String[] exerciseNameArray = tempExerciseNames.split(",");

                routineList.add(rObject);

            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }

        //close entry to database
        db.close();

        // return routineList list
        return routineList;
    }


    //function to return specific routine object -- unnecessary
    public RoutineObject returnSpecificRoutine(RoutineObject _routineObject)
    {
        ArrayList<RoutineObject> routineList = new ArrayList<RoutineObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ROUTINES;

        //making connection to database that allows us write privileges
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //creating a return object
        RoutineObject returnRoutine = new RoutineObject();

        //moveToFirst will 'move' cursor to first item in database
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //creating a new routine objects
                RoutineObject rObject = new RoutineObject();

                /*
                ORDER:
                0 : ID (INTEGER)
                1 : RoutineName (STRING)
                2 : ExerciseNames (STRING) <-- Comma separated string
                 */

                //setting object variables to values returned from query
                rObject.setR_ID(cursor.getInt(0));
                rObject.setRoutineName(cursor.getString(1));

                //need to convert string to string[] before assignment
                String tempExerciseNames = cursor.getString(2);

                String[] exerciseNameArray = tempExerciseNames.split(",");

                routineList.add(rObject);

            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }

        //searching returned objects to find matching ID to object passed to method
        for (RoutineObject search : routineList) {
            if (search.getR_ID() == _routineObject.getR_ID())
            {
                returnRoutine = search;
                //breaking out of loop for efficiency
                break;
            }
        }

        //close entry to database
        db.close();

        // return routineList list
        return returnRoutine;
    }


    //function to delete an item from the routines database
    public void deleteRoutine(RoutineObject _routineObject)
    {
        //making connection to database that allows us write privileges
        SQLiteDatabase db = this.getWritableDatabase();

        //DELETE FROM tablename WHERE columnname = value
        db.execSQL("DELETE FROM " + TABLE_ROUTINES + " WHERE " + KEY_ROUTINE_ID + " = " + _routineObject.getR_ID());

        //closing the database
        db.close();
    }


    //function to change the name of the routineObject -- connect to a prompt instead of a textEditor
    public void editRoutine(RoutineObject _routineObject, String _newRoutineName)
    {
        deleteRoutine(_routineObject);
        addNewRoutine(_routineObject, _newRoutineName);
    }
}
