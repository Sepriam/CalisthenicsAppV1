package com.example.matt.calisthenicsappv1.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.matt.calisthenicsappv1.Database.AppDBHandler;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;

/**
 * Created by Matt on 20/01/2018.
 */

public class DisplayRandomExercisesAdapter extends ArrayAdapter<ExerciseObject> {

    public ArrayList<ExerciseObject> exerciseList;


    public DisplayRandomExercisesAdapter(@NonNull Context context, @LayoutRes int resource, /*This is the resource we're passing in*/ ArrayList<ExerciseObject> _exerciseList) {

        //this will be the what the listview's element's layout will look like and from array we're passing in params above
        super(context, resource, _exerciseList);
        //passing the arraylist we recieved from params into global arraylist for adapter
        this.exerciseList = new ArrayList<ExerciseObject>();
        this.exerciseList.addAll(_exerciseList);
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        //inflating the layout
        LayoutInflater myInflater = LayoutInflater.from(getContext());

        //creating a custom view that uses the display exercise only custom listview element layout
        View CustomView = myInflater.inflate(R.layout.customlv_display_exercise, parent, false);

        //the position is just the position of the item in the list
        ExerciseObject singleExercise = getItem(position);

        //assinging the widgets to an appropriate variable name
        TextView nameTextView = (TextView) CustomView.findViewById(R.id.displayCLVTV1);
        TextView MuscleGroupTextView = (TextView) CustomView.findViewById(R.id.displayCLVTV2);

        //setting the values into the widgets
        nameTextView.setText(singleExercise.getExerciseName());
        MuscleGroupTextView.setText(singleExercise.getMuscleGroup());

        return CustomView;
    }
}
