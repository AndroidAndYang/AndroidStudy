package com.seabig.moduledemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.seabig.annotation.anno.BindView;
import com.seabig.apt_library.BindViewTools;

import org.w3c.dom.Text;

/**
 * @author YJZ
 *         date 2018/5/10
 *         description
 */

public class MyFragment extends Fragment {

    @BindView (R.id.text_view) TextView textView;
    @BindView (R.id.button) Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        textView = (TextView) getActivity().findViewById(R.id.text_view);
        textView.setText("adasdsa");

    }
}
