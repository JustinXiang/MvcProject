package com.flx.administrator.mvcproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flx.administrator.mvcproject.R;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;

/**
 * Created by Administrator.
 */

public abstract class BaseDialog extends RxDialogFragment {

    private View view;

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

    /**
     * 加载数据，刷新View
     */
    protected abstract void fetchData();
    public BaseDialog() {
        setStyle(STYLE_NO_FRAME, R.style.BaseDialog);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getContentId(),null);
        onLogicCreate(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchData();
    }

    protected  <T extends View> T $(int id) {
        return (T) view.findViewById(id);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != getView()) {
            getView().setFitsSystemWindows(true);
            ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        }
    }

}
