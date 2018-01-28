package com.example.matt.calisthenicsappv1.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.matt.calisthenicsappv1.Adapters.SelectMuscleGroupsCAdapter;
import com.example.matt.calisthenicsappv1.Objects.muscleGroupObject;

import com.example.matt.calisthenicsappv1.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class selectMuscleGroupsAct extends AppCompatActivity {

    SelectMuscleGroupsCAdapter _selectMuscleGroupsCAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_muscle_groups);

        createMuscleGroups();

        completeActivityBtnClick();

    }

    private void createMuscleGroups()
    {
        ArrayList<muscleGroupObject> muscleGroupArrayList = new ArrayList<>();

        muscleGroupObject MGO = new muscleGroupObject("Shoulders", false);
        muscleGroupArrayList.add(MGO);
        MGO = new muscleGroupObject("Chest", false);
        muscleGroupArrayList.add(MGO);
        MGO = new muscleGroupObject("Back", false);
        muscleGroupArrayList.add(MGO);
        MGO = new muscleGroupObject("Arms", false);
        muscleGroupArrayList.add(MGO);
        MGO = new muscleGroupObject("Core", false);
        muscleGroupArrayList.add(MGO);

        _selectMuscleGroupsCAdapter = new SelectMuscleGroupsCAdapter(this, R.layout.customlv_muscle_group_element,
                muscleGroupArrayList);
        ListView lv = (ListView) findViewById(R.id.display_muscle_group_LV);

        lv.setAdapter(_selectMuscleGroupsCAdapter);
    }

    private void completeActivityBtnClick()
    {
        //assigning button the the button on activity
        Button endButton = (Button) findViewById(R.id.return_muscle_group_BTN);

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Here we can code in what to pass back to previous activity.
                //creating an arraylist to hold the musclegroup objects
                ArrayList<muscleGroupObject> muscleGroupsSelected = new ArrayList<>();

                muscleGroupsSelected.addAll(_selectMuscleGroupsCAdapter._muscleGroupsArrayList);

                //creating an arraylist of strings to pass back to previous activity
                ArrayList<String> toPassBack = new ArrayList<>();



                //looping through each musclegroup item
                for (int i = 0; i < muscleGroupsSelected.size(); i++)
                {
                    //assign a temporary muscle group object to the current item in arrayList
                    muscleGroupObject MGO = muscleGroupsSelected.get(i);

                    //check if temp object's selected value is true
                    if(MGO.getSelected())
                    {
                        //if true, add its name to the arraylist<string> to be passed back
                        toPassBack.add(MGO.getMuscleGroup());
                    }
                }

                Intent i = getIntent();
                i.putExtra("_result", toPassBack);
                setResult(Activity.RESULT_OK, i);
                finish();

            }
        });

    }
}
