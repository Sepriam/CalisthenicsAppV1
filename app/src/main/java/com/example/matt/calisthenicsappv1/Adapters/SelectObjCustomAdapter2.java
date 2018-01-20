package com.example.matt.calisthenicsappv1.Adapters;

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
 * Created by Matt on 10/08/2017.
 */

//note to self - Context is basically just background infomation

public class SelectObjCustomAdapter2 extends ArrayAdapter<ExerciseObject>{

    public ArrayList<ExerciseObject> exerciseList;

    AppDBHandler db = new AppDBHandler(getContext());

    public SelectObjCustomAdapter2(@NonNull Context context, @LayoutRes int resource, /*This is the resource we're passing in*/ ArrayList<ExerciseObject> _exerciseList) {

        //this will be the what the listview's element's layout will look like and from array we're passing in params above
        super(context, resource, _exerciseList);
        this.exerciseList = new ArrayList<ExerciseObject>();
        this.exerciseList.addAll(_exerciseList);
    }

    public class ViewHolder
    {
        TextView exerciseName;
        CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SelectObjCustomAdapter2.ViewHolder holder = null;

        //logging the value of the position of item
        Log.v("ConvertView", String.valueOf(position));

        //the position is just the position of the item in the list
        ExerciseObject singleExercise = getItem(position);
        singleExercise = db.getExercise(singleExercise.getExerciseName());

        if (convertView == null)
        {
            LayoutInflater myInflater = LayoutInflater.from(getContext());

            /*View customView*/ convertView = myInflater.inflate(R.layout.customlv_choose_exercise_element, parent, false);

            holder = new SelectObjCustomAdapter2.ViewHolder();
            holder.exerciseName = (TextView) convertView.findViewById(R.id.myTextview);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.myCheckbox);

            convertView.setTag(holder);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox cb = (CheckBox) view;
                    //get the associated name from tag
                    //grab this exercise from db
                    //update this exercise

                    ExerciseObject _exercise = (ExerciseObject) cb.getTag();

                    if (_exercise == null)
                    {
                        String log = "Null Object";
                        // Writing Contacts to log
                        Log.d("Didn't grab exercise as", log);
                    }
                    else
                    {
                        String log = "Exercise Name: " + _exercise.getExerciseName();
                        // Writing Contacts to log
                        Log.d("Contains:", log);
                    }


                    //We have the object,
                    //next thing to do is to.....
                    //Pass the name into the update method which should update the isselected option
                    //createNewLink to database?
                    //AppDBHandler db = new AppDBHandler(getContext());
                    db.updateIsSelected(_exercise);
                }
            });

        }
        else
        {
            holder = (SelectObjCustomAdapter2.ViewHolder) convertView.getTag();
        }


        holder.exerciseName.setText(singleExercise.getExerciseName());
        holder.checkBox.setChecked(singleExercise.isSelected());

        holder.checkBox.setTag(singleExercise);


        return convertView;
    }
}
