package com.dsm.view.util;

import android.content.Context;

import com.dsm.platform.base.DeviceLockBaseUtil;
import com.dsm.platform.listener.OnPermissionResult;
import com.dsm.platform.util.MusicUtil;
import com.dsm.platform.util.PermisstionUtil;
import com.dsm.platform.util.ToastUtil;
import com.dsm.platform.util.log.LogUtil;
import com.dsm.view.R;
import com.dsm.view.dialog.XiaodiMaskDialog;

/**
 * BongMaskDialogUtil
 *
 * @author SJL
 * @date 2017/7/5
 */

public class LockMaskDialogUtil {
    private static final String TAG = "LockMaskDialogUtil";

    /**
     * 根据设备型号显示蒙版提示对话框
     */
    public static boolean showMaskGuideDialog(final Context context, boolean deviceConnectState,  final String deviceType, final String meterType, final Boolean fingerInputMode, final XiaodiMaskDialog.OnConfirmListener onConfirmListener) {
        LogUtil.i(TAG, "showMaskDialogByMeterType,meterType=" + meterType);
        if (context == null) {
            LogUtil.e(TAG, "context=null");
            return false;
        }
        if (deviceConnectState) {
            showMaskGuide(deviceType, meterType, fingerInputMode, context, onConfirmListener);
            return true;
        }
        PermisstionUtil.requestBLELocationPermission(context, context.getString(R.string.need_location_permission), new OnPermissionResult() {
            @Override
            public void granted(int requestCode) {
                showMaskGuide(deviceType, meterType, fingerInputMode, context, onConfirmListener);
            }

            @Override
            public void denied(int requestCode) {
                ToastUtil.showToastLong(context.getString(R.string.need_location_permission));
            }
        });
        return true;
    }

    private static void showMaskGuide(String deviceType, String meterType, Boolean fingerInputMode, Context context, XiaodiMaskDialog.OnConfirmListener onConfirmListener) {
        int maskGuide = DeviceLockBaseUtil.getMaskGuide(deviceType, meterType);
        if (maskGuide == 1) {
            //T700(默认)
            if (fingerInputMode) {
                showMaskDialog(context, R.mipmap.mask_lock_700_with_finger, 260, 360, context.getString(R.string.iKnow), true, getMaskOnConfirmListener(onConfirmListener));
            } else {
                showMaskDialog(context, R.mipmap.mask_lock_700, 260, 300, context.getString(R.string.iKnow), true, getMaskOnConfirmListener(onConfirmListener));
            }
        } else if (maskGuide == 2) {
            //820
            if (fingerInputMode) {
                showMaskDialog(context, R.mipmap.mask_lock_820_with_finger, 260, 360, context.getString(R.string.iKnow), true, getMaskOnConfirmListener(onConfirmListener));
            } else {
                showMaskDialog(context, R.mipmap.mask_lock_820, 260, 300, context.getString(R.string.iKnow), true, getMaskOnConfirmListener(onConfirmListener));
            }
        } else if (maskGuide == 3) {
            //510
            if (fingerInputMode) {
                showMaskDialog(context, R.mipmap.mask_lock_510_with_finger, 260, 360, context.getString(R.string.iKnow), true, getMaskOnConfirmListener(onConfirmListener));
            } else {
                showMaskDialog(context, R.mipmap.mask_lock_510, 260, 300, context.getString(R.string.iKnow), true, getMaskOnConfirmListener(onConfirmListener));
            }
        } else if (maskGuide == 4) {
            //保险箱
            if (fingerInputMode) {
                showMaskDialog(context, R.mipmap.mask_safe_finger, 260, 360, context.getString(R.string.iKnow), true, getMaskOnConfirmListener(onConfirmListener));
            } else {
                showMaskDialog(context, R.mipmap.mask_safe, 260, 300, context.getString(R.string.iKnow), true, getMaskOnConfirmListener(onConfirmListener));
            }
        } else {
            //T700(默认)
            if (fingerInputMode) {
                showMaskDialog(context, R.mipmap.mask_lock_700_with_finger, 260, 360, context.getString(R.string.iKnow), true, getMaskOnConfirmListener(onConfirmListener));
            } else {
                showMaskDialog(context, R.mipmap.mask_lock_700, 260, 300, context.getString(R.string.iKnow), true, getMaskOnConfirmListener(onConfirmListener));
            }
        }
        MusicUtil.playMusic(context, "android.resource://" + context.getPackageName() + "/raw/lock_config_sound");
    }

    /**
     * 蒙版点击确认回调通用事件
     */
    private static XiaodiMaskDialog.OnConfirmListener getMaskOnConfirmListener(final XiaodiMaskDialog.OnConfirmListener onConfirmListener) {
        return new XiaodiMaskDialog.OnConfirmListener() {
            @Override
            public void onCofirm() {
                MusicUtil.stopMusic();
                onConfirmListener.onCofirm();
            }
        };
    }

    /**
     * 显示录指纹或设备硬件配置的提示对话框
     */
    private static XiaodiMaskDialog showMaskDialog(Context context, int maskImgResId, int maskRelativeLayoutWidth, int maskRelativeLayoutHeight, String maskBtnString, boolean cancelableOnTouchOutside, XiaodiMaskDialog.OnConfirmListener onConfirmListener) {
        XiaodiMaskDialog xiaodiMaskDialog;
        synchronized (TAG) {
            xiaodiMaskDialog = new XiaodiMaskDialog(context);
            xiaodiMaskDialog.setMaskImgResId(maskImgResId)
                    .setMaskRelativeLayoutWidth(maskRelativeLayoutWidth)
                    .setMaskRelativeLayoutHeight(maskRelativeLayoutHeight)
                    .setMaskBtnString(maskBtnString)
                    .setCancelableOnTouchOutside(cancelableOnTouchOutside)
                    .setOnConfirmListener(onConfirmListener);
            xiaodiMaskDialog.show();
        }
        return xiaodiMaskDialog;
    }
}
