package com.flx.administrator.mvcproject.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.flx.administrator.mvcproject.R;
import com.flx.administrator.mvcproject.manager.AppManager;
import com.flx.administrator.mvcproject.manager.TitleManager;
import com.flx.administrator.mvcproject.utils.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.Serializable;

/**
 * Created by Administrator.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    private LinearLayout layout;
    private View bodyView;
    private TitleManager titleManager;
    /**
     * 是否需要关闭app
     */
    protected boolean mNeedFinishApp = false;
    /**
     * 上次点击<返回>键的时间
     */
    protected long lastEventTime;
    /**
     * 允许两次点击<返回>键的时间差
     */
    protected static int TIME_TO_WAIT = 3 * 1000;

    /**
     * 返回一个Title的View
     *
     * @return
     */
    protected abstract TitleManager addTitle(TitleManager manager);

    /**
     * 返回一个layout的id
     *
     * @return
     */
    protected abstract int getContentId();

    /**
     * 执行逻辑。
     */
    protected abstract void onLogicCreate(View view);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        View view = View.inflate(this,R.layout.act_root,null);
        layout = (LinearLayout) view.findViewById(R.id.act_root);
        //添加title
        TitleManager manager = new TitleManager(this);
        titleManager = addTitle(manager);
        if(titleManager!=null && titleManager.getTitleView()!=null){
            layout.addView(titleManager.getTitleView(),new LinearLayout.
                    LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        //加载添加的View
        bodyView = View.inflate(this, getContentId(), null);
        layout.addView(bodyView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        setContentView(layout);
        onLogicCreate(layout);
    }

    public void startActivity(Class<?> cls, Object obj) {
        Intent intent = new Intent(this, cls);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, Object obj, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivityForResult(intent, requestCode);
    }

    protected <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

    protected <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onBackPressed() {
        //instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例
        if (mNeedFinishApp) {
            long currentEventTime = System.currentTimeMillis();
            if ((currentEventTime - lastEventTime) > TIME_TO_WAIT) {// 提示再按一次退出应用
                ToastUtils.show(this, "请再按一次Back键退出", TIME_TO_WAIT);
                lastEventTime = currentEventTime;
            } else {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());// 关闭软件所有线程
                Runtime.getRuntime().gc();// 通知Java虚拟机回收垃圾
            }
        } else {
            super.onBackPressed();
            finish();
        }
    }
}
