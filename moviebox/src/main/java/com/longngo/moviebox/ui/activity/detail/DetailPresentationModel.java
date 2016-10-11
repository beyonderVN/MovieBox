package com.longngo.moviebox.ui.activity.detail;

import com.longngo.moviebox.ui.activity.base.BasePresentationModel;
import com.longngo.moviebox.ui.viewmodel.BaseVM;

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
}
