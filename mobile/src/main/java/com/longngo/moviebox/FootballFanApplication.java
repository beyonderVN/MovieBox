package com.longngo.moviebox;

import android.app.Application;
import android.content.Context;

import com.longngo.moviebox.di.DaggerMainComponent;
import com.longngo.moviebox.di.MainComponent;
import com.longngo.moviebox.di.MainModule;


/**
 * Created by Long on 10/5/2016.
 */

public class FootballFanApplication extends Application {

    public static Context mContext;
    static MainComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        setupGraph();
    }

    void setupGraph(){
        applicationComponent = DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build();

    }

    public static MainComponent getMainComponent() {
        return applicationComponent;
    }
}
