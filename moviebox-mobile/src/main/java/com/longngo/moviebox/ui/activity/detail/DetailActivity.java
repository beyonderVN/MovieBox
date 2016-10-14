package com.longngo.moviebox.ui.activity.detail;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.longngo.moviebox.FootballFanApplication;
import com.longngo.moviebox.R;
import com.longngo.moviebox.common.DynamicHeightImageView;
import com.longngo.moviebox.common.ElasticDragDismissFrameLayout;
import com.longngo.moviebox.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.longngo.moviebox.ui.activity.Navigator;
import com.longngo.moviebox.ui.activity.base.BaseActivity;
import com.longngo.moviebox.ui.viewmodel.BaseVM;
import com.longngo.moviebox.ui.viewmodel.MovieVM;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

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
    @BindView(R.id.flPlay)
    FrameLayout flPlay;

    public static Intent getCallingIntent(Context context, BaseVM baseVM){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(MOVIE_ITEM,baseVM);
        return intent;
    }
    private MovieVM getItemFromIntent(Intent intent){
        return (MovieVM) intent.getSerializableExtra(MOVIE_ITEM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setupUI();


    }
    void setupUI(){
        final MovieVM movieVM = getItemFromIntent(getIntent());
        imageView.setRatio(1.5);

        if (imageView!=null) {
            Picasso.with(this).load("https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getPosterPath())
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(movieVM.getMovie().getId()))
                    .into(imageView);
        }

        if (ivBackdropPath != null) {
            Picasso.with(this).load("https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getBackdropPath())
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(movieVM.getMovie().getId()))
                    .transform(new RoundedCornersTransformation(5, 0))
                    .into(ivBackdropPath);
        }

        flPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.navigateToYTPlayActivity(v.getContext(),movieVM.getMovie().getId());


            }
        });


        tvOverview.setText(movieVM.getMovie().getOverview());
        tvTitle.setText(movieVM.getMovie().getTitle());
        tvReleaseDate.setText(Html.fromHtml("<font color=\"BLUE\"><b>Release Date: </b></font>"+movieVM.getMovie().getReleaseDate()+""));
        tvPopularity.setText(Html.fromHtml("<font color=\"BLUE\"><b>Popularity: </b></font>"+movieVM.getMovie().getPopularity()+""));
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
