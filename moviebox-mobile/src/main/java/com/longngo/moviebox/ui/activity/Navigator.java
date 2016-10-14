package com.longngo.moviebox.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.longngo.moviebox.ui.activity.detail.DetailActivity;
import com.longngo.moviebox.ui.activity.youtubeplayer.QuickYTPlayActivity;
import com.longngo.moviebox.ui.viewmodel.BaseVM;


/**
 * Created by Admin on 07/10/2016.
 */

public class Navigator {
    public static void navigateToDetailActivity(Context context, BaseVM baseVM,ActivityOptions ops) {
        if (context != null) {
            Intent intentToLaunch = DetailActivity.getCallingIntent(context, baseVM);
//            Intent intentToLaunch = DetailUsingOnlyRVActivity.getCallingIntent(context, baseVM);
            context.startActivity(intentToLaunch,ops.toBundle());
        }
    }

    public static void navigateToYTPlayActivity(Context context, int source) {
        if (context != null) {

            Intent intentToLaunch = QuickYTPlayActivity.getCallingIntent(context, source);
            context.startActivity(intentToLaunch);
        }
    }
    public static void navigateYTStandalonePlayerActivity(Context context ) {
        Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, "AIzaSyBImIX-GsZ1Uwxtdf68iIOpNB01jDItW1k", "5xVh-7ywKpE");
        context.startActivity(intent);
    }
}
