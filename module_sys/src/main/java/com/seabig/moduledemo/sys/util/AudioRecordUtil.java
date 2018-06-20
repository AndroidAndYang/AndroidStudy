package com.seabig.moduledemo.sys.util;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.seabig.moduledemo.sys.listener.IAudioListen;

/**
 * author： YJZ
 * date:  2018/6/16
 * des:
 */

public class AudioRecordUtil {

    private static final String TAG = "AudioRecord";
    private static final int SAMPLE_RATE_IN_HZ = 8000;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    private AudioRecord mAudioRecord;
    public static boolean isGetVoiceRun;
    private Object mLock;
    private IAudioListen audioListen;

    public static int MAX_VOLUME = 50;

    private boolean isStart = false;

    public void setAudioRecordListener(IAudioListen audioRecordListener) {
        this.audioListen = audioRecordListener;
    }

    public AudioRecordUtil() {
        mLock = new Object();
    }

    public void getNoiseLevel() {

        if (isGetVoiceRun) {
            Log.e(TAG, "还在录着呢");
            return;
        }

        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);

        if (mAudioRecord == null) {
            Log.e("sound", "mAudioRecord初始化失败");
        }

        isGetVoiceRun = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAudioRecord.startRecording();
                short[] buffer = new short[BUFFER_SIZE];
                while (isGetVoiceRun) {
                    //r是实际读取的数据长度，一般而言r会小于bufferSize
                    int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (short aBuffer : buffer) {
                        v += aBuffer * aBuffer;
                    }
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) r;
                    double volume = 10 * Math.log10(mean);

                    audioListen.update(volume);

                    Log.e(TAG, "分贝值:" + volume);

                    if (volume > MAX_VOLUME) {
                        isStart = true;
                        audioListen.top();
                    } else {
                        if (isStart) {
                            isStart = false;
                            audioListen.pause();
                        }
                    }

                    // 大概一秒十次
                    synchronized (mLock) {
                        try {
                            mLock.wait(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            }
        }).start();
    }
}
