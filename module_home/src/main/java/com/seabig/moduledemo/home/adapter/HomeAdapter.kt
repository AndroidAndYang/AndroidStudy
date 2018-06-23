package com.seabig.moduledemo.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.seabig.moduledemo.home.R

/**
 * author： YJZ
 * date:  2018/6/23
 * des:   Home
 */

class HomeAdapter(layoutResId: Int, data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

    override fun convert(baseViewHolder: BaseViewHolder, s: String) {
        baseViewHolder.setText(R.id.text,s)
    }
}
