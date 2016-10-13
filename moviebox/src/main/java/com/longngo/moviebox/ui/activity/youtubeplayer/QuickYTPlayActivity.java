package com.longngo.moviebox.ui.activity.youtubeplayer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.longngo.moviebox.BuildConfig;
import com.longngo.moviebox.FootballFanApplication;
import com.longngo.moviebox.R;

import java.util.concurrent.ExecutionException;

import static android.R.attr.id;

/**
 * Created by Admin on 13/10/2016.
 */

public class QuickYTPlayActivity extends YouTubeBaseActivity {
    private static final String TRAILER_URL = "TRAILER_URL";

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
        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        final String finalUrl = url;
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

    private class GetTrailer extends AsyncTask<Integer,String,String> {


        @Override
        protected String doInBackground(Integer... params) {
            return FootballFanApplication.getMainComponent().dataManager().getTrailer(params[0]);
        }
    }
}