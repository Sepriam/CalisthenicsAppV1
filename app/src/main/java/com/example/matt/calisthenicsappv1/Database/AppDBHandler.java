package com.example.matt.calisthenicsappv1.Database;

import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;

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
    private static final int DATABASE_VERSION = 9;

    // Database Name
    private static final String DATABASE_NAME = "EXERCISE_LIST_TEST_DB";//"exerciseDatabase.db";

    // Contacts table name
    private static final String TABLE_EXERCISES = "exercises";

    // Contacts Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_MUSCLE_GROUP = "muscle_group";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_LOWER_REP_RANGE = "lower_rep_range";
    private static final String KEY_UPPER_REP_RANGE = "upper_rep_range";
    private static final String KEY_SUGGESTED_TIME = "suggested_time";
    private static final String KEY_SELECTED = "selected";



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
                + KEY_SELECTED + " TEXT" + ")";


        db.execSQL(CREATE_CONTACTS_TABLE);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);

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


        String log = "EName: " + _newexercise.getExerciseName() + " ,MGroup: " + _newexercise.getMuscleGroup() + " ,Difficulty: " + _newexercise.getDifficulty() +
                " ,LowerRepRange: " + String.valueOf(_newexercise.getLowerRepRange()) + " ,UpperRepRange: " + String.valueOf(_newexercise.getUpperRepRange()) +
                " ,SuggestedTime: " + String.valueOf(_newexercise.getSuggestedTime()) + " ,Selected: " + String.valueOf(_newexercise.isSelected());
        // Writing Contacts to log
        Log.d("Exercise: ", "Exercise Input to Table: \n" +log);

        //inserting row
        db.insert(TABLE_EXERCISES, null, values);
        db.close();
    }


    public void setDefaultValues()
    {
        //Shoulder Values
        AddExerciseToDB(new ExerciseObject("Handstand Press ups", "Shoulders", "Advanced",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Handstand Kick-Ups", "Shoulders", "Advanced",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Military Press", "Shoulders", "Intermediate",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Dumbbell Shoulder Press", "Shoulders", "Easy",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Pike Hold", "Shoulders", "Easy",
                0 , 0, 30));
        AddExerciseToDB(new ExerciseObject("Elevated Pike Hold", "Shoulders", "Intermediate",
                0, 0, 30));
        AddExerciseToDB(new ExerciseObject("Pike Push-ups", "Shoulders", "Intermediate",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Wall Walks", "Shoulders", "Advanced",
                3, 7, 0));


        //Chest Values
        AddExerciseToDB(new ExerciseObject("Push-ups", "Chest", "Easy",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Dips", "Chest", "Intermediate",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Bench Dips", "Chest", "Easy",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("TypeWriter Push-ups", "Chest", "Advanced",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Bench Press", "Chest", "Easy",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Straight Bar Dips", "Chest", "Intermediate",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Weighted Dips", "Chest", "Easy",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Korean Dips", "Chest", "Advanced",
                6, 12, 0));


        //Back Values
        AddExerciseToDB(new ExerciseObject("Pull-ups", "Back", "Intermediate",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("One-arm Pull-ups", "Back", "Advanced",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Australian Pull-ups", "Back", "Easy",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Bent-over Dumbbell Row", "Back", "Intermediate",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Bent-over Barbell Row", "Back", "Easy",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Deadlifts", "Back", "Advanced",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Band Assisted Pull-ups", "Back", "Easy",
                6, 12, 0));
        AddExerciseToDB(new ExerciseObject("Archer Australian Pull-ups", "Back", "Intermediate",
                6, 12, 0));

    }

    //If one item needs to be returned from the exercise table
    public ExerciseObject getExercise(String _exerciseName)
    {
        //get a readable item from the database
        SQLiteDatabase db = this.getReadableDatabase();

        //
        Cursor cursor = db.query(TABLE_EXERCISES, new String[] {KEY_NAME,
                        KEY_MUSCLE_GROUP, KEY_DIFFICULTY, KEY_LOWER_REP_RANGE,
                        KEY_UPPER_REP_RANGE, KEY_SUGGESTED_TIME, KEY_SELECTED}, KEY_NAME + "=?",
                new String[] {String.valueOf(_exerciseName)},null,null,null,null);
        //If the result of this query done not come up empty
        if(cursor != null)
        {
            //point cursor to the item returned from the query
            cursor.moveToFirst();
        }

        //From the query result, assign each string to correct parameter position
        //NAME > MUSCLE_GROUP > DIFFICULTY > LOWER_REP_RANGE > UPPER_REP_RANGE > SUGGESTED_TIME > SELECTED
        ExerciseObject eObject = new ExerciseObject(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

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


    //retrieving all exercises across multiple muscl groups
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
    public List<ExerciseObject> getDifficultyExercises(String _difficulty)
    {
        if (_difficulty != "Easy" || _difficulty != "Intermediate" || _difficulty != "Advanced")
        {
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

}
