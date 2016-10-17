package com.longngo.moviebox.wear;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longngo.moviebox.wear.adapter.BaseAdapter;
import com.ngohoang.along.appcore.common.DynamicHeightImageView;
import com.ngohoang.along.appcore.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.ngohoang.along.appcore.presentation.presentor.main.MainPresentationModel;
import com.ngohoang.along.appcore.presentation.presentor.main.MainPresenter;
import com.ngohoang.along.appcore.presentation.presentor.main.MainView;
import com.ngohoang.along.appcore.presentation.viewmodel.BaseVM;
import com.ngohoang.along.appcore.presentation.viewmodel.MovieVM;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresentationModel,MainView,MainPresenter> implements MainView,WearableListView.ClickListener{

    @BindView(R.id.pager_shots)
    GridViewPager mShotsPager;
    @BindView(R.id.page_indicator)
    DotsPageIndicator mPageIndicator;



    private BaseAdapter baseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    protected void performFieldInjection() {
        WearApplication.getMainComponent().inject(this);
    }

    @NonNull
    @Override
    protected MainPresentationModel createPresentationModel() {
        return new MainPresentationModel();
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);

    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();

    }

    @Override
    public void onExitAmbient() {

        super.onExitAmbient();
    }



    @Override
    protected void onStart() {
        super.onStart();
        presenter.fetchRepository(1);
    }

    @Override
    public void showProcess() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void updateView() {
        if(baseAdapter == null){
            baseAdapter = new BaseAdapter(this, presenter.getPresentationModel());
            mShotsPager.setAdapter(baseAdapter);
            mPageIndicator.setPager(mShotsPager);
            mPageIndicator.setDotColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mPageIndicator.setDotColorSelected(ContextCompat.getColor(this, R.color.colorAccent));
            mPageIndicator.setDotRadius(4);
        }else{
            mShotsPager.setAdapter(baseAdapter);
        }
    }

    @Override
    public void onClick(WearableListView.ViewHolder v) {
        Integer tag = (Integer) v.itemView.getTag();
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

}
