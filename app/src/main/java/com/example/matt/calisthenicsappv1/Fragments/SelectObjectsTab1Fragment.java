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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 11/08/2017.
 */

public class SelectObjectsTab1Fragment extends Fragment {
    private static final String TAG = "SelectObjectsTab1FragmentSelectObjectsTab1Fragment";



    private Button btrTEST;
    private ListView lvTEST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_select_objects1,container,false);

        //btrTEST = (Button) view.findViewById(R.id.button1);

        //createNewLink to database?
        AppDBHandler db = new AppDBHandler(getContext());

        //creating a list of exercises in table that are in shoulders muscle group
        List<ExerciseObject> ExerciseList = db.getAllCExercises("Shoulders");

        //Log the exercises inside the shoulder exercise list
        for (ExerciseObject cn : ExerciseList) {
                String log = "EName: " + cn.getExerciseName() + " ,MGroup: " + cn.getMuscleGroup() + " ,Difficulty: " + cn.getDifficulty() +
                        " ,LowerRepRange: " + String.valueOf(cn.getLowerRepRange()) + " ,UpperRepRange: " + String.valueOf(cn.getUpperRepRange()) +
                        " ,SuggestedTime: " + String.valueOf(cn.getSuggestedTime()) + " ,Selected: " + String.valueOf(cn.isSelected());
                // Writing Contacts to log
                Log.d("Exercise: ", "Shoulder Exercise List Contains: \n" +log);
            }


        ArrayList<ExerciseObject> arrayListExercises = new ArrayList<ExerciseObject>(ExerciseList);


        ListAdapter myListAdapter = new SelectObjCustomAdapter1(getContext(), R.layout.customlv_choose_exercise_element, arrayListExercises);
        lvTEST = (ListView) view.findViewById(R.id.listview1);
        lvTEST.setAdapter(myListAdapter);



        if (savedInstanceState == null)
        {

        }
        else
        {

        }


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

}
