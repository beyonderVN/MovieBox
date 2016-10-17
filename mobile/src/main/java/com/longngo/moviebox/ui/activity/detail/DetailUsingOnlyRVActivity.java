package com.longngo.moviebox.ui.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.longngo.moviebox.FootballFanApplication;
import com.longngo.moviebox.R;

import com.longngo.moviebox.ui.activity.base.BaseActivity;
import com.longngo.moviebox.ui.adapter.BaseAdapter;

import com.ngohoang.along.appcore.common.ElasticDragDismissFrameLayout;
import com.ngohoang.along.appcore.common.recyclerviewhelper.InfiniteScrollListener;
import com.ngohoang.along.appcore.presentation.presentor.detail.DetailPresentationModel;
import com.ngohoang.along.appcore.presentation.presentor.detail.DetailPresenter;
import com.ngohoang.along.appcore.presentation.presentor.detail.DetailView;
import com.ngohoang.along.appcore.presentation.viewmodel.BaseVM;
import com.ngohoang.along.appcore.presentation.viewmodel.MovieDetailVM;
import com.ngohoang.along.appcore.presentation.viewmodel.MovieVM;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;



public class DetailUsingOnlyRVActivity extends BaseActivity<DetailPresentationModel,DetailView,DetailPresenter> implements DetailView {
    private static final String TAG = "CompetionDetailActivity";
    public static final String MOVIE_ITEM = "MOVIE_ITEM";

    @BindView(R.id.activity_detail)
    ElasticDragDismissFrameLayout draggableFrame;
    private ElasticDragDismissFrameLayout.SystemChromeFader chromeFader;
    @BindInt(R.integer.column_num)
    int columnNum;
    @BindView(R.id.rvMovieDetail)
    RecyclerView listRV;
    BaseAdapter baseAdapter;


    public static Intent getCallingIntent(Context context, BaseVM baseVM){
        Intent intent = new Intent(context, DetailUsingOnlyRVActivity.class);
        intent.putExtra(MOVIE_ITEM,baseVM);
        return intent;
    }
    private MovieVM getItemFromIntent(Intent intent){
        return (MovieVM) getIntent().getSerializableExtra(MOVIE_ITEM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_rv_detail);
        ButterKnife.bind(this);
        setupUI();


    }
    void setupUI(){

        chromeFader = new ElasticDragDismissFrameLayout.SystemChromeFader(this);
        draggableFrame.addListener(
                new ElasticDragDismissFrameLayout.SystemChromeFader(this) {
                    @Override
                    public void onDragDismissed() {
                        supportFinishAfterTransition();
                    }
                });
        setupRV();


    }


    void setupRV(){
        final StaggeredGridLayoutManager staggeredGridLayoutManagerVertical =
                new StaggeredGridLayoutManager(
                        columnNum, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        staggeredGridLayoutManagerVertical.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        staggeredGridLayoutManagerVertical.invalidateSpanAssignments();

        listRV.setLayoutManager(staggeredGridLayoutManagerVertical);
        listRV.setHasFixedSize(true);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(3000);
        itemAnimator.setRemoveDuration(3000);
        listRV.setItemAnimator(itemAnimator);
        listRV.setNestedScrollingEnabled(true);
        listRV.addOnScrollListener(new InfiniteScrollListener(staggeredGridLayoutManagerVertical) {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore: ");
                try {
                    presenter.loadMore();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

            @Override
            public boolean isLoading() {
                return presenter.getPresentationModel().isLoadingMore();
            }

            @Override
            public boolean isNoMore() {
                return presenter.getPresentationModel().isNoMore();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        if(baseAdapter == null){
            MovieVM movieVM = getItemFromIntent(getIntent());
            baseAdapter = new BaseAdapter(this, presenter.getPresentationModel());
            MovieDetailVM movieDetailVM = new MovieDetailVM(movieVM.getMovie());
            movieDetailVM.setFullSpan(true);
            presenter.getPresentationModel().add(movieDetailVM);
            listRV.setAdapter(baseAdapter);
        }
    }

    @Override
    protected void performFieldInjection() {
        FootballFanApplication.getMainComponent().inject(this);
    }

    @NonNull
    @Override
    protected DetailPresentationModel createPresentationModel() {
        return new DetailPresentationModel();
    }


    @Override
    public void loadCompetitions() {

    }
    @Override
    protected void onResume() {
        super.onResume();
        // clean up after any fab expansion
        draggableFrame.addListener(chromeFader);
    }
    @Override
    protected void onPause() {
        draggableFrame.removeListener(chromeFader);
        super.onPause();
    }

}
