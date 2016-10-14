package com.longngo.moviebox.ui.viewmodel;

import com.longngo.moviebox.data.model.Movie;
import com.longngo.moviebox.ui.adapter.vmfactory.VMTypeFactory;

/**
 * Created by Long on 10/5/2016.
 */

public class NoMoreItemVM extends BaseVM{


    @Override
    public int getVMType(VMTypeFactory vmTypeFactory) {
        return vmTypeFactory.getType(this);
    }

}
