package com.dsm.view.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.dsm.platform.util.log.LogUtil;
import com.dsm.view.dialog.ActivityDialog;
import com.dsm.view.dialog.CustomDialog;
import com.dsm.view.dialog.NetWorkErrorDialog;

/**
 * 普通弹窗工具类
 *
 * @author SJL
 * @date 2017/2/21
 */

public class DialogUtil {
    private static final String TAG = "DialogUtil";

    /**
     * 显示弹窗
     */
    public static CustomDialog showAlert(Context context, CharSequence titleText, CharSequence contentText, CharSequence confirmText, int confirmColor, int animateStyle, CustomDialog.DialogListener dialogListener) {
        return showDialog(context, titleText, null, contentText, null, null, confirmText, -1, -1, -1, confirmColor, animateStyle, dialogListener, false);
    }

    /**
     * 显示类似于通知一样的弹窗
     */
    public static CustomDialog showAlertMultiLines(Context context, CharSequence titleText, CharSequence contentTopLeftText, CharSequence contentCenter, CharSequence contentButtomRightText, CharSequence confirmText, int confirmColor, int animateStyle, CustomDialog.DialogListener dialogListener) {
        return showDialog(context, titleText, contentTopLeftText, contentCenter, contentButtomRightText, null, confirmText, -1, -1, -1, confirmColor, animateStyle, dialogListener, false);
    }

    /**
     * 显示类似于通知一样的弹窗
     */
    public static CustomDialog showConfirmMultiLines(Context context, CharSequence titleText, CharSequence contentTopLeftText, CharSequence contentCenter, CharSequence contentButtomRightText, CharSequence cancelText, CharSequence confirmText, int confirmColor, int animateStyle, CustomDialog.DialogListener dialogListener) {
        return showDialog(context, titleText, contentTopLeftText, contentCenter, contentButtomRightText, cancelText, confirmText, -1, -1, -1, confirmColor, animateStyle, dialogListener, true);
    }

    /**
     * 显示有确认取消按钮的弹窗
     */
    public static CustomDialog showConfirm(Context context, CharSequence titleText, CharSequence contentText, CharSequence cancelText, CharSequence confirmText, int confirmColor, int animateStyle, CustomDialog.DialogListener dialogListener) {
        return showDialog(context, titleText, null, contentText, null, cancelText, confirmText, -1, -1, -1, confirmColor, animateStyle, dialogListener, true);
    }

    /**
     * 显示有确认取消按钮的弹窗
     */
    public static CustomDialog showConfirm(Context context, CharSequence contentText, CharSequence cancelText, CharSequence confirmText, CustomDialog.DialogListener dialogListener) {
        return showConfirm(context, null, contentText, cancelText, confirmText, -1, -1, dialogListener);
    }

    /**
     * 显示对话框
     */
    private static CustomDialog showDialog(Context context, CharSequence titleText, CharSequence contentLeftText, CharSequence contentCenterText, CharSequence contentRightText, CharSequence cancelText, CharSequence confirmText, int titleColor, int contentColor, int cancelColor, int confirmColor, int animateStyle, CustomDialog.DialogListener dialogListener, boolean showCancel) {
        synchronized (TAG) {
            CustomDialog customDialog = new CustomDialog(context);
            customDialog.setTitleText(titleText)//标题文字
                    .setTitleColor(titleColor)//标题颜色
                    .setContentLeftTopText(contentLeftText)//内容左上角文字
                    .setContentCenterText(contentCenterText)//内容中间文字
                    .setContentRightButtomText(contentRightText)//内容右下角文字
                    .setContentColor(contentColor)//内容文字颜色
                    .setCancelText(cancelText)//取消文字
                    .setCancelColor(cancelColor)//取消文字颜色
                    .setConfirmText(confirmText)//确定文字
                    .setConfirmColor(confirmColor)//确定文字颜色
                    .setWindowAnimate(animateStyle)//动画效果
                    .setShowCancel(showCancel)//是否显示取消按钮
                    .setDialogListener(dialogListener)
                    .show();
            return customDialog;
        }
    }

    /**
     * 显示网络报错页面
     */
    public static void showNetWorkErrorDialog(Context context, NetWorkErrorDialog.DialogListener dialogListener) {
        NetWorkErrorDialog netWorkErrorDialog = new NetWorkErrorDialog(context, dialogListener);
        try {
            netWorkErrorDialog.show();
        } catch (Exception e) {
            LogUtil.i(TAG, "showNetWorkErrorDialog:" + e.getMessage());
        }
    }

    /**
     * 显示活动广告
     */
    public static ActivityDialog showActivityDialog(Context context, Bitmap bitmap, ActivityDialog.DialogListener dialogListener) {
        ActivityDialog activityDialog;
        synchronized (TAG) {
            activityDialog = new ActivityDialog(context, dialogListener, true);
            activityDialog.show();
            activityDialog.setImage(bitmap);
            return activityDialog;
        }
    }

}
