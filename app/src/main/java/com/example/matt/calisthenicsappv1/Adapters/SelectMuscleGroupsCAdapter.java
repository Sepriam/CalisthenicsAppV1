package com.example.matt.calisthenicsappv1.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.calisthenicsappv1.Database.AppDBHandler;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;
import com.example.matt.calisthenicsappv1.Objects.muscleGroupObject;

import java.util.ArrayList;

/**
 * Created by Matt on 25/01/2018.
 */

public class SelectMuscleGroupsCAdapter extends ArrayAdapter<muscleGroupObject> {

    public ArrayList<muscleGroupObject> _muscleGroupsArrayList;

    public SelectMuscleGroupsCAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<muscleGroupObject> _muscleGroups) {
        super(context, resource, _muscleGroups);
        //passing all the items for the adapter into the adapter's Array List
        this._muscleGroupsArrayList = new ArrayList<>();
        this._muscleGroupsArrayList.addAll(_muscleGroups);

    }

    public class ViewHolder {
        TextView displayMuscleGroupTV;
        CheckBox selectMuscleGroupCB;
    }


    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater myInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

            /*View customView*/
            convertView = myInflater.inflate(R.layout.customlv_muscle_group_element, null);

            holder = new ViewHolder();
            holder.displayMuscleGroupTV = convertView.findViewById(R.id.custom_display_MGTV);
            holder.selectMuscleGroupCB = convertView.findViewById(R.id.muscleGroupsCB);

            convertView.setTag(holder);

            holder.selectMuscleGroupCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    muscleGroupObject muscleGroup = (muscleGroupObject) cb.getTag();

                    muscleGroup.setSelected(cb.isChecked());


                }
            });
        }
        else
        {
                holder = (ViewHolder) convertView.getTag();
        }

        muscleGroupObject muscleGroupObject = _muscleGroupsArrayList.get(position);

        holder.displayMuscleGroupTV.setText(muscleGroupObject.getMuscleGroup());
        holder.selectMuscleGroupCB.setChecked(muscleGroupObject.getSelected());

        holder.selectMuscleGroupCB.setTag(muscleGroupObject);

        return convertView;
    }
}
