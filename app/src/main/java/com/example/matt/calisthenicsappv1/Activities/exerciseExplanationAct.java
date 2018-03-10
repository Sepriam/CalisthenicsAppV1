package com.example.matt.calisthenicsappv1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.calisthenicsappv1.Objects.ExerciseObject;
import com.example.matt.calisthenicsappv1.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class exerciseExplanationAct extends YouTubeBaseActivity {

    TextView descriptionTV;
    YouTubePlayerView youTubePlayer;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    //Button playYoutubeVidBtn;

    ExerciseObject passedObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_explanation);

        //retrieval of the item passed across intent
        Intent i = getIntent();
        passedObject = (ExerciseObject) i.getSerializableExtra("itemPassed");

        if (passedObject.getMuscleGroup().equals(""))
        {
            //small hack - checks whether there's a musclegroup attached to object, 1this should always be false as object is required to have musclegroup attached
            Log.d("exercise Explanation:", " Exercise failed to be retrieved across intents");
            //display that this error occured in a toast
            Toast.makeText(this, "Exercise Not Passed Across Intent", Toast.LENGTH_SHORT).show();
        }

        initiateWidgets();

        /*
        Todo:
        get extra from intent that passes string values of the exercise selected from previous page's listview

        use string values to search through the database for the exercise corresponding to the one passed

        Grab the video url and the tips from the exercise in database
         */

    }


    private void initiateWidgets()
    {
        //initiate the widgets
        descriptionTV = (TextView)findViewById(R.id.exerciseExplanation_TV2);
        youTubePlayer = (YouTubePlayerView)findViewById(R.id.youtubePlayer);
        //playYoutubeVidBtn = (Button)findViewById(R.id.playYoutubeVideo_btn);

        //set widget text
        descriptionTV.setText(passedObject.getTips());

        final String vidURL = passedObject.getVideoURL();

        //initialise youtubePlayer
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //takes the url of video
                youTubePlayer.loadVideo(vidURL);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youTubePlayer.initialize("AIzaSyAXQZ_OJCmsqI24uTc9eDNFPrxp-q1L1rQ", onInitializedListener);

        /*playYoutubeVidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

    }
}
