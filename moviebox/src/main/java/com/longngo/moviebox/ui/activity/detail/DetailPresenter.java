package com.longngo.moviebox.ui.activity.detail;

import android.util.Log;

import com.longngo.moviebox.common.coremvp.SimpleMVPPresenter;
import com.longngo.moviebox.common.schedulers.BaseSchedulerProvider;
import com.longngo.moviebox.data.model.Movie;
import com.longngo.moviebox.data.source.MoviesRepository;
import com.longngo.moviebox.ui.viewmodel.BaseVM;
import com.longngo.moviebox.ui.viewmodel.mapper.Mapper;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Admin on 06/10/2016.
 */

public class DetailPresenter extends SimpleMVPPresenter<DetailView,DetailPresentationModel> implements DetailView {
    private static final String TAG = "DetailPresenter";
    private BaseSchedulerProvider baseSchedulerProvider;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    private MoviesRepository competitionsRepository;
    @Inject
    DetailPresenter(BaseSchedulerProvider baseSchedulerProvider, MoviesRepository competitionsRepository) {
        this.baseSchedulerProvider = baseSchedulerProvider;
        this.competitionsRepository = competitionsRepository;
    }

    @Override
    public void attachView(DetailView mvpView, DetailPresentationModel presentationModel) {
        super.attachView(mvpView, presentationModel);
        fetchRepositories("2016");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    public void fetchRepositories(String season){
        if (!getPresentationModel().isShouldFetchRepositories()) {
            return;
        }
        getCompetitions(season);
    }
    private void getCompetitions(String season) {
        mSubscriptions.clear();
        Subscription subscription = competitionsRepository
                .getMovieList(1)
                .map(new Func1<List<Movie>, List<BaseVM>>() {
                    @Override
                    public List<BaseVM> call(List<Movie> competitions) {
                        return Mapper.tranCompetition(competitions);
                    }
                })
                .subscribeOn( baseSchedulerProvider.computation())
                .observeOn( baseSchedulerProvider.ui())
                .subscribe(new Observer<List<BaseVM>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<BaseVM> competitions) {
                        Log.d(TAG, "onNext: "+competitions.size());
                        if (!competitions.isEmpty()) {
                            Log.d(TAG, "onSuccess: "+competitions.size());

                            getPresentationModel().add(competitions);
                            loadCompetitions();

                        } else {
                            Log.d(TAG, "onSuccess: is empty");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void loadCompetitions() {
        if(getMvpView()==null)return;
        getMvpView().loadCompetitions();
    }

    public void refreshData() {
    }

    public void loadMore() {

    }
}
