package com.longngo.moviebox.ui.activity.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.longngo.moviebox.FootballFanApplication;
import com.longngo.moviebox.R;
import com.longngo.moviebox.ui.activity.base.BaseActivity;
import com.longngo.moviebox.ui.adapter.BaseAdapter;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity<DetailPresentationModel,DetailView,DetailPresenter> implements DetailView {
    private static final String TAG = "CompetionDetailActivity";
    @BindInt(R.integer.column_num)
    int columnNum;
    @BindView(R.id.list)
    RecyclerView listRV;

    BaseAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupUI();
    }
    void setupUI(){
        setupRV();
        setupToolBar();
    }
    void setupToolBar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
    void setupRV(){
        final StaggeredGridLayoutManager staggeredGridLayoutManagerVertical =
                new StaggeredGridLayoutManager(
                        columnNum, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        staggeredGridLayoutManagerVertical.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        staggeredGridLayoutManagerVertical.invalidateSpanAssignments();
        RecyclerView.LayoutManager layoutGridManager = new GridLayoutManager(this, columnNum);
        listRV.setLayoutManager(staggeredGridLayoutManagerVertical);
        listRV.setHasFixedSize(true);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(3000);
        itemAnimator.setRemoveDuration(3000);
        listRV.setItemAnimator(itemAnimator);
    }
    @Override
    protected void onStart() {
        super.onStart();

        baseAdapter = new BaseAdapter(presenter.getPresentationModel().getVisitableList());
        listRV.setAdapter(baseAdapter);
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
        baseAdapter.notifyDataSetChanged();
    }
}
