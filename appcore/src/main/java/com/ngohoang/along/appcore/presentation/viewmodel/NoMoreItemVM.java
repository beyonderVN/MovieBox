package com.ngohoang.along.appcore.presentation.viewmodel;



/**
 * Created by Long on 10/5/2016.
 */

public class NoMoreItemVM extends BaseVM{


    @Override
    public int getVMType(VMTypeFactory vmTypeFactory) {
        return vmTypeFactory.getType(this);
    }

}
