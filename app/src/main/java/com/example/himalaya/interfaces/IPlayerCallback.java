package com.example.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

/**
 * Description :
 * Time : 2020/7/25 15:50
 * Author : zengkun
 */
public interface IPlayerCallback {
    /**
     * 开始播放
     */
    void onPlayStart();

    /**
     * 暂停播放
     */
    void onPlayPause();

    /**
     * 停止播放
     */
    void onPlayStop();

    /**
     * 播放出错
     */
    void onPlayError();

    /**
     * 播放下一首
     */
    void onNextPlay(Track track);

    /**
     * 播放上一首
     */
    void onPrePlay(Track track);

    /**
     * 播放列表数据加载完成
     * @param list
     */
    void onListLoaded(List<Track> list);

    /**
     * 播放器模式改变
     * @param mode
     */
    void onPlayModeChange(XmPlayListControl.PlayMode mode);

    /**
     * 进度条的改变
     * @param currentTime
     * @param total
     */
    void onProgressChange(int currentTime, int total);

    /**
     * 加载广告
     */
    void onAdLoading();

    /**
     * 广告加载完成
     */
    void onAdFinish();
}