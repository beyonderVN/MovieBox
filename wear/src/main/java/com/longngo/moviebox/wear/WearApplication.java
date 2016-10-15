package com.longngo.moviebox.wear;

import android.app.Application;
import android.content.Context;

import com.longngo.moviebox.wear.di.DaggerMainComponent;
import com.longngo.moviebox.wear.di.MainComponent;
import com.longngo.moviebox.wear.di.MainModule;

/**
 * Created by Admin on 15/10/2016.
 */

public class WearApplication extends Application {
    public static Context mContext;
    static MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        setupGraph();
    }
    void setupGraph(){
        mainComponent = DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build();

    }

    public static MainComponent getMainComponent() {
        return mainComponent;
    }
}

