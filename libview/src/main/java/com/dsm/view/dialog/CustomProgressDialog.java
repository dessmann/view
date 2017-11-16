package com.dsm.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.dsm.view.R;

/**
 * CustomProgressDialog
 *
 * @author SJL
 * @date 2017/2/22
 */

public class CustomProgressDialog extends Dialog {
    private static final String TAG = "CustomProgressDialog";
    private View view;
    private boolean cancelableOnTouchOutside;

    public CustomProgressDialog(Context context) {
        this(context, 0);
    }

    private CustomProgressDialog(Context context, int themeResId) {
        super(context, R.style.CustomProgressDialog);
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
        WindowManager.LayoutParams windowWM = window.getAttributes();
        window.setAttributes(windowWM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        super.show();
    }

    /**
     * 设置布局
     */
    private void setDefaultLayout() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.view_dialog_custom_progress, null);
    }

    public CustomProgressDialog showBackground(boolean isShow) {
        view.findViewById(R.id.rlDataProgress).setVisibility(View.VISIBLE);
        view.findViewById(R.id.rlOpenProgress).setVisibility(View.GONE);
        view.findViewById(R.id.llSubmit).setVisibility(isShow?View.VISIBLE:View.GONE);
        view.findViewById(R.id.progressLoad).setVisibility(isShow?View.GONE:View.VISIBLE);
        return this;
    }

    public CustomProgressDialog showOpenProgress(){
        view.findViewById(R.id.rlDataProgress).setVisibility(View.GONE);
        view.findViewById(R.id.rlOpenProgress).setVisibility(View.VISIBLE);
        return this;
    }

    public CustomProgressDialog setCancelableOnTouchOutside(boolean cancelableOnTouchOutside) {
        this.cancelableOnTouchOutside = cancelableOnTouchOutside;
        setCanceledOnTouchOutside(cancelableOnTouchOutside);
        return this;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (cancelableOnTouchOutside) {
                dismiss();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
