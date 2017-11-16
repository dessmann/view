package com.dsm.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dsm.platform.util.ScreenUtils;
import com.dsm.view.R;

/**
 * CustomDialog
 *
 * @author SJL
 * @date 2017/2/21
 */

public class CustomDialog extends Dialog {
    /**
     * 布局
     */
    private View view;
    private RelativeLayout rlCustomDialog;
    private TextView tvTitle;
    private ScrollView svContent;
    private LinearLayout llContent;
    private TextView tvContentLeft;
    private TextView tvContentCenter;
    private TextView tvContentRight;
    private RelativeLayout rlCancel;
    private TextView tvCancel;
    private TextView tvConfirm;
    private DialogListener dialogListener;

    private int screenHeight = -1;
    private CharSequence titleText;
    private CharSequence contentLeftTopText;
    private CharSequence contentCenterText;
    private CharSequence contentRightButtomText;
    private CharSequence cancelText;
    private CharSequence confirmText;
    private int titleColor;
    private int contentColor;
    private int cancelColor;
    private int confirmColor;
    private boolean showCancel;
    private int windowAnimate;
    private boolean cancelableOnTouchOutside;

    public interface DialogListener {
        void onConfirm(CustomDialog customDialog);

        void onCancel(CustomDialog customDialog);
    }

    public CustomDialog(Context context) {
        this(context, 0);
    }

    private CustomDialog(Context context, int themeResId) {
        super(context, R.style.CustomDialog);
        this.cancelableOnTouchOutside = false;
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        if (view == null) {
            setDefaultLayout();
        }
        setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setAttributes(layoutParams);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        if (windowAnimate != -1) {
            window.setWindowAnimations(windowAnimate);
        }
        initData();
        super.show();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //标题文字
        tvTitle.setText(titleText);
        //标题颜色
        tvTitle.setTextColor(titleColor);
        //内容左上角文字
        if (contentLeftTopText == null) {
            tvContentLeft.setVisibility(View.GONE);
        } else {
            tvContentLeft.setVisibility(View.VISIBLE);
            tvContentLeft.setText(contentLeftTopText);
        }
        //内容中间文字
        if (contentCenterText == null) {
            tvContentCenter.setVisibility(View.GONE);
        } else {
            tvContentCenter.setVisibility(View.VISIBLE);
            tvContentCenter.setText(contentCenterText);
        }
        //内容右下角文字
        if (contentRightButtomText == null) {
            tvContentRight.setVisibility(View.GONE);
        } else {
            tvContentRight.setVisibility(View.VISIBLE);
            tvContentRight.setText(contentRightButtomText);
        }
        //内容文字颜色
        tvContentLeft.setTextColor(contentColor);
        tvContentCenter.setTextColor(contentColor);
        tvContentRight.setTextColor(contentColor);
        //取消文字
        tvCancel.setText(cancelText);
        //取消文字颜色
        tvCancel.setTextColor(cancelColor);
        //确定文字
        tvConfirm.setText(confirmText);
        //确定文字颜色
        tvConfirm.setTextColor(confirmColor);
        rlCancel.setVisibility(showCancel ? View.VISIBLE : View.GONE);
        setDialogHeight();
    }

    /**
     * 设置高度
     */
    private void setDialogHeight() {
        screenHeight = screenHeight == -1 ? ScreenUtils.getScreenHeight(getContext()) : screenHeight;
        ViewGroup.LayoutParams layoutParams = svContent.getLayoutParams();
        rlCustomDialog.measure(0, 0);
        if (llContent.getMeasuredHeight() > screenHeight * 0.45) {
            layoutParams.height = (int) (screenHeight * 0.45);
            svContent.setLayoutParams(layoutParams);
        }
    }

    private void findView() {
        rlCustomDialog = (RelativeLayout) view.findViewById(R.id.rlCustomDialog);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        svContent = (ScrollView) view.findViewById(R.id.svContent);
        llContent = (LinearLayout) view.findViewById(R.id.llContent);
        tvContentLeft = (TextView) view.findViewById(R.id.tvContentLeft);
        tvContentCenter = (TextView) view.findViewById(R.id.tvContentCenter);
        tvContentRight = (TextView) view.findViewById(R.id.tvContentRight);
        LinearLayout llBottom = (LinearLayout) view.findViewById(R.id.llBottom);
        rlCancel = (RelativeLayout) view.findViewById(R.id.rlCancel);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogListener != null) {
                    dialogListener.onCancel(CustomDialog.this);
                }
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogListener != null) {
                    dialogListener.onConfirm(CustomDialog.this);
                }
                dismiss();
            }
        });
    }

    /**
     * 设置布局
     */
    private void setDefaultLayout() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.view_dialog_custom, null);
        findView();
    }


    public CustomDialog setShowCancel(boolean showCancel) {
        this.showCancel = showCancel;
        return this;
    }

    public CustomDialog setConfirmColor(int confirmColor) {
        this.confirmColor = ContextCompat.getColor(getContext(),confirmColor == -1 ? R.color.textColor33 : confirmColor);
        return this;
    }

    public CustomDialog setCancelColor(int cancelColor) {
        this.cancelColor = ContextCompat.getColor(getContext(),cancelColor == -1 ? R.color.textColor33 : cancelColor);
        return this;
    }

    public CustomDialog setContentColor(int contentColor) {
        this.contentColor = ContextCompat.getColor(getContext(),contentColor == -1 ? R.color.textColor33 : contentColor);
        return this;
    }

    public CustomDialog setTitleColor(int titleColor) {
        this.titleColor = ContextCompat.getColor(getContext(),titleColor == -1 ? R.color.textColor33 : titleColor);
        return this;
    }

    public CustomDialog setConfirmText(CharSequence confirmText) {
        this.confirmText = confirmText == null ? getContext().getString(R.string.view_dialog_confirm) : confirmText;
        return this;
    }

    public CustomDialog setCancelText(CharSequence cancelText) {
        this.cancelText = cancelText == null ? getContext().getString(R.string.view_dialog_cancel) : cancelText;
        return this;
    }

    public CustomDialog setContentRightButtomText(CharSequence contentRightButtomText) {
        this.contentRightButtomText = contentRightButtomText;
        return this;
    }

    public CustomDialog setContentCenterText(CharSequence contentCenterText) {
        this.contentCenterText = contentCenterText;
        return this;
    }

    public CustomDialog setContentLeftTopText(CharSequence contentLeftTopText) {
        this.contentLeftTopText = contentLeftTopText;
        return this;
    }

    public CustomDialog setTitleText(CharSequence titleText) {
        this.titleText = titleText == null ? getContext().getString(R.string.view_dialog_title) : titleText;
        return this;
    }

    public CustomDialog setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
        return this;
    }

    public CustomDialog setWindowAnimate(int windowAnimate) {
        this.windowAnimate = windowAnimate;
        return this;
    }

    public CustomDialog setCancelableOnTouchOutside(boolean cancelableOnTouchOutside) {
        this.cancelableOnTouchOutside = cancelableOnTouchOutside;
        setCanceledOnTouchOutside(cancelableOnTouchOutside);
        return this;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (cancelableOnTouchOutside) {
                tvCancel.performClick();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public TextView getTvContentCenter() {
        return tvContentCenter;
    }
}
