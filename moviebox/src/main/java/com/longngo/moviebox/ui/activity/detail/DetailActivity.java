package com.longngo.moviebox.ui.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.longngo.moviebox.FootballFanApplication;
import com.longngo.moviebox.R;
import com.longngo.moviebox.ui.activity.base.BaseActivity;
import com.longngo.moviebox.ui.adapter.BaseAdapter;
import com.longngo.moviebox.ui.viewmodel.BaseVM;
import com.longngo.moviebox.ui.viewmodel.MovieVM;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity<DetailPresentationModel,DetailView,DetailPresenter> implements DetailView {
    private static final String TAG = "CompetionDetailActivity";
    public static final String MOVIE_ITEM = "MOVIE_ITEM";
    @BindView(R.id.ivHeader)
    ImageView imageView;
    @BindView(R.id.list)
    RecyclerView listRV;

    BaseAdapter baseAdapter;

    public static Intent getCallingIntent(Context context, BaseVM baseVM){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(MOVIE_ITEM,baseVM);
        return intent;
    }
    private MovieVM getItemFromIntent(Intent intent){
        return (MovieVM) getIntent().getSerializableExtra(MOVIE_ITEM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setupUI();
        MovieVM movieVM = getItemFromIntent(getIntent());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185_and_h278_bestv2"+movieVM.getMovie().getBackdropPath())
                .asBitmap().into(imageView);

    }
    void setupUI(){
        setupRV();
        setupToolBar();
    }
    void setupToolBar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("JavBox");
    }
    void setupRV(){
        listRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
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
