package com.longngo.moviebox.wear.adapter;

import android.content.Context;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longngo.moviebox.wear.adapter.holder.BaseViewHolder;
import com.ngohoang.along.appcore.presentation.BasePresentationModel;
import com.ngohoang.along.appcore.presentation.viewmodel.BaseVM;

import java.util.List;

/**
 * Created by Admin on 15/10/2016.
 */

public class BaseAdapter extends GridPagerAdapter {
    public HolderFactory holderFactory = new HolderFactoryImpl();
    List<BaseVM> baseVMs;
    Context context;
    public BaseAdapter(Context context, BasePresentationModel basePresentationModel) {
        super();
        this.context =context;
        baseVMs = basePresentationModel.getVisitableList();
    }

    @Override
    public int getRowCount() {
        return baseVMs.size();
    }

    @Override
    public int getColumnCount(int i) {
        return 1;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i, int i1) {

        if (viewGroup != null) {
            BaseVM baseVM = baseVMs.get(i);
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(baseVM.getVMType(holderFactory), viewGroup, false);
            BaseViewHolder viewHolder =  holderFactory.createHolder(baseVM.getVMType(holderFactory), view);
            viewHolder.bind(baseVM);
            viewGroup.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {
        viewGroup.removeView((View)o);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

}

