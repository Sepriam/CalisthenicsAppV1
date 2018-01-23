package com.example.matt.calisthenicsappv1.Activities;

import java.util.ArrayList;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
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
import com.example.matt.calisthenicsappv1.Fragments.SelectObjectsTab1Fragment;
import com.example.matt.calisthenicsappv1.Fragments.SelectObjectsTab2Fragment;
import com.example.matt.calisthenicsappv1.Fragments.SelectObjectsTab3Fragment;
import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;

import java.util.ArrayList;

public class SelectObjectsAct extends AppCompatActivity {

    private Button btnMain;

    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionPageAdapter;

    private ViewPager mViewPager;

    private ViewPager lastView;

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

                //for each item in arraylist, check if it's bool is true. If true, mark the current checkbox and then delete from array


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
        viewPager.setAdapter(adapter);
    }

    //reset selection of each exercise
    public void _ResetSelection(View view) {

        return;
        //we can remove the above fragment once you reset each checkbox to unticked




        //AppDBHandler db = new AppDBHandler(this);

        //db.returnTrueExercises();

    }
}

