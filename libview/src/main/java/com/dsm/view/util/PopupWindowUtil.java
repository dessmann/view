package com.dsm.view.util;

import android.content.Context;

import com.dsm.platform.listener.OnItemClickListener;
import com.dsm.view.BongSettingPopupWindow;
import com.dsm.view.popupwindow.SelectPopupWindow;

import java.util.List;

/**
 * PopupWindowUtil
 *
 * @author SJL
 * @date 2017/2/23
 */

public class PopupWindowUtil {
    private static final String TAG = "PopupWindowUtil";
    private static BongSettingPopupWindow bongSettingPopupWindow;

    public static SelectPopupWindow showSelectPopupWindow(Context context, List<String> list, OnItemClickListener onItemClickListener) {
        SelectPopupWindow selectPopupWindow = new SelectPopupWindow(context, list, onItemClickListener);
        selectPopupWindow.show();
        return selectPopupWindow;
    }
}
