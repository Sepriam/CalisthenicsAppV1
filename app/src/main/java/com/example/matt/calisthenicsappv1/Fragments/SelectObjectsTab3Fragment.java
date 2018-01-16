package com.example.matt.calisthenicsappv1.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.matt.calisthenicsappv1.Adapters.*;
import com.example.matt.calisthenicsappv1.Database.AppDBHandler;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.List;

/**
 * Created by Matt on 11/08/2017.
 */

public class SelectObjectsTab3Fragment extends Fragment {
    private static final String TAG = "SelectObjectsTab3Fragment";



    private Button btrTEST;
    private ListView lvTEST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment3,container,false);

        //createNewLink to database?
        AppDBHandler db = new AppDBHandler(getContext());

        //creating a list of exercises in table that are in shoulders muscle group
        List<ExerciseObject> ExerciseList = db.getAllCExercises("Back");

        //Log the exercises inside the shoulder exercise list
        for (ExerciseObject cn : ExerciseList) {
            String log = "EName: " + cn.getExerciseName() + " ,MGroup: " + cn.getMuscleGroup() + " ,Difficulty: " + cn.getDifficulty() +
                    " ,LowerRepRange: " + String.valueOf(cn.getLowerRepRange()) + " ,UpperRepRange: " + String.valueOf(cn.getUpperRepRange()) +
                    " ,SuggestedTime: " + String.valueOf(cn.getSuggestedTime()) + " ,Selected: " + String.valueOf(cn.isSelected());
            // Writing Contacts to log
            Log.d("Exercise: ", "Back Exercise List Contains: \n" +log);
        }


        //creating a string array that contains names of exercises for use in customListView
        String[] ExerciseNameArray3 = new String[ExerciseList.size()];

        //create an int to increment through array elements
        int incrementArray = 0;

        for (ExerciseObject e : ExerciseList)
        {
            //assign name array element to exercise name in object
            ExerciseNameArray3[incrementArray] = e.getExerciseName();
            //increment the array for next assignment
            incrementArray++;
        }


        ListAdapter myListAdapter = new SelectObjCustomAdapter3(getContext(), ExerciseNameArray3);
        lvTEST = (ListView) view.findViewById(R.id.listview3);
        lvTEST.setAdapter(myListAdapter);


        return view;
    }
}
