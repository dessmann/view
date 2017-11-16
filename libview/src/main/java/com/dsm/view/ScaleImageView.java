package com.dsm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * RectImageView
 *
 * @author SJL
 * @date 2017/10/11
 */

public class ScaleImageView extends AppCompatImageView {
    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
    }

    private int scaleWidth;
    private int scaleHeight;

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ScaleImageView);
        scaleWidth = typedArray.getInteger(R.styleable.ScaleImageView_scaleWidth, 0);
        scaleHeight = typedArray.getInteger(R.styleable.ScaleImageView_scaleHeight, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(scaleWidth!=0&&scaleHeight!=0) {
            int width = MeasureSpec.getSize(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY));
            setMeasuredDimension(width, width*scaleHeight/scaleWidth);
        }
    }
}
