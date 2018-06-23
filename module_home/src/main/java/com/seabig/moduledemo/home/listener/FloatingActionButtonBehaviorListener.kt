package com.seabig.moduledemo.home.listener

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView

/**
 * author： YJZ
 * date:  2018/6/23
 * des:  RecyclerView 滑动监听 FloatingActionButton
 */

object FloatingActionButtonBehaviorListener {

    private const val SCROLL_DIS_SHOW = 4

    class ForRecyclerView(private val floatingActionButton: FloatingActionButton) : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            if (dy > SCROLL_DIS_SHOW) {
                floatingActionButton.hide()
            } else if (dy < -SCROLL_DIS_SHOW){
                floatingActionButton.show()
            }
        }
    }
}
