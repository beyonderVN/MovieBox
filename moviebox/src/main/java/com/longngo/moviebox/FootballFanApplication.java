package com.longngo.moviebox;

import android.app.Application;
import android.content.Context;

import com.longngo.moviebox.common.di.DaggerMainComponent;
import com.longngo.moviebox.common.di.MainComponent;
import com.longngo.moviebox.common.di.MainModule;

/**
 * Created by Long on 10/5/2016.
 */

public class FootballFanApplication extends Application {

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
