package com.seabig.moduledemo.common.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    private static final String LOCAL = "Android_Study";

    private static final String LOCAL_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator;

    /**
     * 录音文件目录
     */
    public static final String REC_PATH = LOCAL_PATH + LOCAL + File.separator;

    /*
     * 自动在SD卡创建相关的目录
     */
    static {
        File dirRootFile = new File(LOCAL_PATH);
        if (!dirRootFile.exists()) {
            dirRootFile.mkdirs();
        }
        File recFile = new File(REC_PATH);
        if (!recFile.exists()) {
            recFile.mkdirs();
        }
    }

    /**
     * 判断是否存在存储空间
     */
    public static boolean isExitSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 是否存在改文件
     * @param fileName 文件名
     */
    private static boolean hasFile(String fileName) {
        File f = createFile(fileName);
        return null != f && f.exists();
    }

    /**
     * 创建文件
     * @param fileName 文件名
     */
    public static File createFile(String fileName) {

        File myCaptureFile = new File(REC_PATH + fileName);
        if (myCaptureFile.exists()) {
            myCaptureFile.delete();
        }
        try {
            myCaptureFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCaptureFile;
    }
}
