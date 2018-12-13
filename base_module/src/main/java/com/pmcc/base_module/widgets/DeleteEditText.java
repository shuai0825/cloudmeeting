package com.pmcc.base_module.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.pmcc.base_module.R;


/**
 * Created by ${zhangshuai} on 2018/11/20.
 * 2751157603@qq.com
 * 带删除按钮的editext
 */
public class DeleteEditText extends android.support.v7.widget.AppCompatEditText {
    private int delet_img;// 删除图片

    public DeleteEditText(Context context) {
        super(context);
    }

    public DeleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DeleteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray propertys = context.obtainStyledAttributes(attrs, R.styleable.DeleteEditText);
        delet_img = propertys.getResourceId(R.styleable.DeleteEditText_delet_img, R.mipmap.base_clear_content);
        propertys.recycle();
    }
    //放置图片的位置
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (length() < 1) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, this.getResources().getDrawable(delet_img), null);
        }
    }
    //删除图片点击操作
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (delet_img !=0 && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }
}
