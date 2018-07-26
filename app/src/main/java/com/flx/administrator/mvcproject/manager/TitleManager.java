package com.flx.administrator.mvcproject.manager;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flx.administrator.mvcproject.R;
import com.flx.administrator.mvcproject.utils.PxUtil;


/**
 * Created by Administrator.
 * 头管理器
 */

public class TitleManager {
    Activity c;
    private RelativeLayout toptitle_left;
    private RelativeLayout toptitle_right;
    private RelativeLayout toptitle_center;
    private View titleView;

    public TitleManager(Activity c) {
        super();
        this.c = c;

        titleView = View.inflate(c, R.layout.top_title, null);
        toptitle_left = (RelativeLayout) titleView
                .findViewById(R.id.toptitle_left);
        toptitle_right = (RelativeLayout) titleView
                .findViewById(R.id.toptitle_right);
        toptitle_center = (RelativeLayout) titleView
                .findViewById(R.id.toptitle_center);
        titleView.getBackground().setAlpha(254);
    }
    /**
     * 默认title的处理
     */

    public void setHeadName(String name) {
        // common_view_head
        ImageView leftView = getLeftView(ImageView.class);
        leftView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                c.finish();


            }
        });
        TextView centerView = getCenterView(TextView.class);
        centerView.setText(name);
        centerView.setMaxEms(10);
        centerView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
    }
    public void setHeadOnlyName(String name) {

        TextView centerView = getCenterView(TextView.class);
        centerView.setText(name);
        centerView.setMaxEms(10);
        centerView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
    }
    /**
     * 默认title的处理，,onbackclick 如果为null则默认finish这个activity
     */

    public void setHeadNameAndBack(String name, final OnBackClick bc) {
        // common_view_head
        ImageView leftView = getLeftView(ImageView.class);
        leftView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (bc == null) {
                    c.finish();
                } else {
                    bc.onClick();
                }

            }
        });
        TextView centerView = getCenterView(TextView.class);
        centerView.setText(name);
    }

    public View getTitleView() {
        return titleView;

    }

    /**
     * 获取leftview
     */

    public RelativeLayout getLeftLayout() {

        return toptitle_left;

    }

    /**
     * 获取Rightview
     */

    public RelativeLayout getRightLayout() {

        return toptitle_right;

    }


    /**
     * 获取Layout
     */

    public RelativeLayout getCenterLayout() {

        return toptitle_center;

    }

    /**
     * 通过此方法获取textview imageview imagebutton button
     * ，别的控件不可使用，如果需要别的控件，需要调用getLeftView() 方法,手动add进去view
     *
     * @param t
     * @return
     */
    public <T> T getLeftView(Class<T> t) {

        if (t == TextView.class) {
            toptitle_left.removeAllViews();
            TextView textView = new TextView(c);
            textView.setTextSize(18);
//            textView.setTextColor(0xffffffff);
            textView.setTextColor(c.getResources().getColor(R.color.color_080808));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8));
            textView.setLayoutParams(layoutParams);
            textView.setPadding(PxUtil.convertDIP2PX(c, 10),
                    PxUtil.convertDIP2PX(c, 10), PxUtil.convertDIP2PX(c, 10),
                    PxUtil.convertDIP2PX(c, 10));
            textView.setGravity(Gravity.CENTER);
            toptitle_left.addView(textView);
            return (T) textView;
        } else if (t == ImageView.class) {
            toptitle_left.removeAllViews();
            ImageView imageView = new ImageView(c);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8));

            imageView.setLayoutParams(layoutParams);

            imageView.setBackgroundResource(R.mipmap.icon_back);

            toptitle_left.addView(imageView);
            return (T) imageView;
        } else if (t == ImageButton.class) {
            toptitle_left.removeAllViews();

            ImageButton imageButton = new ImageButton(c);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8));

            imageButton.setLayoutParams(layoutParams);
            imageButton.setPadding(PxUtil.convertDIP2PX(c, 10),
                    PxUtil.convertDIP2PX(c, 10), PxUtil.convertDIP2PX(c, 10),
                    PxUtil.convertDIP2PX(c, 10));
            toptitle_left.addView(imageButton);
            return (T) imageButton;
        } else if (t == Button.class) {
            toptitle_left.removeAllViews();
            Button button = new Button(c);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8));

            button.setLayoutParams(layoutParams);
            button.setGravity(Gravity.CENTER);
            toptitle_left.addView(button);
            return (T) button;
        } else {
            return null;
        }

    }

    public <T> T getRightView(Class<T> t) {

        if (t == TextView.class) {
            toptitle_right.removeAllViews();
            TextView textView = new TextView(c);
			textView.setTextSize(10);
            textView.setTextColor(c.getResources().getColor(R.color.color_080808));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8));

            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(PxUtil.convertDIP2PX(c, 0),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 0),
                    PxUtil.convertDIP2PX(c, 8));
            toptitle_right.addView(textView);
            return (T) textView;
        } else if (t == ImageView.class) {
            toptitle_right.removeAllViews();
            ImageView imageView = new ImageView(c);
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8));

            imageView.setLayoutParams(layoutParams);
            imageView.setPadding(PxUtil.convertDIP2PX(c, 10),
                    PxUtil.convertDIP2PX(c, 10), PxUtil.convertDIP2PX(c, 10),
                    PxUtil.convertDIP2PX(c, 10));
            toptitle_right.addView(imageView);
            return (T) imageView;
        } else if (t == ImageButton.class) {
            toptitle_right.removeAllViews();
            ImageButton imageButton = new ImageButton(c);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8));

            imageButton.setLayoutParams(layoutParams);
            imageButton.setPadding(PxUtil.convertDIP2PX(c, 10),
                    PxUtil.convertDIP2PX(c, 10), PxUtil.convertDIP2PX(c, 10),
                    PxUtil.convertDIP2PX(c, 10));
            toptitle_right.addView(imageButton);
            return (T) imageButton;
        } else if (t == Button.class) {
            toptitle_right.removeAllViews();
            Button button = new Button(c);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8));

            button.setLayoutParams(layoutParams);
            button.setGravity(Gravity.CENTER);
            toptitle_right.addView(button);
            return (T) button;
        } else {
            return null;
        }

    }

    public <T> T getCenterView(Class<T> t) {

        if (t == TextView.class) {
            toptitle_center.removeAllViews();
            TextView textView = new TextView(c);
            textView.setTextSize(18);
//            textView.setTextColor(0xffffffff);
            textView.setTextColor(c.getResources().getColor(R.color.color_080808));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8));

            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            toptitle_center.addView(textView);
            return (T) textView;
        } else if (t == ImageView.class) {
            toptitle_center.removeAllViews();
            ImageView imageView = new ImageView(c);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8));

            imageView.setLayoutParams(layoutParams);
            toptitle_center.addView(imageView);
            return (T) imageView;
        } else if (t == EditText.class) {
            toptitle_center.removeAllViews();
            EditText edit = new EditText(c);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8), PxUtil.convertDIP2PX(c, 8),
                    PxUtil.convertDIP2PX(c, 8));

            edit.setLayoutParams(layoutParams);
            edit.setPadding(PxUtil.convertDIP2PX(c, 10),
                    PxUtil.convertDIP2PX(c, 10), PxUtil.convertDIP2PX(c, 10),
                    PxUtil.convertDIP2PX(c, 10));

            toptitle_center.addView(edit);
            return (T) edit;
        } else {
            return null;
        }

    }
    public RelativeLayout.LayoutParams getRelativeParams(){
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        return layoutParams;

    }
    public interface OnBackClick {
        void onClick();
    }
}
