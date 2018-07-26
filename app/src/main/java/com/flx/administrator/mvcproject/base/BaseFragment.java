package com.flx.administrator.mvcproject.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flx.administrator.mvcproject.R;
import com.flx.administrator.mvcproject.manager.TitleManager;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.io.Serializable;

/**
 * Created by Administrator.
 */

public abstract class BaseFragment extends RxFragment {

    private LinearLayout layout;
    private View rootView;
    private View bodyView;
    private TitleManager titleManager;
    //判断fragment的状态值
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    private TextView tv_progress;
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

    /**
     * 加载数据，刷新View
     */
    protected abstract void fetchData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDataInitiated =false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isViewInitiated = false;
        if(rootView == null){
            rootView = inflater.inflate(R.layout.frg_root,null);
            layout = (LinearLayout) rootView.findViewById(R.id.frg_root);
            tv_progress = (TextView) rootView.findViewById(R.id.tv_progress);
            //添加title
            TitleManager manager = new TitleManager(getActivity());
            titleManager = addTitle(manager);
            if(titleManager!=null && titleManager.getTitleView()!=null){
                layout.addView(titleManager.getTitleView(),new LinearLayout.
                        LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
            }
            //获取并添加bodyView
            bodyView = View.inflate(getActivity(), getContentId(), null);
//            hide();
            layout.addView(bodyView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            onLogicCreate(rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    public void startActivity(Context context, Class<?> cls, Object obj) {
        Intent intent = new Intent(context, cls);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivity(intent);
    }

    public void startActivityForResult(Context context,Class<?> cls, Object obj, int requestCode) {
        Intent intent = new Intent(context, cls);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivityForResult(intent, requestCode);
    }

    protected  <T extends View> T $(int id) {
        return (T) rootView.findViewById(id);
    }

    public void hide(){
        bodyView.setVisibility(View.GONE);
        tv_progress.setVisibility(View.VISIBLE);
    }
    public void show(){
        bodyView.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.GONE);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        //Fragment状态改变的时候加载数据
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }


    public void prepareFetchData() {
        if (  isViewInitiated && !isDataInitiated) {
            isDataInitiated = true;
            fetchData();
        }
    }
}
