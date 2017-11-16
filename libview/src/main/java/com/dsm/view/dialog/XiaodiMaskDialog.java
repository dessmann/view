package com.dsm.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dsm.platform.util.DensityUtil;
import com.dsm.platform.util.log.LogUtil;
import com.dsm.view.R;

/**
 * Created by dccjll on 2017/3/13.
 * 小嘀管家蒙版提醒对话框2
 */

public class XiaodiMaskDialog extends Dialog {

    private Context context;//加载对话框的上下文

    private int maskImgResId;//蒙版图片的资源ID
    private int maskRelativeLayoutWidth;//蒙版图片对话框的宽度
    private int maskRelativeLayoutHeight;//蒙版图片对话框的高度
    private String maskBtnString;//按钮上显示的文字
    private boolean cancelableOnTouchOutside = true;//触摸对话框外部是否关闭对话框，默认关闭
    private OnConfirmListener onConfirmListener;//按钮点击相应监听器

    public interface OnConfirmListener{
        void onCofirm();
    }

    public XiaodiMaskDialog(Context context) {
        super(context, R.style.CustomMaskDialog);
        this.context = context;
    }

    public XiaodiMaskDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected XiaodiMaskDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
        }
        setContentView(R.layout.dialog_mask2);

        RelativeLayout maskRelativeLayout = (RelativeLayout) findViewById(R.id.maskRelativeLayout);
        if (maskRelativeLayoutWidth > 0 && maskRelativeLayoutHeight > 0) {
            ViewGroup.LayoutParams layoutParams = maskRelativeLayout.getLayoutParams();
            layoutParams.width = DensityUtil.dp2px(context, maskRelativeLayoutWidth);
            layoutParams.height = DensityUtil.dp2px(context, maskRelativeLayoutHeight);
            maskRelativeLayout.setLayoutParams(layoutParams);
        }
        ImageView maskImg = (ImageView) findViewById(R.id.maskImg);
        if(maskImgResId > 0){
            maskImg.setImageResource(maskImgResId);
        }
        Button maskBtn = (Button) findViewById(R.id.maskBtn);
        LogUtil.i("maskBtn=" + maskBtn + "\nmaskBtnString=" + maskBtnString);
        maskBtn.setBackgroundResource(R.drawable.button_normal);
        maskBtn.setText(maskBtnString);
        if(TextUtils.isEmpty(maskBtnString)){
            maskBtn.setVisibility(View.GONE);
        }else{
            maskBtn.setVisibility(View.VISIBLE);
        }
        if (onConfirmListener != null){
            maskBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isShowing()){
                                dismiss();
                            }
                            onConfirmListener.onCofirm();
                        }
                    }
            );
        }
    }

    public XiaodiMaskDialog setMaskImgResId(int maskImgResId) {
        this.maskImgResId = maskImgResId;
        return this;
    }

    public XiaodiMaskDialog setMaskRelativeLayoutWidth(int maskRelativeLayoutWidth) {
        this.maskRelativeLayoutWidth = maskRelativeLayoutWidth;
        return this;
    }

    public XiaodiMaskDialog setMaskRelativeLayoutHeight(int maskRelativeLayoutHeight) {
        this.maskRelativeLayoutHeight = maskRelativeLayoutHeight;
        return this;
    }

    public XiaodiMaskDialog setMaskBtnString(String maskBtnString) {
        this.maskBtnString = maskBtnString;
        return this;
    }

    public XiaodiMaskDialog setCancelableOnTouchOutside(boolean cancelableOnTouchOutside) {
        this.cancelableOnTouchOutside = cancelableOnTouchOutside;
        return this;
    }

    public XiaodiMaskDialog setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
        return this;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if (cancelableOnTouchOutside && isShowing()){
                dismiss();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
