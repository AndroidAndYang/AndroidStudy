package com.seabig.moduledemo.common.util

import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * author :  YJZ
 * data  :  2018/3/15
 * desc :
 */
object FileUtil {

    private const val LOCAL = "Android_Study"

    private val LOCAL_PATH = Environment.getExternalStorageDirectory().path + File.separator

    /**
     * 录音文件目录
     */
    val REC_PATH = LOCAL_PATH + LOCAL + File.separator

    /*
     * 自动在SD卡创建相关的目录
     */
    init {
        val dirRootFile = File(LOCAL_PATH)
        if (!dirRootFile.exists()) {
            dirRootFile.mkdirs()
        }
        val recFile = File(REC_PATH)
        if (!recFile.exists()) {
            recFile.mkdirs()
        }
    }

    /**
     * 判断是否存在存储空间
     */
    fun isExitSDCard(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * 是否存在改文件
     * @param fileName 文件名
     */
    private fun hasFile(fileName: String): Boolean {
        val f = createFile(fileName)
        return null != f && f.exists()
    }

    /**
     * 创建文件
     * @param fileName 文件名
     */
    fun createFile(fileName: String): File {

        val myCaptureFile = File(REC_PATH + fileName)
        if (myCaptureFile.exists()) {
            myCaptureFile.delete()
        }
        try {
            myCaptureFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return myCaptureFile
    }


    private val rootFolderPath = Environment.getExternalStorageDirectory().absolutePath + File.separator + "CameraDemo"

    fun createImageFile(isCrop: Boolean = false): File? {
        return try {
            val rootFile = File(rootFolderPath + File.separator + "capture")
            if (!rootFile.exists())
                rootFile.mkdirs()

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fileName = if (isCrop) "IMG_${timeStamp}_CROP.jpg" else "IMG_$timeStamp.jpg"
            File(rootFile.absolutePath + File.separator + fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun createCameraFile(): File? {
        return try {
            val rootFile = File(rootFolderPath + File.separator + "camera1")
            if (!rootFile.exists())
                rootFile.mkdirs()

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fileName = "IMG_$timeStamp.jpg"
            File(rootFile.absolutePath + File.separator + fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun createVideoFile(): File? {
        return try {
            val rootFile = File(rootFolderPath + File.separator + "video")
            if (!rootFile.exists())
                rootFile.mkdirs()

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fileName = "VIDEO_$timeStamp.mp4"
            File(rootFile.absolutePath + File.separator + fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}