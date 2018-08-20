package com.seabig.moduledemo.widget.ui.activity


import android.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.View
import com.seabig.moduledemo.common.base.BaseActivity
import com.seabig.moduledemo.widget.R
import com.seabig.moduledemo.widget.ui.widget.PieView

/**
 * @author YJZ
 * date 2018/6/3
 * description
 */

class PieActivity : BaseActivity(), View.OnClickListener {

    private var isRunning = false
    private lateinit var pieView: PieView

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.start -> {
                if (!isRunning) {
                    // val random = Random()
                    //pieView.rotate(i[random.nextInt(4)]);
                    pieView .rotate(2)
                }
                isRunning = true
            }
        }
    }

    override fun onSettingUpContentViewResourceID(): Int {
        return R.layout.widget_activity_pie
    }

    override fun onSettingUpView() {

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        initToolBar(toolbar, getStringByResId(R.string.widget_pie))

        findViewById(R.id.start).setOnClickListener(this)
        pieView = findViewById(R.id.pie) as PieView
        pieView.setListener { s ->
            isRunning = false
            AlertDialog.Builder(this@PieActivity)
                    .setTitle("很可惜，再接再厉")
                    .setMessage(s)
                    .setIcon(R.drawable.widget_pie_item_one)
                    .setNegativeButton("退出", null)
                    .show()
        }
    }
}
