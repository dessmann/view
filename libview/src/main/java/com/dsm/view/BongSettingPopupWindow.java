package com.dsm.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

@SuppressWarnings("ALL")
public class BongSettingPopupWindow implements View.OnClickListener {
    private final Activity activity;
    private View parentView;
    private PopupWindow popupWindow;
    private final OnBongSettingListener onBongSettingListener;
    private boolean isShow;
    public interface OnBongSettingListener {
        void onSystemUpdateClick();
        void onRebootDeviceClick();
        void onUnBindClick();
    }

    public BongSettingPopupWindow(Activity activity, OnBongSettingListener onBongSettingListener) {
        this.activity = activity;
        this.onBongSettingListener = onBongSettingListener;
    }

    public void show() {
        if (popupWindow == null) {
            setDefaultLayout();
        }
        if (popupWindow != null && !isShow) {
            popupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, 0, 0);
            changeAlpha(0.3F);
            isShow = true;
        }
    }

    private void setDefaultLayout() {
        parentView = activity.findViewById(android.R.id.content);
        View popupView = LayoutInflater.from(activity).inflate(R.layout.view_dialog_bong_setting, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.style.PopupWindowSelectAnimation);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupView.findViewById(R.id.llParent).setOnClickListener(this);
        popupView.findViewById(R.id.systemUpdateItemLayout).setOnClickListener(this);
        popupView.findViewById(R.id.rebootDeviceItemLayout).setOnClickListener(this);
        popupView.findViewById(R.id.unbindItemLayout).setOnClickListener(this);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismiss();
            }
        });
    }

    /**
     * 取消弹窗
     */
    private void dismiss() {
        if (popupWindow != null && isShow) {
            popupWindow.dismiss();
            popupWindow = null;
            changeAlpha(1F);
            isShow = false;
        }
    }

    /**
     * 改变activity透明度
     */
    private void changeAlpha(float alpha) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = alpha;
        activity.getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View view) {
        dismiss();
        if (view.getId() == R.id.systemUpdateItemLayout) {
            onBongSettingListener.onSystemUpdateClick();
        } else if (view.getId() == R.id.rebootDeviceItemLayout) {
            onBongSettingListener.onRebootDeviceClick();
        } else if (view.getId() == R.id.unbindItemLayout) {
            onBongSettingListener.onUnBindClick();
        }
    }
}
