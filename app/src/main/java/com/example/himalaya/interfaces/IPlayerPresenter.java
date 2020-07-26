package com.example.himalaya.interfaces;

import com.example.himalaya.base.IBasePresenter;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

/**
 * Description :
 * Time : 2020/7/25 15:43
 * Author : zengkun
 */
public interface IPlayerPresenter extends IBasePresenter<IPlayerCallback> {


    /**
     * 播放
     */
    void play();

    /**
     * 暂替
     */
    void pause();

    /**
     * 停止
     */
    void stop();

    /**
     * 播放上一首
     */
    void playPre();

    /**
     * 播放下一首
     */
    void playNext();

    /**
     * 切换播放模式
     * @param mode
     */
    void switchPlayMode(XmPlayListControl.PlayMode mode);

    /**
     * 获取播放列表
     *
     */
    void getPlayList();

    /**
     * 根据节目位置进行播放
     * @param index 节目再列表中的位置
     */
    void playByIndex(int index);

    /**
     * 切换播放进度
     * @param process
     */
    void seekTo(int process);

    /**
     * 判断是否播放
     * @return
     */
    boolean isPlay();
}