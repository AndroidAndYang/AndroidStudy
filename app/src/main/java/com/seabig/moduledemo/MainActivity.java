package com.seabig.moduledemo;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.seabig.annotation.anno.BindView;
import com.seabig.apt_library.BindViewTools;
import com.seabig.moduledemo.one.activity.OneActivity;
import com.seabig.moduledemo.widget.ui.activity.ScreenSlideActivity;

/**
 * @author AirBike-Work2
 */
public class MainActivity extends AppCompatActivity {

    @BindView (R.id.tv) TextView textView;
    @BindView (R.id.btn) Button button;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindViewTools.bind(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        textView.setText("我是TextView");
        button.setText("我是Button");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment, new MyFragment());
        transaction.commit();
    }

    public void clickOne(View view)
    {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "ID");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "NAME");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        startActivity(new Intent(this, OneActivity.class));
    }

    public void clickTwo(View view)
    {
        startActivity(new Intent(this, ScreenSlideActivity.class));
    }
}
