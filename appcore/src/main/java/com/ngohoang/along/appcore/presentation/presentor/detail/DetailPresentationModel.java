package com.ngohoang.along.appcore.presentation.presentor.detail;



import com.ngohoang.along.appcore.presentation.BasePresentationModel;
import com.ngohoang.along.appcore.presentation.viewmodel.BaseVM;

import java.util.List;

/**
 * Created by Admin on 06/10/2016.
 */

public class DetailPresentationModel extends BasePresentationModel<BaseVM> {
    public DetailPresentationModel() {
        super();
    }

    @Override
    public boolean isShouldFetchRepositories() {
        return visitableList==null||visitableList.size()==0;
    }
    public void add(BaseVM baseVM){
        visitableList.add(baseVM);
    }
    public void add(List<BaseVM> baseVMs){
        visitableList.addAll(baseVMs);
    }

    public boolean isLoadingMore() {
        return true;
    }

    public boolean isNoMore() {
        return true;
    }

}
