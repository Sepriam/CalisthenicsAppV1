package com.example.matt.calisthenicsappv1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.matt.calisthenicsappv1.R;

public class StretchingTypeSelectionAct extends AppCompatActivity {

    //includes in this:
    /*
    1- functionality to take the user to one of 2 other pages
    1.1 - button 1 to take user to random stretching selection screen
    1.2 - button 2 to take user to custom stretching selection screen
     */

    Button toRandomStretchBtn, toCustomStretchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stretching_type_selection);

        initiateWidgets();

    }

    //function to assign the button variables to their respective widgets in layout
    private void initiateWidgets()
    {
        toRandomStretchBtn = (Button) findViewById(R.id.toRandomStretchAct_btn);
        toCustomStretchBtn = (Button) findViewById(R.id.toCustomStretchAct_btn);
    }

    //on click functionality method for random stretch btn
    //functionality to take user to the next activity _
    public void toRandomStretch_Click(View view) {
        Intent i = new Intent(this, );
        startActivity(i);

    }

    //on click functionality method for random stretch btn
    //functionality to take user to the next activity _
    public void toCustomStretch_Click(View view) {
        Intent i = new Intent(this, );
        startActivity(i);
    }
}
