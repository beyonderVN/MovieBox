package com.ngohoang.along.appcore.presentation.viewmodel;

import com.ngohoang.along.appcore.data.model.Movie;

/**
 * Created by Long on 10/5/2016.
 */

public class MovieVM extends BaseVM{

    private Movie movie;

    public MovieVM(Movie movie) {
        this.movie = movie;
    }
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public int getVMType(VMTypeFactory vmTypeFactory) {
        return vmTypeFactory.getType(this);
    }

    @Override
    public String toString() {
        return movie.toString();
    }
}
