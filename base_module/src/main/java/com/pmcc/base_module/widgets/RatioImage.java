package com.pmcc.base_module.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.pmcc.base_module.R;


/**
 * Created by ${zhangshuai} on 2018/11/16.
 * 2751157603@qq.com
 * 宽高比的view
 */
public class RatioImage extends android.support.v7.widget.AppCompatImageView {

    /**
     * ratioSize=高/宽
     */
    private double ratioSize;

    public double getRatioSize() {
        return ratioSize;
    }

    public void setRatioSize(double ratioSize) {
        this.ratioSize = ratioSize;
        invalidate();
    }

    public RatioImage(Context context) {
        super(context);
    }

    public RatioImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray propertys = context.obtainStyledAttributes(attrs, R.styleable.RatioImage);
        ratioSize = propertys.getFloat(R.styleable.RatioImage_ratioSize, 1f);
        propertys.recycle();//否则这次的设定会对下次的使用造成影响
    }

    public RatioImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredWidth() * ratioSize));
    }
}
