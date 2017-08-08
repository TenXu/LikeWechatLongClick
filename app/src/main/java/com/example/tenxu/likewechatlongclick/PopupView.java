package com.example.tenxu.likewechatlongclick;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by TenXu on 2017/7/28.
 */

public class PopupView {
    private final Context mContext;
    private PopupWindow mPopup;
    private WindowManager wm;
    private int screenWidth;
    private int screenHeight;
    private int mHeight;

    public PopupView(Context context) {
        mContext = context;
    }

    /**
     * reset the popupwindow
     */
    public void reset() {
        mPopup.dismiss();
        mPopup = null;
    }

    public void setScreen() {
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        //获取屏幕尺寸
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    /**
     * init the popupview
     *
     * @param view the view add to popup view
     */
    public void initView(View view, int width, int height) {
        // TODO Auto-generated method stub
        if (width == 0 || height == 0) {
            setScreen();
//            width = screenWidth / 2;
            if (width == 0) {
                width = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            if (height == 0) {
                height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
        }
        mHeight = height;
        mPopup = new PopupWindow(view, width, height);
        //设置触摸外面时消失
        mPopup.setOutsideTouchable(true);
        //设置系统动画
        mPopup.setAnimationStyle(android.R.style.Animation_Dialog);
        mPopup.setTouchable(true);
        mPopup.setBackgroundDrawable(new BitmapDrawable());
        mPopup.update();
        //设置了可获得焦点才能让Popu盘Window中的控件获取相应
        mPopup.setFocusable(true);
    }

    /**
     * show popup view
     *
     * @param anchor  a parent view to get the getWindowToken() token from
     * @param offsetX x坐标
     * @param offsetY y坐标
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showView(View anchor, int tatol, int current, int parHeight, int offsetX, int offsetY) {
        // TODO Auto-generated method stub
        if (tatol >= 10) {
            if (tatol - current <= 2 && offsetY > parHeight / 2) {
                mPopup.showAsDropDown(anchor, offsetX,
                        -(parHeight - offsetY + mHeight),
                        Gravity.CENTER);
            } else {
                mPopup.showAsDropDown(anchor, offsetX, offsetY - parHeight, Gravity.CENTER);
            }
        } else {
            mPopup.showAsDropDown(anchor, offsetX, offsetY - parHeight, Gravity.CENTER);
        }
    }

    /**
     * hide the popup view
     */
    public void hideView() {
        if (mPopup.isShowing())
            mPopup.dismiss();
    }

    /**
     * @param anchor
     * @param curViewY
     * @return if curViewY>=maxHeight return true;
     */
    public boolean needReverse(View anchor, int curViewY) {
        int maxHeight = mPopup.getMaxAvailableHeight(anchor);
        return curViewY >= maxHeight;
    }
}