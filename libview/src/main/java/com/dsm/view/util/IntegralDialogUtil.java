package com.dsm.view.util;

import android.content.Context;

import com.dsm.view.R;
import com.dsm.view.dialog.XiaodiMaskDialog;

/**
 * IntegralDialogUtil
 *
 * @author SJL
 * @date 2017/8/31
 */

public class IntegralDialogUtil {
    private static final String TAG = "IntegralDialogUtil";

    /**
     * 显示抽奖结果蒙版
     * @param context
     * @param isSuccess
     * @param onConfirmListener
     */
    public static XiaodiMaskDialog showIntegralDrawedMaskDialog(Context context,boolean isSuccess,XiaodiMaskDialog.OnConfirmListener onConfirmListener){
        if(isSuccess){
            //显示成功蒙版，暂时没有成功样式
            return showMaskDialog(context, R.mipmap.integral_draw_failure, 260, 270, context.getString(R.string.iKnow), true, onConfirmListener);
        }else {
            //显示失败蒙版
            return showMaskDialog(context, R.mipmap.integral_draw_failure, 260, 270, context.getString(R.string.iKnow), true, onConfirmListener);
        }
    }
    /**
     * 显示抽奖过程中蒙版
     * @param context
     */
    public static XiaodiMaskDialog showIntegralDrawingMaskDialog(Context context){
        return showMaskDialog(context, R.mipmap.integral_drawing, 260, 250, "", false, null);
    }
    /**
     * 显示积分说明蒙版
     * @param context
     * @param onConfirmListener
     */
    public static XiaodiMaskDialog showIntegralDescMaskDialog(Context context,XiaodiMaskDialog.OnConfirmListener onConfirmListener){
        return showMaskDialog(context, R.mipmap.integral_desc, 260, 300, context.getString(R.string.iKnow), true, onConfirmListener);
    }
    /**
     * 蒙版提示对话框
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
