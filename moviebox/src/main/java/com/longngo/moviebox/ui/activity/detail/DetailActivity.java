package com.longngo.moviebox.ui.activity.detail;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.longngo.moviebox.FootballFanApplication;
import com.longngo.moviebox.R;
import com.longngo.moviebox.common.DynamicHeightImageView;
import com.longngo.moviebox.common.ElasticDragDismissFrameLayout;
import com.longngo.moviebox.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.longngo.moviebox.ui.activity.base.BaseActivity;
import com.longngo.moviebox.ui.viewmodel.BaseVM;
import com.longngo.moviebox.ui.viewmodel.MovieVM;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity<DetailPresentationModel,DetailView,DetailPresenter> implements DetailView {
    private static final String TAG = "DetailActivity";
    public static final String MOVIE_ITEM = "MOVIE_ITEM";
    @Nullable
    @BindView(R.id.activity_detail)
    ElasticDragDismissFrameLayout draggableFrame;
    private ElasticDragDismissFrameLayout.SystemChromeFader chromeFader;
    @Nullable
    @BindView(R.id.ivHeader)
    DynamicHeightImageView imageView;
    @Nullable
    @BindView(R.id.ivBackdropPath)
    DynamicHeightImageView ivBackdropPath;
    @Nullable
    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @Nullable
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @Nullable
    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;
    @Nullable
    @BindView(R.id.tvPopularity)
    TextView tvPopularity;
    @Nullable
    @BindView(R.id.srbStar)
    SimpleRatingBar srbStart;

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
//        Glide.with(this)
//                .load("https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getPosterPath())
//                .into(imageView);
        if (imageView!=null) {
            Picasso.with(this).load("https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getPosterPath())
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(movieVM.getMovie().getId()))
                    .into(imageView);
        }

        if (ivBackdropPath != null) {
            Picasso.with(this).load("https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getBackdropPath())
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(movieVM.getMovie().getId()))
                    .into(ivBackdropPath);
        }



        tvOverview.setText(movieVM.getMovie().getOverview());
        tvTitle.setText(movieVM.getMovie().getTitle());
        tvReleaseDate.setText(Html.fromHtml("<b>Release Date: </b>"+movieVM.getMovie().getReleaseDate()+""));
        tvPopularity.setText(Html.fromHtml("<b>Popularity: </b>"+movieVM.getMovie().getPopularity()+""));
        Log.d(TAG, "movieVM.getMovie().getVoteAverage() "+movieVM.getMovie().getVoteAverage());

        SimpleRatingBar.AnimationBuilder builder = srbStart.getAnimationBuilder()
                .setRepeatCount(0)
                .setRepeatMode(ValueAnimator.INFINITE)
                .setInterpolator(new BounceInterpolator())

                .setRatingTarget(movieVM.getMovie().getVoteAverage())

                ;
        builder.start();



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
