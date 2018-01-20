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

public class SelectObjCustomAdapter1 extends ArrayAdapter<ExerciseObject>{

    public ArrayList<ExerciseObject> exerciseList;


    public SelectObjCustomAdapter1(@NonNull Context context, @LayoutRes int resource, /*This is the resource we're passing in*/ ArrayList<ExerciseObject> _exerciseList) {

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



        ViewHolder holder = null;

        //logging the value of the position of item
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null)
        {
            LayoutInflater myInflater = LayoutInflater.from(getContext());

            /*View customView*/ convertView = myInflater.inflate(R.layout.customlv_choose_exercise_element, parent, false);

            holder = new ViewHolder();
            holder.exerciseName = (TextView) convertView.findViewById(R.id.myTextview);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.myCheckbox);

            convertView.setTag(holder);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox cb = (CheckBox) view;
                    ExerciseObject _exercise = (ExerciseObject) cb.getTag();

                    //We have the object,
                    //next thing to do is to.....
                    //Pass the name into the update method which should update the isselected option
                    //createNewLink to database?
                    AppDBHandler db = new AppDBHandler(getContext());
                    db.updateIsSelected(_exercise);
                }
            });

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        //the position is just the position of the item in the list
        ExerciseObject singleExercise = getItem(position);

        //The CustomView is the one we created above

        //AppDBHandler db = new AppDBHandler(getContext());
        /*String ExerciseName = singleExercise.getExerciseName();
        singleExercise = db.getExercise(ExerciseName);
        */
        //db.close();

        holder.exerciseName.setText(singleExercise.getExerciseName());
        holder.checkBox.setChecked(singleExercise.isSelected());

        holder.checkBox.setTag(singleExercise);


        return convertView;
    }
}
