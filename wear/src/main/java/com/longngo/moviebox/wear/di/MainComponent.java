package com.longngo.moviebox.wear.di;

import android.content.Context;


import com.longngo.moviebox.wear.MainActivity;
import com.ngohoang.along.appcore.data.backend.MovieBoxService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Long on 7/8/2016.
 */
@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {
    Context context();
    MovieBoxService movieBoxService();
    void inject(MainActivity mainActivity);




}
