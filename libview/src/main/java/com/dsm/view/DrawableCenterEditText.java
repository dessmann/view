package com.dsm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by dccjll on 2017/3/22.
 * drawableLeft指定的图片可以居中
 */

public class DrawableCenterEditText extends android.support.v7.widget.AppCompatEditText {
    private final Context context;

    public DrawableCenterEditText(Context context) {
        super(context);
        this.context = context;
    }

    public DrawableCenterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public DrawableCenterEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableLeft = drawables[0];
        if (drawableLeft != null) {
            float textWidth;
            if (!TextUtils.isEmpty(getText().toString())) {
                textWidth = getPaint().measureText(getText().toString());
            } else {
                textWidth = getPaint().measureText(getHint().toString());//hint的字体大小必须设置为EditText的文字字体大小一样，不然，即使文字内容为空，计算的文本宽度仍然变大，目前不知道什么
            }
            int drawablePadding = getCompoundDrawablePadding();
            int drawableWidth;
            drawableWidth = drawableLeft.getIntrinsicWidth();
            float bodyWidth = textWidth + drawableWidth + drawablePadding;
//            LogUtil.e("DrawableCenterEditText", "textWidth=" + textWidth + "\ndrawableWidth=" + drawableWidth + "\ndrawablePadding=" + drawablePadding + "\ngetWidth()=" + getWidth() + "\nsreenWidth=" + ScreenUtils.getScreenWidth(context));
            canvas.translate((getWidth() - bodyWidth) / 2, 0);
        }
        super.onDraw(canvas);
    }
}
