package com.seabig.moduledemo.home.activity.camera

import android.content.Intent
import com.seabig.moduledemo.common.base.BaseActivity
import com.seabig.moduledemo.home.R
import kotlinx.android.synthetic.main.home_activity_camera_home.*

/**
 * author： YJZ
 * date:  2018/8/1
 * des:
 */
class CameraHomeActivity : BaseActivity() {

    override fun onSettingUpContentViewResourceID(): Int {
        return R.layout.home_activity_camera_home
    }

    override fun onSettingUpView() {

        bt_capture.setOnClickListener { startActivity(Intent(this, CaptureActivity::class.java)) }

        bt_camera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            intent.putExtra(CameraActivity.TYPE_TAG, CameraActivity.TYPE_CAPTURE)
            startActivity(intent)
        }

        bt_camera_record.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            intent.putExtra(CameraActivity.TYPE_TAG, CameraActivity.TYPE_RECORD)
            startActivity(intent)
        }
    }
}