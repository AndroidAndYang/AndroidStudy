package com.seabig.moduledemo.common.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.seabig.moduledemo.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YJZ
 *         date: 2017/12/15
 *         description：IOS底部对话框
 */

public class BottomMenuDialog extends Dialog {

    private BottomMenuDialog(Context context, int themeResId)
    {
        super(context, themeResId);
    }

    private static class Params {
        private final List<BottomMenu> menuList = new ArrayList<>();
        private View.OnClickListener cancelListener;
        private String menuTitle;
        private String cancelText;
        private Context context;
    }

    public static class Builder {

        private boolean canCancel = true;
        private boolean shadow = true;
        private final Params params;

        public Builder(Context context)
        {
            params = new Params();
            params.context = context;
        }

        public Builder setCanCancel(boolean canCancel)
        {
            this.canCancel = canCancel;
            return this;
        }

        public Builder setShadow(boolean shadow)
        {
            this.shadow = shadow;
            return this;
        }

        public Builder setTitle(String title)
        {
            this.params.menuTitle = title;
            return this;
        }

        public Builder addMenu(String text, View.OnClickListener listener)
        {
            BottomMenu bm = new BottomMenu(text, listener);
            this.params.menuList.add(bm);
            return this;
        }

        public Builder addMenu(int textId, View.OnClickListener listener)
        {
            return addMenu(params.context.getString(textId), listener);
        }

        public Builder setCancelListener(View.OnClickListener cancelListener)
        {
            params.cancelListener = cancelListener;
            return this;
        }

        public Builder setCancelText(int resId)
        {
            params.cancelText = params.context.getString(resId);
            return this;
        }

        public Builder setCancelText(String text)
        {
            params.cancelText = text;
            return this;
        }

        public BottomMenuDialog create()
        {
            final BottomMenuDialog dialog = new BottomMenuDialog(params.context, shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.Animation_Bottom_Rising);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            window.setGravity(Gravity.BOTTOM);

            View view = LayoutInflater.from(params.context).inflate(R.layout.common_dialog_bottom_menu, null);

            TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
            ViewGroup layContainer = (ViewGroup) view.findViewById(R.id.lay_container);
            ViewGroup.LayoutParams lpItem = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.LayoutParams lpDivider = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            int dip1 = (int) (1 * params.context.getResources().getDisplayMetrics().density + 0.5f);
            int spacing = dip1 * 12;
            btnCancel.setPadding(0, spacing, 0, spacing);
            boolean hasTitle = !TextUtils.isEmpty(params.menuTitle);
            if (hasTitle)
            {
                TextView tTitle = new TextView(params.context);
                tTitle.setLayoutParams(lpItem);
                tTitle.setGravity(Gravity.CENTER);
                tTitle.setTextColor(0xFF8F8F8F);
                tTitle.setText(params.menuTitle);
                tTitle.setTextSize(17);
                tTitle.setPadding(0, spacing, 0, spacing);
                tTitle.setBackgroundResource(R.drawable.common_dialog_selection_selector_top);
                layContainer.addView(tTitle);

                View viewDivider = new View(params.context);
                viewDivider.setLayoutParams(lpDivider);
                viewDivider.setBackgroundColor(0xFFCED2D6);
                layContainer.addView(viewDivider);
            }

            for (int i = 0; i < params.menuList.size(); i++)
            {
                BottomMenu bottomMenu = params.menuList.get(i);
                TextView bbm = new TextView(params.context);
                bbm.setLayoutParams(lpItem);
                int backgroundResId = R.drawable.common_dialog_selection_selector_center;
                if (params.menuList.size() > 1)
                {
                    if (i == 0)
                    {
                        if (hasTitle)
                        {
                            backgroundResId = R.drawable.common_dialog_selection_selector_center;
                        } else
                        {
                            backgroundResId = R.drawable.common_dialog_selection_selector_top;
                        }
                    } else if (i == params.menuList.size() - 1)
                    {
                        backgroundResId = R.drawable.common_dialog_selection_selector_bottom;
                    }
                } else if (params.menuList.size() == 1)
                {
                    backgroundResId = R.drawable.common_dialog_selection_selector_singleton;
                }
                bbm.setBackgroundResource(backgroundResId);
                bbm.setPadding(0, spacing, 0, spacing);
                bbm.setGravity(Gravity.CENTER);
                bbm.setText(bottomMenu.funName);
                bbm.setTextColor(0xFF007AFF);
                bbm.setTextSize(19);
                bbm.setOnClickListener(bottomMenu.listener);
                layContainer.addView(bbm);

                if (i != params.menuList.size() - 1)
                {
                    View viewDivider = new View(params.context);
                    viewDivider.setLayoutParams(lpDivider);
                    viewDivider.setBackgroundColor(0xFFCED2D6);
                    layContainer.addView(viewDivider);
                }
            }

            if (!TextUtils.isEmpty(params.cancelText))
            {
                btnCancel.setText(params.cancelText);
            }

            if (params.cancelListener != null)
            {
                btnCancel.setOnClickListener(params.cancelListener);
            } else
            {
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });
            }
            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(canCancel);
            dialog.setCancelable(canCancel);
            return dialog;
        }

    }

    private static class BottomMenu {

        private String funName;
        private View.OnClickListener listener;

        private BottomMenu(String funName, View.OnClickListener listener)
        {
            this.funName = funName;
            this.listener = listener;
        }
    }
}
