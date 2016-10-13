package com.longngo.moviebox.ui.activity.youtubeplayer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.longngo.moviebox.BuildConfig;
import com.longngo.moviebox.FootballFanApplication;
import com.longngo.moviebox.R;

import java.util.concurrent.ExecutionException;

import static com.longngo.moviebox.R.id.player;

/**
 * Created by Admin on 13/10/2016.
 */

public class QuickYTPlayActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {
    private static final String TRAILER_URL = "TRAILER_URL";
    String finalUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_quick_play);
        int id = getDataFromCallingIntent();
        String url="";
        try {
            url = new GetTrailer().execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        finalUrl = url;
        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(player);


        youTubePlayerView.initialize(BuildConfig.YOUTUBE_API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(finalUrl);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    public static Intent getCallingIntent(Context context, int source) {
        Intent intentToLaunch = new Intent(context, QuickYTPlayActivity.class);
        intentToLaunch.putExtra(TRAILER_URL,source);
        return intentToLaunch;
    }
    int getDataFromCallingIntent() {
        return getIntent().getIntExtra(TRAILER_URL, 0);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(finalUrl);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Error :( " + youTubeInitializationResult.toString(), Toast.LENGTH_LONG)
                .show();
    }

    private class GetTrailer extends AsyncTask<Integer,String,String> {


        @Override
        protected String doInBackground(Integer... params) {
            return FootballFanApplication.getMainComponent().dataManager().getTrailer(params[0]);
        }
    }
}