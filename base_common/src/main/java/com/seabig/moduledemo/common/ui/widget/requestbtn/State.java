package com.seabig.moduledemo.common.ui.widget.requestbtn;

/**
 * author:  YJZ
 * date: 2017/10/18
 * description：图标状态
 */

public enum State {
    /**
     * idle
     * <p>
     * 闲置状态
     */
    IDLE,
    /**
     * progress
     * <p>
     * 请求状态
     */
    PROGRESS,
    /**
     * circle or tick,on style tick start circle
     * <p>
     * 画圈或打钩，打钩和画圈同时进行的样式
     */
    TICK_START_CIRCLE,
    /**
     * circle or tick,on style tick half circle
     * <p>
     * 画圈或打钩，画圈到一半打钩的样式
     */
    TICK_HALF_CIRCLE,
    /**
     * circle or tick,on style tick end circle
     * <p>
     * 画圈或打钩，画完圈打钩的样式
     */
    TICK_END_CIRCLE
}
