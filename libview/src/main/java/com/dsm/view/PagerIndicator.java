package com.dsm.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.dsm.platform.util.DensityUtil;

/**
 * PagerIndicator
 *
 * @author SJL
 * @date 2017/3/17
 */

public class PagerIndicator extends View {
    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private Paint paint;
    //间隔
    private final int space = 9;
    private final int indicator = 21;
    private int left = 0;
    //个数
    private final int num = 3;
    private float offset = 0;
    private Bitmap bmpNormal;
    private Bitmap bmpSelected;

    private void init(AttributeSet attrs) {
        int resNormal = R.mipmap.lead_normal;
        int resSelected = R.mipmap.lead_select;
        bmpNormal = BitmapFactory.decodeResource(getResources(), resNormal);
        bmpSelected = BitmapFactory.decodeResource(getResources(), resSelected);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        left = (getMeasuredWidth()- DensityUtil.dp2px(getContext(),num*(space + indicator)-space))/2;
        drawNormal(canvas);
        drawSelected(canvas);
    }

    private void drawNormal(Canvas canvas) {
        for (int i = 0; i < num; i++) {
            canvas.drawBitmap(bmpNormal, DensityUtil.dp2px(getContext(),i * (space + indicator))+left, 0, paint);
        }
    }

    private void drawSelected(Canvas canvas) {
        canvas.drawBitmap(bmpSelected, DensityUtil.dp2px(getContext(),(space + indicator) * offset)+left, 0, paint);
    }

    public void setOffset(float offset) {
        this.offset = offset;
        invalidate();
    }
}
