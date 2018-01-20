package com.example.matt.calisthenicsappv1.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;

/**
 * Created by Matt on 20/01/2018.
 */

public class DisplayRandomExercisesAndSuggestionAdapter extends ArrayAdapter<ExerciseObject> {

    public ArrayList<ExerciseObject> exerciseList;


    public DisplayRandomExercisesAndSuggestionAdapter(@NonNull Context context, @LayoutRes int resource, /*This is the resource we're passing in*/ ArrayList<ExerciseObject> _exerciseList) {

        //this will be the what the listview's element's layout will look like and from array we're passing in params above
        super(context, resource, _exerciseList);
        //passing the arraylist we recieved from params into global arraylist for adapter
        this.exerciseList = new ArrayList<ExerciseObject>();
        this.exerciseList.addAll(_exerciseList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        //inflating the listview
        LayoutInflater myInflater = LayoutInflater.from(getContext());

        //creating a new custom view that uses display exercises and suggestions custom listview element layout
        View CustomView = myInflater.inflate(R.layout.customlv_display_exercise_and_suggestions, parent, false);

        //the position is just the position of the item in the list
        ExerciseObject singleExercise = getItem(position);

        //Assigning all of the widgets to a respective variable name
        TextView nameTextView = (TextView) CustomView.findViewById(R.id.suggestionsCLVTV1);
        TextView MuscleGroupTextView = (TextView) CustomView.findViewById(R.id.suggestionsCLVTV2);
        TextView RepRangeTextView = (TextView) CustomView.findViewById(R.id.suggestionsCLVTV3);
        TextView SuggestedTimeTextView = (TextView) CustomView.findViewById(R.id.suggestionsCLVTV4);

        //retrieving the desired layout of a rep range
        String RepRange = "";
        //Defaulting the string to N/A if there's no suggested rep range
        if(singleExercise.getLowerRepRange() == 0 && singleExercise.getUpperRepRange() == 0)
        {
            RepRange = "N/A";
        }
        else
        {
            RepRange = String.valueOf(singleExercise.getLowerRepRange()) + " - " + String.valueOf(singleExercise.getUpperRepRange());
        }

        //Setting the values to their places in the listview element
        nameTextView.setText(singleExercise.getExerciseName());
        MuscleGroupTextView.setText(singleExercise.getMuscleGroup());
        RepRangeTextView.setText(RepRange);
        SuggestedTimeTextView.setText(String.valueOf(singleExercise.getSuggestedTime()));

        return CustomView;
    }

}
