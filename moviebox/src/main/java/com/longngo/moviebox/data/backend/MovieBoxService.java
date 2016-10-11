package com.longngo.moviebox.data.backend;


import android.util.Log;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.longngo.moviebox.data.model.Movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

@Singleton
public class MovieBoxService {
    private static final String TAG = "MovieBoxService";

    private final MovieBoxServiceApi movieBoxServiceApi;

    @Inject
    public MovieBoxService(MovieBoxServiceApi footballServiceApi) {
        movieBoxServiceApi = footballServiceApi;
    }

    @RxLogObservable
    public Observable<List<Movie>> getCompetitions() {
        return movieBoxServiceApi.getMovieList()
                .map(new Func1<JsonObject, List<Movie>>() {
                    @Override
                    public List<Movie> call(JsonObject jsonObject) {
                        Log.d(TAG, "getMovieList: "+jsonObject.toString());
                        Type listType = new TypeToken<ArrayList<Movie>>(){}.getType();
                        List<Movie> movies = (new Gson()).fromJson(jsonObject.getAsJsonArray("results"),listType);
                        return movies;
                    }
                });
    }

    @RxLogObservable
    public Observable<List<Movie>> getCompetitions(int page) {
        return movieBoxServiceApi.getMovieList(page)
                .map(new Func1<JsonObject, List<Movie>>() {
                    @Override
                    public List<Movie> call(JsonObject jsonObject) {
                        Log.d(TAG, "getMovieList: "+jsonObject.toString());
                        Type listType = new TypeToken<ArrayList<Movie>>(){}.getType();
                        List<Movie> movies = (new Gson()).fromJson(jsonObject.getAsJsonArray("results"),listType);
                        return movies;
                    }
                });
    }


}