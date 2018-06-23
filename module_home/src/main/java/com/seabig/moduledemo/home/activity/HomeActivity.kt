package com.seabig.moduledemo.home.activity

import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.seabig.moduledemo.common.base.BaseActivity
import com.seabig.moduledemo.common.ui.widget.BannerLayout
import com.seabig.moduledemo.common.util.ActivityUtils
import com.seabig.moduledemo.common.util.ToastUtils
import com.seabig.moduledemo.home.R
import java.util.ArrayList

/**
 * author： YJZ
 * date:  2018/6/22
 * des:   主页
 */
class HomeActivity : BaseActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    override fun onSettingUpContentViewResourceID(): Int {
        return R.layout.home_activty_home
    }

    override fun onSettingUpView() {

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "AndroidStudy"
        setSupportActionBar(toolbar)

        findViewById(R.id.fab).setOnClickListener(this)

        // 设置ToolBar左边的Image跟随DrawLayout滚动
        val drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 侧滑菜单
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        val view = navigationView.inflateHeaderView(R.layout.home_nav_header_main)

        val userNameTv = view.findViewById(R.id.username) as TextView
        val name = "Yang"
        userNameTv.text = name

        val userDesTv = view.findViewById(R.id.des) as TextView
        val des = "good good study,day day up！"
        userDesTv.text = des

        val mBanner = findViewById(R.id.banner) as BannerLayout

        val url = ArrayList<String>()
        url.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3526671419,880179147&fm=27&gp=0.jpg")
        url.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3311055475,689736834&fm=27&gp=0.jpg")
        url.add("http://img3.imgtn.bdimg.com/it/u=1039587549,3670387024&fm=27&gp=0.jpg")
        url.add("http://img0.imgtn.bdimg.com/it/u=3544634194,545861522&fm=27&gp=0.jpg")
        // 轮播图
        mBanner.setImageLoader { context, path, imageView ->
            Glide.with(context).load(path).into(imageView) }
        mBanner.setViewUrls(url)
        mBanner.setOnBannerItemClickListener {
            ToastUtils.getInstance().showToast(this, "position = $it")
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fab -> {
                Snackbar.make(v, "FloatingActionButton is Click", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 省略控件点击展开
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.nav_camera -> {
                ToastUtils.getInstance().showToast(this, "Camera")
            }

            R.id.nav_gallery -> {
                ToastUtils.getInstance().showToast(this, "Album")
            }

            R.id.nav_scan -> {
                ToastUtils.getInstance().showToast(this, "Scan")
            }

            R.id.nav_tools -> {
                ToastUtils.getInstance().showToast(this, "Tools")
            }

            R.id.about -> {
                ActivityUtils.startActivity(this,AboutActivity::class.java)
            }
        }
        // 如果是侧滑栏时打开的则关闭
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    // 创建 ToolBar最右边的省略图片控件
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}