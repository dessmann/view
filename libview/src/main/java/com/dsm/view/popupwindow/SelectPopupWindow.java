package com.dsm.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dsm.platform.listener.OnItemClickListener;
import com.dsm.platform.util.SystemUtil;
import com.dsm.view.R;
import com.dsm.view.popupwindow.adapter.TextSelectAdapter;

import java.util.List;

/**
 * SelectPopupWindow
 *
 * @author SJL
 * @date 2017/2/23
 */

@SuppressWarnings("ALL")
public class SelectPopupWindow implements View.OnClickListener {
    private final Context context;
    private View parentView;
    private PopupWindow popupWindow;
    private final List<String> list;
    private TextSelectAdapter textSelectAdapter;
    private boolean isShow;
    private final OnItemClickListener onItemClickListener;

    public SelectPopupWindow(Context context, List<String> list, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 显示底部弹窗
     */
    public void show() {
        if (popupWindow == null) {
            setDefaultLayout();
        }
        textSelectAdapter.notifyDataSetChanged();
        if (popupWindow != null && !isShow) {
            popupWindow.showAtLocation(parentView, Gravity.BOTTOM, (int) parentView.getX(), (int) (parentView.getY()));
            SystemUtil.changeAlpha(context,0.3f);
            isShow = true;
        }
    }

    private void setDefaultLayout() {
        parentView = ((Activity) context).findViewById(android.R.id.content);
        View popupView = LayoutInflater.from(context).inflate(R.layout.view_popup_window_select, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.style.PopupWindowSelectAnimation);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismiss();
            }
        });
        LinearLayout llParent = (LinearLayout) popupView.findViewById(R.id.llParent);
        RecyclerView rv = (RecyclerView) popupView.findViewById(R.id.rv);
        TextView tvCancel = (TextView) popupView.findViewById(R.id.tvCancel);
        textSelectAdapter = new TextSelectAdapter(context,list);
        rv.setAdapter(textSelectAdapter);
        rv.setLayoutManager(new LinearLayoutManager(context));

        llParent.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        textSelectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onItemClickListener.onItemClick(view,position);
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
            SystemUtil.changeAlpha(context,1f);
            isShow = false;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.llParent){
            dismiss();
        }else if(id==R.id.tvCancel){
            dismiss();
        }
    }
}
