package com.longngo.moviebox.di;

import android.content.Context;

import com.longngo.moviebox.ui.activity.detail.DetailActivity;
import com.longngo.moviebox.ui.activity.detail.DetailUsingOnlyRVActivity;
import com.longngo.moviebox.ui.activity.main.MainActivity;
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

//    ActivityModule plus(ActivityModule homeModule);
    void inject(MainActivity mainActivity);


    void inject(DetailActivity detailActivity);
    void inject(DetailUsingOnlyRVActivity detailUsingOnlyRVActivity);


}
