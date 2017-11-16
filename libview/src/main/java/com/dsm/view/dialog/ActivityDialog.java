package com.dsm.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dsm.view.R;

/**
 * 活动广告弹窗
 *
 * @author SJL
 * @date 2017/3/21
 */

public class ActivityDialog extends Dialog {
    private final Context mContext;
    private final Boolean cancelableOnTouchOutside;
    private View view;

    private ImageView ivContent;
    private LinearLayout llCancel;

    private DialogListener dialogListener;

    public interface DialogListener {
        void onConfirm(ActivityDialog activityDialog);

        void onCancel(ActivityDialog activityDialog);
    }

    public ActivityDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.cancelableOnTouchOutside = false;
        setCanceledOnTouchOutside(false);
    }

    public ActivityDialog(Context context, DialogListener dialogListener) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.dialogListener = dialogListener;
        this.cancelableOnTouchOutside = false;
        setCanceledOnTouchOutside(false);
    }

    public ActivityDialog(Context context, DialogListener dialogListener, Boolean cancelableOnTouchOutside) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.dialogListener = dialogListener;
        this.cancelableOnTouchOutside = cancelableOnTouchOutside;
        setCanceledOnTouchOutside(cancelableOnTouchOutside);
    }

    @Override
    public void show() {
        try {
            if (view == null) {
                setDefaultLayout();
            }
            setContentView(view);
            Window window = this.getWindow();
            WindowManager.LayoutParams windowWM = window.getAttributes();
            window.setAttributes(windowWM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            findView();

            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDefaultLayout() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.view_dialog_activity, null);
    }

    private void findView() {
        ivContent = (ImageView) view.findViewById(R.id.ivContent);
        llCancel = (LinearLayout) view.findViewById(R.id.llCancel);

        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null) {
                    dialogListener.onCancel(ActivityDialog.this);
                }
                dismiss();
            }
        });

        ivContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null) {
                    dialogListener.onConfirm(ActivityDialog.this);
                }
                dismiss();
            }
        });
    }

    public void setImage(Bitmap bitmap) {
        ivContent.setImageBitmap(bitmap);
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (cancelableOnTouchOutside) {
                llCancel.performClick();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
