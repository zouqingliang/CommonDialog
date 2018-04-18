package com.liang.commondialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/4/17 0017.
 */

public class BaseDialog extends Dialog {

    private Context mContext;

    private boolean canTouchOutside = false;

    private LinearLayout lyTitle;
    private TextView tvTitle;
    private TextView tvContent;
    private FrameLayout flContent;
    private LinearLayout lyBottom;
    private TextView tvCancel;
    private TextView tvConfirm;
    private TextView tvOk;

    private String mTitleTxt;
    private String mContentTxt;
    private String mCancelTxt;
    private String mConfirmTxt;
    private String mOkTxt;
    private View mView;

    private View.OnClickListener delaultClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };

    private View.OnClickListener cancelListener = delaultClickListener;
    private View.OnClickListener comfirmListener = delaultClickListener;
    private View.OnClickListener okListener = delaultClickListener;



    protected BaseDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    protected BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        setContentView(R.layout.dailog_default);

        WindowManager wm = ((Activity)mContext).getWindowManager();
        Display display = wm.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = display.getWidth() * 4/5;
        window.setAttributes(lp);

        lyTitle = findViewById(R.id.ly_title);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        flContent = findViewById(R.id.fl_content);
        lyBottom = findViewById(R.id.ly_bottom);
        tvCancel = findViewById(R.id.tv_cancel);
        tvConfirm = findViewById(R.id.tv_confirm);
        tvOk = findViewById(R.id.tv_ok);
    }

    @Override
    public void show() {
        super.show();
        show(this);
    }

    protected void show(BaseDialog dialog){

        tvOk.setVisibility(View.GONE);
        dialog.setCanceledOnTouchOutside(dialog.canTouchOutside);

        if (null != dialog.mView){
            lyTitle.setVisibility(View.GONE);
            tvContent.setVisibility(View.GONE);
            lyBottom.setVisibility(View.GONE);
            flContent.setVisibility(View.VISIBLE);
            flContent.addView(mView);
        } else {
            lyTitle.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);
            lyBottom.setVisibility(View.VISIBLE);
            flContent.setVisibility(View.GONE);
            tvContent.setText(dialog.mContentTxt);

            if (!TextUtils.isEmpty(dialog.mTitleTxt)){
                lyTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(dialog.mTitleTxt);
            } else {
                lyTitle.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(dialog.mContentTxt)){
                tvContent.setText(dialog.mContentTxt);
            }

            if (!TextUtils.isEmpty(dialog.mCancelTxt)){
                tvCancel.setText(dialog.mCancelTxt);
            }

            if (!TextUtils.isEmpty(dialog.mConfirmTxt)){
                tvConfirm.setText(dialog.mConfirmTxt);
            }

            if (!TextUtils.isEmpty(dialog.mOkTxt)){
                tvOk.setVisibility(View.VISIBLE);
                lyBottom.setVisibility(View.GONE);
                tvOk.setText(dialog.mOkTxt);
            }

            if (null != dialog.comfirmListener){
                tvConfirm.setOnClickListener(dialog.comfirmListener);
            }

            if (null != dialog.cancelListener){
                tvCancel.setOnClickListener(dialog.cancelListener);
            }

            if (null != dialog.okListener){
                tvOk.setOnClickListener(dialog.okListener);
            }

        }
    }

    public static class Builder{
        private Context context;
        private BaseDialog baseDialog;

        public Builder(Context context) {
            this.context = context;
            baseDialog = new BaseDialog(context,R.style.baseDialog);
        }

        public Builder setTitle(String title){
            baseDialog.mTitleTxt = title;
            return this;
        }

        public Builder setContent(String content){
            baseDialog.mContentTxt = content;
            return this;
        }

        public Builder setCancelTxt(String cancelTxt){
            baseDialog.mCancelTxt = cancelTxt;
            return this;
        }

        public Builder setConfirmTxt(String confirmTxt){
            baseDialog.mConfirmTxt = confirmTxt;
            return this;
        }

        public Builder setOkTxt(String okTxt){
            baseDialog.mOkTxt = okTxt;
            return this;
        }

        public Builder setView(View view){
            baseDialog.mView = view;
            return this;
        }

        public Builder setCanOutsideTouch(boolean canOutsideTouch){
            baseDialog.canTouchOutside = canOutsideTouch;
            return this;
        }

        public Builder setCancelClickListener(View.OnClickListener listener){
            baseDialog.cancelListener = listener;
            return this;
        }

        public Builder setConfirmClickListener(View.OnClickListener listener){
            baseDialog.comfirmListener = listener;
            return this;
        }

        public Builder setOkClickListener(View.OnClickListener listener){
            baseDialog.okListener = listener;
            return this;
        }

        public BaseDialog create(){
            return baseDialog;
        }

    }
}
