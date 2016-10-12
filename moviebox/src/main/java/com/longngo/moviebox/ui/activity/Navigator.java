package com.longngo.moviebox.ui.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

import com.longngo.moviebox.ui.activity.detail.DetailActivity;
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
}
