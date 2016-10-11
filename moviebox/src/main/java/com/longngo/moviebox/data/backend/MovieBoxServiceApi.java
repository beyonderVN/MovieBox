package com.longngo.moviebox.data.backend;


import com.google.gson.JsonObject;
import com.longngo.moviebox.data.model.Movie;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface MovieBoxServiceApi {

    /**
     * Retrieve a list of competitions
     */


    @GET("movie/now_playing")
    Observable<JsonObject> getMovieList();





}
