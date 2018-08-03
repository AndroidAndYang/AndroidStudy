package com.seabig.moduledemo.sys.util;

import android.media.MediaRecorder;
import android.util.Log;

import com.seabig.moduledemo.common.util.FileUtil;
import com.seabig.moduledemo.sys.listener.IAudioListen;

import java.io.File;
import java.io.IOException;

/**
 * author： YJZ
 * date:  2018/6/22
 * des: 获取声音分贝大小 MediaRecorder(基于文件录音)，AudioRecord(基于字节流录音)
        优点：封装度很高，操作简单
        缺点：无法实现实时处理音频，输出的音频格式少
 */

public class MediaRecorderUtil {

    /**
     * 录音文件
     */
    private File mFileRec;
    /**
     * 音频管理类
     */
    private MediaRecorder mMediaRecorder;
    /**
     * 监测的声音大小
     */
    public static boolean isGetVoiceRun = false;

    /**
     * 检查声音的最大值
     */
    public static int MAX_VOLUME = 50;

    public MediaRecorderUtil() {
        // TODO 创建文件
         // mFileRec = FileUtil.createFile("test.amr");
    }

    private IAudioListen listen;

    public void startRecord() {
        try {
            // MediaRecorder(基于文件录音)，AudioRecord(基于字节流录音)
            /*
             MediaRecorder(基于文件录音) ：
                 已集成了录音，编码，压缩等，支持少量的音频格式文件。
                 优点：封装度很高，操作简单
                 缺点：无法实现实时处理音频，输出的音频格式少
             */
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(mFileRec.getAbsolutePath());
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isGetVoiceRun) {
                    try {
                        // 声音大小
                        double volume = mMediaRecorder.getMaxAmplitude();

                        float db = 0;

                        if (volume > 0 && volume < 1000000) {
                            // 转换成分贝
                            db = 20 * (float) (Math.log10(volume));
                        }

                        Log.e("TAG", "db = " + db);

                        listen.update(db);

                        if (db > MAX_VOLUME) {
                            Log.e("TAG", "声音超过阀值大小");
                            // listen.top();
                        }

                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        isGetVoiceRun = false;
                    }
                }
            }
        });
        thread.start();
    }

    public void setListen(IAudioListen listen) {
        this.listen = listen;
    }

    public void stop() {
        isGetVoiceRun = false;
        mMediaRecorder.stop();
    }
}
