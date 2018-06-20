package com.seabig.moduledemo.sys.listener;

/**
 * author： YJZ
 * date:  2018/6/16
 * des: 声音监听
 */

public interface IAudioListen {

    /**
     * controller turn
     */
    void top();

    /**
     * pause electrical machinery
     */
    void pause();

    /**
     * Update interface display
     */
    void update(double volume);

}
