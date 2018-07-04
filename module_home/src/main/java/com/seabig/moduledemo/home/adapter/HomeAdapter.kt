package com.seabig.moduledemo.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.seabig.moduledemo.home.R
import com.seabig.moduledemo.home.bean.HomeBean

/**
 * author： YJZ
 * date:  2018/6/23
 * des:   Home
 */

class HomeAdapter(layoutResId: Int, data: List<HomeBean>?) : BaseQuickAdapter<HomeBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(baseViewHolder: BaseViewHolder, homeBean: HomeBean) {

        baseViewHolder.setImageResource(R.id.item_img, homeBean.desImgPath)
        baseViewHolder.setText(R.id.item_name,homeBean.name)
        baseViewHolder.setText(R.id.item_des,homeBean.des)

        baseViewHolder.addOnClickListener(R.id.item_img)
    }
}
