package com.example.matt.calisthenicsappv1.Activities;

import java.util.ArrayList;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.example.matt.calisthenicsappv1.Adapters.SectionsPageAdapter;
import com.example.matt.calisthenicsappv1.Database.AppDBHandler;
import com.example.matt.calisthenicsappv1.Fragments.*;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;
import java.util.List;

public class SelectObjectsAct extends AppCompatActivity {

    private Button btnMain;

    private SectionsPageAdapter mSectionPageAdapter;

    private ViewPager mViewPager;

    ArrayList<ExerciseObject> checkedExerciseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_objects);

        //Merge button with it's source in XML
        //---------------------- Need to create a method here that displays all currently displayed items in a list.
        btnMain = (Button) findViewById(R.id.buttonMain);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }


    //PageViewer is the tabs across top of this activity
    private void setupViewPager(ViewPager viewPager)
    {
        //Included functionality from pageViewer import
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        //renaming
        adapter.addFragment(new SelectObjectsTab1Fragment(), "Shoulders");
        adapter.addFragment(new SelectObjectsTab2Fragment(), "Chest");
        adapter.addFragment(new SelectObjectsTab3Fragment(), "Back");
        adapter.addFragment(new SelectObjectsTab4Fragment(), "Arms");
        adapter.addFragment(new SelectObjectsTab5Fragment(), "Core");
        adapter.addFragment(new SelectObjectsTab6Fragment(), "Legs");
        viewPager.setAdapter(adapter);
    }

    //reset selection of each exercise
    public void _ResetSelection(View view) {


        //we can remove the above fragment once you reset each checkbox to unticked
        AppDBHandler db = new AppDBHandler(this);

        db.returnTrueExercises();

        //restart the current activity to refresh all the listviews
        Intent intent = getIntent();
        finish();
        startActivity(intent);

        /*
        note to self:
        could not find suitable / efficient way to refresh the current fragment
        so instead i just restarted the entire activity
        this removes the possibility of having reset items and still having checkboxes selected
         */

        return;

    }

    public void _PassCustomRoutineToDisplay(View view)
    {
        AppDBHandler db = new AppDBHandler(this);


        /*ArrayList<ExerciseObject> exerciseListToPass =  new ArrayList<>();

        //transfer the first x amount of exercises into new exercise list
        for (int i = 0; i < numOfExercises; i++)
        {
            //note -- not very efficient for memory usage
            //this will transfer only x amount
            exerciseListToPass.add(difficultyExerciseList.get(i));
        }

        //clear the difficultyExerciseList just ot clear memory..
        difficultyExerciseList.clear();*/

        ArrayList<ExerciseObject> selectExercisesList = new ArrayList<>();

        List<ExerciseObject> tempList = db.getAllSelectedExercises();

        for (int i = 0; i < tempList.size(); i++)
        {
            selectExercisesList.add(tempList.get(i));
        }

        tempList.clear();

        Intent i = new Intent(this, DisplayCustomRoutineAct.class);
        Bundle passBundle = new Bundle();
        passBundle.putSerializable("CheckedExerciseList", selectExercisesList);
        i.putExtras(passBundle);
        startActivity(i);

    }
}

