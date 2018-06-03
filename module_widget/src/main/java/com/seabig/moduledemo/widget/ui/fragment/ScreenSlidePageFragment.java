package com.seabig.moduledemo.widget.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seabig.moduledemo.widget.R;


/**
 * @author YJZ
 *         date 2018/5/17
 *         description 沒有TabLayout的ViewPager的Fragment
 */

public class ScreenSlidePageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.widget_fragment_screen_slide_page, container, false);
    }
}
