package com.longngo.moviebox.ui.activity.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 10/7/2016.
 */

public abstract class BasePresentationModel<T> implements Serializable {
    protected List<T> visitableList ;
    public abstract boolean isShouldFetchRepositories();
    protected BasePresentationModel() {
        this.visitableList = new ArrayList<>();
    }

    public List<T> getVisitableList() {
        return visitableList;
    }

    public void setVisitableList(List<T> visitableList) {
        this.visitableList = visitableList;
    }
}
