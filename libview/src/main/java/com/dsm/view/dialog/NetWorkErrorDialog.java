package com.dsm.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dsm.view.R;

public class NetWorkErrorDialog extends Dialog {

    private final Context mContext;
    private View view;

    public interface DialogListener {
        void onRefresh();
        void onBackHome();
    }

    private DialogListener dialogListener;

    private NetWorkErrorDialog(Context context) {
        super(context, R.style.NetWorkErrorDialog);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
        initView();
    }

    public NetWorkErrorDialog(Context context, DialogListener dialogListener) {
        this(context);
        this.dialogListener = dialogListener;

    }

    private void initView() {
        if (view == null) {
            setDefalutLayout();
        }
        setContentView(view);
        Window window = this.getWindow();
        WindowManager.LayoutParams windowWM = window.getAttributes();
        window.setAttributes(windowWM);
        if (Build.VERSION.SDK_INT >= 19) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        findView();
    }

    /*@Override
    public void show() {
        if (view == null) {
            setDefalutLayout();
        }
        setContentView(view);
        Window window = this.getWindow();
        WindowManager.LayoutParams windowWM = window.getAttributes();
        window.setAttributes(windowWM);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        findView();
        super.show();
    }*/

    private void setDefalutLayout() {
        view = LayoutInflater.from(mContext).inflate(R.layout.view_dialog_network_error, null);
    }

    private void findView() {
        if (view == null) {
            setDefalutLayout();
        }

        ImageView ivRefresh = (ImageView) view.findViewById(R.id.network_refresh_iv);
        ImageView ivBack = (ImageView) view.findViewById(R.id.network_back_home_iv);
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null) {
                    dialogListener.onRefresh();
                }
                NetWorkErrorDialog.this.dismiss();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null) {
                    dialogListener.onBackHome();
                }
                NetWorkErrorDialog.this.dismiss();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN
            || super.onKeyDown(keyCode, event);
    }
}
