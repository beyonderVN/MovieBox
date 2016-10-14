package com.ngohoang.along.appcore.presentation.presentor.main;

import android.util.Log;



import com.ngohoang.along.appcore.common.coremvp.SimpleMVPPresenter;
import com.ngohoang.along.appcore.common.schedulers.BaseSchedulerProvider;
import com.ngohoang.along.appcore.data.model.Movie;
import com.ngohoang.along.appcore.data.source.MoviesRepository;
import com.ngohoang.along.appcore.presentation.viewmodel.BaseVM;
import com.ngohoang.along.appcore.presentation.viewmodel.mapper.Mapper;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Admin on 06/10/2016.
 */

public class MainPresenter extends SimpleMVPPresenter<MainView,MainPresentationModel> implements MainView{
    private static final String TAG = "MainPresenter";
    private BaseSchedulerProvider baseSchedulerProvider;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    private MoviesRepository competitionsRepository;
    @Inject
    MainPresenter(BaseSchedulerProvider baseSchedulerProvider, MoviesRepository competitionsRepository) {
        this.baseSchedulerProvider = baseSchedulerProvider;
        this.competitionsRepository = competitionsRepository;
    }

    @Override
    public void attachView(MainView mvpView, MainPresentationModel presentationModel) {
        super.attachView(mvpView, presentationModel);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    public void fetchRepositoryFirst(){
        if (!getPresentationModel().isShouldFetchRepositories()) {
            return;
        }
        showProcess();
        mSubscriptions.clear();
        getPresentationModel().reset();
        Subscription subscription = competitionsRepository
                .getMovieList(getPresentationModel().getNextPage())
                .map(new Func1<List<Movie>, List<BaseVM>>() {
                    @Override
                    public List<BaseVM> call(List<Movie> competitions) {
                        return Mapper.tranToVM(competitions);
                    }
                })
                .subscribeOn( baseSchedulerProvider.computation())
                .observeOn( baseSchedulerProvider.ui())
                .subscribe(new Observer<List<BaseVM>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(List<BaseVM> competitions) {
                        Log.d(TAG, "onNext: "+competitions.size());
                        if (!competitions.isEmpty()) {
                            Log.d(TAG, "onSuccess: "+competitions.size());

                            getPresentationModel().addAndCollapse(competitions);

                            getPresentationModel().setCurrentPage(getPresentationModel().getCurrentPage()+1);
                        } else {
                            Log.d(TAG, "onSuccess: is empty");
                        }
                        getPresentationModel().stopLoadingMore();
                        updateView();

                    }
                });
        mSubscriptions.add(subscription);
    }
    public void fetchMore(){

        mSubscriptions.clear();
        Subscription subscription = competitionsRepository
                .getMovieList(getPresentationModel().getNextPage())
                .map(new Func1<List<Movie>, List<BaseVM>>() {
                    @Override
                    public List<BaseVM> call(List<Movie> competitions) {
                        return Mapper.tranToVM(competitions);
                    }
                })
                .subscribeOn( baseSchedulerProvider.computation())
                .observeOn( baseSchedulerProvider.ui())
                .subscribe(new Observer<List<BaseVM>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(List<BaseVM> competitions) {
                        Log.d(TAG, "onNext: "+competitions.size());
                        if (!competitions.isEmpty()) {
                            Log.d(TAG, "onSuccess: "+competitions.size());
                            stopLoadingMore();
                            getPresentationModel().addAndCollapse(competitions);
                            getPresentationModel().setCurrentPage(getPresentationModel().getCurrentPage()+1);
                            updateView();
                        } else {
                            Log.d(TAG, "onSuccess: is empty");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void showProcess() {
        if(getMvpView()==null)return;
        getMvpView().showProcess();
    }

    @Override
    public void showContent() {
        if(getMvpView()==null)return;
        getMvpView().showContent();
    }

    @Override
    public void updateView() {
        if(getMvpView()==null)return;
        getMvpView().updateView();

    }

    public void loadMore() {
        startLoadingMore();
        fetchMore();
    }

    private void startLoadingMore() {
        getPresentationModel().startLoadingMore();
        updateView();
    }
    private void stopLoadingMore() {
        getPresentationModel().stopLoadingMore();
        updateView();
    }
    public void refreshData() {
        getPresentationModel().reset();
        fetchRepositoryFirst();
    }

    public void fixState(int column) {
        getPresentationModel().stopLoadingMore();
        getPresentationModel().fixLayout(column);
    }
}
