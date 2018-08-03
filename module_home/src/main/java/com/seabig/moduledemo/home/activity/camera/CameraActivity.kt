package com.seabig.moduledemo.home.activity.camera

import android.graphics.BitmapFactory
import android.graphics.RectF
import android.hardware.Camera
import android.view.View
import android.view.WindowManager
import com.android.library.util.LogUtils
import com.seabig.moduledemo.common.base.BaseActivity
import com.seabig.moduledemo.common.util.BitmapUtils
import com.seabig.moduledemo.common.util.FileUtil
import com.seabig.moduledemo.home.R
import com.seabig.moduledemo.home.util.camera.CameraHelper
import com.seabig.moduledemo.home.util.camera.MediaRecorderHelper
import kotlinx.android.synthetic.main.home_activity_camera.*
import okio.Okio
import kotlin.concurrent.thread

/**
 * author :  YJZ
 * data  :  2018/3/18
 * desc :
 */
class CameraActivity : BaseActivity() {

    companion object {
        const val TYPE_TAG = "type"
        const val TYPE_CAPTURE = 0
        const val TYPE_RECORD = 1
    }

    /**
     * 控制MediaRecorderHelper的初始化
     */
    var lock = false

    private lateinit var mCameraHelper: CameraHelper
    private var mMediaRecorderHelper: MediaRecorderHelper? = null

    override fun onSettingUpContentViewResourceID(): Int {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        return R.layout.home_activity_camera
    }

    override fun onSettingUpView() {

        var isPic = false
        mCameraHelper = CameraHelper(this, surfaceView)
        mCameraHelper.addCallBack(object : CameraHelper.CallBack {
            override fun onFaceDetect(faces: ArrayList<RectF>) {
                faceView.setFaces(faces)
                LogUtils.e("检测到 ${faces.size} 张人脸")
                if (faces.size > 0) {
                    if (!isPic) {
                        mCameraHelper.takePic()
                        isPic = true
                    }
                }
            }

            override fun onTakePic(data: ByteArray?) {
                savePic(data)
                btnTakePic.isClickable = true
            }

            override fun onPreviewFrame(data: ByteArray?) {
                if (!lock) {
                    mCameraHelper.getCamera()?.let {
                        mMediaRecorderHelper = MediaRecorderHelper(this@CameraActivity, mCameraHelper.getCamera()!!, mCameraHelper.mDisplayOrientation, mCameraHelper.mSurfaceHolder.surface)
                    }
                    lock = true
                }
            }
        })

        if (intent.getIntExtra(TYPE_TAG, 0) == TYPE_RECORD) { //录视频
            btnTakePic.visibility = View.GONE
            btnStart.visibility = View.VISIBLE
        }

        btnTakePic.setOnClickListener { mCameraHelper.takePic() }
        ivExchange.setOnClickListener { mCameraHelper.exchangeCamera() }
        btnStart.setOnClickListener {
            ivExchange.isClickable = false
            btnStart.visibility = View.GONE
            btnStop.visibility = View.VISIBLE
            mMediaRecorderHelper?.startRecord()
        }
        btnStop.setOnClickListener {
            btnStart.visibility = View.VISIBLE
            btnStop.visibility = View.GONE
            ivExchange.isClickable = true
            mMediaRecorderHelper?.stopRecord()
        }
    }

    private fun savePic(data: ByteArray?) {
        thread {
            try {
                val temp = System.currentTimeMillis()
                val picFile = FileUtil.createCameraFile()
                if (picFile != null && data != null) {
                    val rawBitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                    val resultBitmap = if (mCameraHelper.mCameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT)
                        BitmapUtils.mirror(BitmapUtils.rotate(rawBitmap, 270f))
                    else
                        BitmapUtils.rotate(rawBitmap, 90f)

                    Okio.buffer(Okio.sink(picFile)).write(BitmapUtils.toByteArray(resultBitmap)).close()
                    runOnUiThread {
                        showToast("图片已保存! ${picFile.absolutePath}")
                        LogUtils.e("图片已保存! 耗时：${System.currentTimeMillis() - temp}    路径：  ${picFile.absolutePath}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    showToast("保存图片失败！")
                }
            }
        }
    }

    override fun onDestroy() {
        mCameraHelper.releaseCamera()
        mMediaRecorderHelper?.let {
            if (it.isRunning)
                it.stopRecord()
            it.release()
        }
        super.onDestroy()
    }

}