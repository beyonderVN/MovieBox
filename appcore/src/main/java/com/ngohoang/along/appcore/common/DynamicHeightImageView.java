package com.ngohoang.along.appcore.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by yuvaraj on 3/4/16.
 */
public class DynamicHeightImageView extends ImageView {
    private static final String TAG = "DynamicHeightImageView";
    private double whRatio = 0;

    public DynamicHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHeightImageView(Context context) {
        super(context);
    }

    public void setRatio(double ratio) {
        whRatio = ratio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


//		if (whRatio != 0) {
//			int width = getMeasuredWidth();
//			int height = (int)(whRatio * width);
//			setMeasuredDimension(width, height);
////			Log.d(TAG, "onMeasure: "+whRatio);
//		}else{
//			if(!(getDrawable() ==null)){
//				Log.d(TAG, "getDrawable().getIntrinsicHeight(): "+getDrawable().getIntrinsicHeight());
//				Log.d(TAG, "getIntrinsicWidth(): "+getDrawable().getIntrinsicWidth());
//
//				int width = getDrawable().getIntrinsicWidth();
//				int height = getDrawable().getIntrinsicHeight();
//				if(height>0&&width>0){
//					whRatio = (double) height/(double)width;
//					setMeasuredDimension(width, height);
//				}
//			}
//
//
//		}
        if (whRatio != 0) {
            int width = getMeasuredWidth();
            int height = (int) (whRatio * width);
            setMeasuredDimension(width, height);
//			Log.d(TAG, "onMeasure: "+whRatio);
        } else {

            if (!(getDrawable() == null)) {
                Log.d(TAG, "getDrawable().getIntrinsicHeight(): " + getDrawable().getIntrinsicHeight());
                Log.d(TAG, "getIntrinsicWidth(): " + getDrawable().getIntrinsicWidth());

                int width = getDrawable().getIntrinsicWidth();
                int height = getDrawable().getIntrinsicHeight();
                if (height > 0 && width > 0) {
                    whRatio = (double) height / (double) width;
                    setMeasuredDimension(getMeasuredWidth(), (int) (whRatio * getMeasuredWidth()));
                }
            }
        }

    }

}
