package com.example.himalaya.presenters;

import com.example.himalaya.base.BaseApplication;
import com.example.himalaya.interfaces.IPlayerCallback;
import com.example.himalaya.interfaces.IPlayerPresenter;
import com.example.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Time : 2020/7/25 16:54
 * Author : zengkun
 */
public class PlayerPresenter implements IPlayerPresenter, IXmAdsStatusListener, IXmPlayerStatusListener {

    private static final String TAG = "PlayerPresenter";
    private static PlayerPresenter sPlayerPresenter;
    private final XmPlayerManager mPlayerManager;
    private List<IPlayerCallback> mIPlayerCallbacks = new ArrayList<>();

    private PlayerPresenter() {
        mPlayerManager = XmPlayerManager.getInstance(BaseApplication.getAppContext());
        //广告相关的接口，并不是每次都有广告
        mPlayerManager.addAdsStatusListener(this);
        //注册播放器相关的接口
        mPlayerManager.addPlayerStatusListener(this);
    }

    public static PlayerPresenter getPlayerPresenter() {
        if (sPlayerPresenter == null) {
            synchronized (PlayerPresenter.class) {
                if (sPlayerPresenter == null) {
                    sPlayerPresenter = new PlayerPresenter();
                }
            }
        }
        return sPlayerPresenter;
    }

    private boolean isPlayListSet = false;

    public void setPlayList(List<Track> list, int playIndex) {
        if (mPlayerManager != null) {
            mPlayerManager.setPlayList(list, playIndex);
            isPlayListSet = true;
        } else {
            LogUtil.d(TAG, "mPlayerManager is null");
        }

    }

    @Override
    public void play() {
        LogUtil.d(TAG, "isPlayListSet--->" + (isPlayListSet ? 1 : 0));
        if (isPlayListSet) {
            mPlayerManager.play();
            if (!isPlay()) {
                play();
            }
        }

    }

    @Override
    public void pause() {
        if (mPlayerManager != null) {
            mPlayerManager.pause();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void playPre() {

    }

    @Override
    public void playNext() {

    }

    @Override
    public void switchPlayMode(XmPlayListControl.PlayMode mode) {

    }

    @Override
    public void getPlayList() {

    }

    @Override
    public void playByIndex(int index) {

    }

    @Override
    public void seekTo(int process) {
        mPlayerManager.seekTo(process);
    }

    @Override
    public boolean isPlay() {
        //返会当前是否正在播放
        return mPlayerManager.isPlaying();
    }

    @Override
    public void registerViewCallback(IPlayerCallback iPlayerCallback) {
        if (!mIPlayerCallbacks.contains(iPlayerCallback)) {
            mIPlayerCallbacks.add(iPlayerCallback);
        }
    }

    @Override
    public void unregisterViewCallback(IPlayerCallback iPlayerCallback) {
        mIPlayerCallbacks.remove(iPlayerCallback);
    }

    // ************广告相关的回调 start********************
    @Override
    public void onStartGetAdsInfo() {
        LogUtil.d(TAG, "onStartGetAdsInfo");
    }

    @Override
    public void onGetAdsInfo(AdvertisList advertisList) {
        LogUtil.d(TAG, "onGetAdsInfo");
    }

    @Override
    public void onAdsStartBuffering() {
        LogUtil.d(TAG, "onAdsStartBuffering");
    }

    @Override
    public void onAdsStopBuffering() {
        LogUtil.d(TAG, "onAdsStopBuffering");
    }

    @Override
    public void onStartPlayAds(Advertis advertis, int i) {
        LogUtil.d(TAG, "onStartPlayAds");
    }

    @Override
    public void onCompletePlayAds() {
        LogUtil.d(TAG, "onCompletePlayAds");
    }

    @Override
    public void onError(int what, int extra) {
        LogUtil.d(TAG, "onError   what --> " + what + " extra---> " + extra);
    }
    // ************广告相关的回调 finish* *******************

    //=================播放器相关的回调============
    @Override
    public void onPlayStart() {
        //LogUtil.d(TAG, "onPlayStart...");
        for (IPlayerCallback callback : mIPlayerCallbacks) {
            callback.onPlayStart();
        }
    }

    @Override
    public void onPlayPause() {
        //LogUtil.d(TAG, "onPlayPause...");
        for (IPlayerCallback callback : mIPlayerCallbacks) {
            callback.onPlayPause();
        }
    }

    @Override
    public void onPlayStop() {
        //LogUtil.d(TAG, "onPlayStop...");
        for (IPlayerCallback callback : mIPlayerCallbacks) {
            callback.onPlayStop();
        }
    }

    @Override
    public void onSoundPlayComplete() {
        //LogUtil.d(TAG, "onSoundPlayComplete...");
    }

    @Override
    public void onSoundPrepared() {
        //LogUtil.d(TAG, "onSoundPrepared...");
    }

    @Override
    public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
        //LogUtil.d(TAG, "onSoundSwitch...");
    }

    @Override
    public void onBufferingStart() {
        //LogUtil.d(TAG, "onBufferingStart");
    }

    @Override
    public void onBufferingStop() {
        //LogUtil.d(TAG, "onBufferingStop");
    }

    @Override
    public void onBufferProgress(int progress) {
        //LogUtil.d(TAG, "onBufferProgress---->" + progress);
    }

    @Override
    public void onPlayProgress(int currPos, int duration) {
        //LogUtil.d(TAG, "onPlayProgress...");
        for (IPlayerCallback callback : mIPlayerCallbacks) {
            callback.onProgressChange(currPos, duration);
        }
    }

    @Override
    public boolean onError(XmPlayerException e) {
        //LogUtil.d(TAG, "onError--->" + e);
        return false;
    }
    //=================播放器相关的回调============

}