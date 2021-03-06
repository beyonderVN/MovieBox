package com.longngo.moviebox.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longngo.moviebox.ui.adapter.viewholder.BaseViewHolder;
import com.longngo.moviebox.ui.adapter.vmfactory.HolderFactory;
import com.longngo.moviebox.ui.adapter.vmfactory.HolderFactoryImpl;
import com.ngohoang.along.appcore.presentation.BasePresentationModel;

import com.ngohoang.along.appcore.presentation.viewmodel.BaseVM;


import java.util.List;

import javax.inject.Inject;

/**
 * Created by Long on 10/5/2016.
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder<BaseVM>> {

    public HolderFactory holderFactory = new HolderFactoryImpl();
    private List<BaseVM> list;

    BasePresentationModel basePresentationModel;
    Activity activity;

    public BaseAdapter(Activity activity, BasePresentationModel basePresentationModel) {
        this.basePresentationModel = basePresentationModel;
        this.list = basePresentationModel.getVisitableList();
        this.activity = activity;
    }

    @Override
    public BaseViewHolder<BaseVM> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (parent != null) {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            return holderFactory.createHolder(viewType, view);
        }
        throw new RuntimeException("Parent is null");
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<BaseVM> holder, int position) {
        if(holder!=null){
            holder.bind(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getVMType(holderFactory);
    }
}
