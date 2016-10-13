package com.longngo.moviebox.ui.activity.main;

import android.util.Log;

import com.longngo.moviebox.ui.activity.base.BasePresentationModel;
import com.longngo.moviebox.ui.viewmodel.BaseVM;
import com.longngo.moviebox.ui.viewmodel.LoadingMoreVM;

import java.util.List;

/**
 * Created by Admin on 06/10/2016.
 */

public class MainPresentationModel extends BasePresentationModel<BaseVM> {
    private static final String TAG = "MainPresentationModel";
    public MainPresentationModel() {
        super();
    }

    @Override
    public boolean isShouldFetchRepositories() {
        return visitableList==null||visitableList.size()==0;
    }
    public void add(BaseVM baseVM){
        visitableList.add(baseVM);
    }
    int countNonFullSpanItem = 0;
    int nonFullSpanItemPosition = 0;
    public void addAndCollapse(BaseVM baseVM){

        if (countNonFullSpanItem%2==0){
            Log.d(TAG, "countNonFullSpanItem%2 "+countNonFullSpanItem);
            visitableList.add(baseVM);
            if(!baseVM.isFullSpan()){
                countNonFullSpanItem++;
                Log.d(TAG, "countNonFullSpanItem: "+countNonFullSpanItem);
            }
        }else{
            if(baseVM.isFullSpan()){
                visitableList.add(baseVM);
            }else {
                for (int i = visitableList.size()-1; i >=0; i--) {
                    Log.d(TAG, "i: "+i);
                    Log.d(TAG, "i: "+visitableList.get(i).isFullSpan());
                    if(!visitableList.get(i).isFullSpan()){
                        visitableList.add(i+1,baseVM);
                        countNonFullSpanItem++;
                        Log.d(TAG, "countNonFullSpanItem: "+countNonFullSpanItem);
                        break;
                    }
                }

            }
        }
    }
    public void addAndCollapse(List<BaseVM> baseVMs){
        for (BaseVM baseVM: baseVMs
             ) {
            addAndCollapse(baseVM);
        }
    }
    public void add(List<BaseVM> baseVMs){
        visitableList.addAll(baseVMs);
    }
    private boolean loadingMore = false;
    public boolean isLoadingMore() {
        return loadingMore;
    }


    public void setNoMore(boolean noMore) {
        this.noMore = noMore;
    }

    private boolean noMore =false;
    public boolean isNoMore() {
        return noMore;
    }

    public void reset() {
        getVisitableList().clear();
        currentPage = 0;
        noMore =false;
        loadingMore =false;
        countNonFullSpanItem=0;
    }
    int currentPage = 0;

    public int getCurrentPage() {
        return currentPage;
    }
    public int getNextPage() {
        return ++currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public void startLoadingMore() {
        if (loadingMore) return;
        loadingMore = true;
        add(new LoadingMoreVM());
    }
    public void stopLoadingMore() {
        if (!loadingMore) return;
        getVisitableList().remove(getVisitableList().size() - 1);
        loadingMore = false;
    }
}
