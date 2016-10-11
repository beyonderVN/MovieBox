package com.longngo.moviebox.ui.activity.main;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.longngo.moviebox.R;
import com.longngo.moviebox.FootballFanApplication;
import com.longngo.moviebox.common.recyclerviewhelper.InfiniteScrollListener;
import com.longngo.moviebox.ui.adapter.BaseAdapter;
import com.longngo.moviebox.ui.activity.base.BaseActivity;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresentationModel,MainView,MainPresenter> implements MainView{
    private static final String TAG = "MainActivity";
    @BindInt(R.integer.column_num)
    int columnNum;
    @BindView(R.id.list)
    RecyclerView listRV;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    BaseAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupUI();
    }
    void setupUI(){
        setupRV();
        setupToolBar();
        setupSwipeRefreshLayout();
    }

    private void setupSwipeRefreshLayout() {
        swipeRefresh.setColorSchemeResources(R.color.colorPrimaryDark);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listRV.setLayoutFrozen(true);
                swipeRefresh.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        Log.d("Swipe", "Refreshing Number");
                        presenter.refreshData();
                    }
                }, 500);

            }
        });
    }

    void setupToolBar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Jav Box");
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
            baseAdapter = new BaseAdapter(this, presenter.getPresentationModel());
            listRV.setAdapter(baseAdapter);
        }
    }

    @Override
    protected void performFieldInjection() {
        FootballFanApplication.getMainComponent().inject(this);
    }

    @NonNull
    @Override
    protected MainPresentationModel createPresentationModel() {
        return new MainPresentationModel();
    }


    @Override
    public void updateView() {
        baseAdapter.notifyDataSetChanged();
        listRV.setLayoutFrozen(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id){
            case R.id.action_search:
                Toast.makeText(this, "R.id.action_search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite :
                Toast.makeText(this, "R.id.action_favorite", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
