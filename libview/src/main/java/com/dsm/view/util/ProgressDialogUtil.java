package com.dsm.view.util;

import android.app.Activity;
import android.content.Context;

import com.dsm.platform.util.log.LogUtil;
import com.dsm.view.dialog.CustomProgressDialog;

/**
 * 加载进度弹窗工具类
 *
 * @author SJL
 * @date 2017/2/22
 */

public class ProgressDialogUtil {
    private static final String TAG = "ProgressDialogUtil";
    private static CustomProgressDialog customProgressDialog;

    /**
     * 显示提交进度
     *
     * @param context
     * @return
     */
    public static CustomProgressDialog showSubmitDialog(Context context) {
        return showProgressDialog(context, true);
    }

    /**
     * 显示加载进度
     *
     * @param context
     * @return
     */
    public static CustomProgressDialog showLoadDialog(Context context) {
        return showProgressDialog(context, false);
    }
    /**
     * 显示开锁弹窗
     *
     * @param context
     * @return
     */
    public static CustomProgressDialog showOpenDialog(Context context) {
        return showProgressDialog(context, false).showOpenProgress();
    }

    /**
     * 显示进度弹窗
     *
     * @param context
     * @param showBackground 是否显示灰色背景
     * @return
     */
    private static CustomProgressDialog showProgressDialog(Context context, boolean showBackground) {
        synchronized (TAG) {
            if (((Activity)context).isFinishing()) {//如果显示对话框的同时界面正在finish，应用程序会闪退
                LogUtil.e(TAG, "activity正在销毁，不显示对话框");
                return null;
            }
            LogUtil.i(TAG,"showProgressDialog");
//            if(customProgressDialog!=null&&customProgressDialog.isShowing()){
////                dismissProgressDialog();
//                customProgressDialog.dismiss();
//            }
            CustomProgressDialog customProgressDialog = new CustomProgressDialog(context);
            customProgressDialog.show();
            customProgressDialog.showBackground(showBackground);
            return customProgressDialog;
        }
    }

//    /**
//     * 取消加载进度
//     *
//     * @return
//     */
//    public static boolean dismissProgressDialog() {
//        LogUtil.i(TAG,"----dismissProgressDialog");
//        synchronized (TAG) {
//            if (customProgressDialog != null && customProgressDialog.isShowing()) {
//                customProgressDialog.dismiss();
//                LogUtil.i(TAG,"dismissProgressDialog");
//                customProgressDialog=null;
//                return true;
//            }
//            return false;
//        }
//    }
    /**
     * 取消加载进度
     *
     * @return
     */
    public static boolean dismissProgressDialog(Activity activity, CustomProgressDialog customProgressDialog) {
        synchronized (TAG) {
            if (customProgressDialog != null && customProgressDialog.isShowing()) {
                if (activity.isFinishing()) {//如果关闭对话框的同时执行dismiss操作，应用程序会闪退
                    LogUtil.e(TAG, "activity正在销毁，取消dismiss对话框");
                    return false;
                }
                customProgressDialog.dismiss();
                LogUtil.i(TAG,"dismissProgressDialog");
                return true;
            }
            return false;
        }
    }

}
