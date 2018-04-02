package com.example.matt.calisthenicsappv1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matt.calisthenicsappv1.Database.AppDBHandler;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.Objects.RoutineObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;

public class DisplaySavedRoutinesActivity extends AppCompatActivity {

    //global arraylist of routineObjects
    ArrayList<RoutineObject> routineObjects = new ArrayList<>();
    //global arraylist to hold all routine names as strings
    ArrayList<String> routineNames = new ArrayList<>();
    //listview object
    ListView routineLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_saved_routines);

        getAllRoutines();

        assignListView();
    }


    //assign the routine objects to the global arraylist
    private void getAllRoutines()
    {
        //make connection to db class
        AppDBHandler db = new AppDBHandler(this);
        //assign database routine objects to class' global list of these objects
        routineObjects.addAll( db.returnAllRoutines());

        Log.d("this routine name: ", String.valueOf(routineObjects.size()));



        //for loop to add all routine names to arraylist
        for (RoutineObject RO : routineObjects)
        {
            routineNames.add(RO.getRoutineName());
        }

        db.close();
    }

    //assignment of listview variable
    private void assignListView()
    {
        routineLV = (ListView) findViewById(R.id.savedRoutines_LV);

        //assigning an arrayAdapter of string list
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, routineNames);

        routineLV.setAdapter(itemsAdapter);

        setLVOnClick();
    }

    private void setLVOnClick()
    {
        routineLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //creating a connection to the database class
                AppDBHandler db = new AppDBHandler(getApplicationContext());

                //get the name of the item clicked
                String tempName = routineNames.get(position);



                //creating a temporary routine object
                RoutineObject tempRoutine = new RoutineObject();


                //find the string in the list of routineObjects
                for(RoutineObject RO : routineObjects)
                {
                    if (RO.getRoutineName().equals(tempName))
                    {
                        //set routineObject
                        tempRoutine = RO;

                        Log.d("this routine name: ", RO.getRoutineName());
                        Log.d("this no. of exercises: ", String.valueOf(RO.getExerciseNames().length));

                        break;
                    }
                }

                for (String a : tempRoutine.getExerciseNames())
                {
                    Log.d("this string: ", a);
                }

                Intent i = new Intent(getApplicationContext(), DisplayCustomRoutineAct.class);
                Bundle passBundle = new Bundle();
                //db funciton
                ArrayList<ExerciseObject> EO = new ArrayList<>();
                EO.addAll(db.decipherRoutine(tempRoutine));
                Log.d("this many exercises: ", String.valueOf(EO.size()));
                passBundle.putSerializable("CheckedExerciseList", EO);
                i.putExtras(passBundle);
                startActivity(i);

            }
        });
    }




}
