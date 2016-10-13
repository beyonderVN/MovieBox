package com.longngo.moviebox.data.backend;


import com.google.gson.JsonObject;
import com.longngo.moviebox.data.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MovieBoxServiceApi {

    /**
     * Retrieve a list of competitions
     */


    @GET("movie/now_playing")
    Observable<JsonObject> getMovieList();
    @GET("movie/now_playing")
    Observable<JsonObject> getMovieList(@Query("page") int page);
    @GET("movie/{id}/trailers")
    Call<JsonObject> getTrailer(@Path("id") int id);






}
