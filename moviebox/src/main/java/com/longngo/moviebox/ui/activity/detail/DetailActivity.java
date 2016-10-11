package com.longngo.moviebox.ui.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longngo.moviebox.FootballFanApplication;
import com.longngo.moviebox.R;
import com.longngo.moviebox.common.DynamicHeightImageView;
import com.longngo.moviebox.common.ElasticDragDismissFrameLayout;
import com.longngo.moviebox.ui.activity.base.BaseActivity;
import com.longngo.moviebox.ui.viewmodel.BaseVM;
import com.longngo.moviebox.ui.viewmodel.MovieVM;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity<DetailPresentationModel,DetailView,DetailPresenter> implements DetailView {
    private static final String TAG = "CompetionDetailActivity";
    public static final String MOVIE_ITEM = "MOVIE_ITEM";

    @BindView(R.id.activity_detail)
    ElasticDragDismissFrameLayout draggableFrame;
    private ElasticDragDismissFrameLayout.SystemChromeFader chromeFader;
    @BindView(R.id.ivHeader)
    DynamicHeightImageView imageView;
    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;


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


    }
    void setupUI(){
        MovieVM movieVM = getItemFromIntent(getIntent());
        Log.d(TAG, "setupUI: "+"https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getBackdropPath());
        Log.d(TAG, "setupUI: "+"https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getPosterPath());
        imageView.setRatio(1.5);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getPosterPath())
                .asBitmap()
                .into(imageView);
        tvOverview.setText(movieVM.getMovie().getOverview());
        tvTitle.setText(movieVM.getMovie().getTitle());
        tvReleaseDate.setText(Html.fromHtml("<b>Release Date: </b><small>"+movieVM.getMovie().getReleaseDate()+"<small>"));



        chromeFader = new ElasticDragDismissFrameLayout.SystemChromeFader(this);
        draggableFrame.addListener(
                new ElasticDragDismissFrameLayout.SystemChromeFader(this) {
                    @Override
                    public void onDragDismissed() {
                        supportFinishAfterTransition();
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

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
