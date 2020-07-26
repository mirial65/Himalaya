package com.example.himalaya;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.himalaya.base.BaseActivity;
import com.example.himalaya.interfaces.IPlayerCallback;
import com.example.himalaya.presenters.PlayerPresenter;
import com.example.himalaya.utils.DateFormatUtil;
import com.example.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

/**
 * Description :
 * Time : 2020/7/25 15:07
 * Author : zengkun
 */
public class PlayerActivity extends BaseActivity implements IPlayerCallback {

    private static final String TAG = "PlayerActivity";
    private ImageView mControl;
    private PlayerPresenter mPlayerPresenter;
    private TextView mCurrentPos;
    private TextView mTrackDuration;
    private SeekBar mTrackSeekBar;
    private int mCurrentPercent = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //测试播放
        mPlayerPresenter = PlayerPresenter.getPlayerPresenter();
        mPlayerPresenter.registerViewCallback(this);
        initView();
        initEvent();
        startPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayerPresenter != null) {
            mPlayerPresenter.unregisterViewCallback(this);
            mPlayerPresenter = null;
        }
    }

    /**
     * 默认一进来就开始播放
     */
    private void startPlay() {
        if (mPlayerPresenter != null) {
            mPlayerPresenter.play();
            LogUtil.d(TAG, "startPlay");
        }
    }

    /**
     * 给控件设置事件
     */
    private void initEvent() {
        mControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果现在的状态是正在播放，那么就暂停
                //如果现在的状态是非播放，那么就播放
                if (mPlayerPresenter.isPlay()) {
                    mPlayerPresenter.pause();
                    LogUtil.d(TAG, "pause");
                }else{
                    mPlayerPresenter.play();
                    LogUtil.d(TAG, "play");
                }
            }
        });
        mTrackSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //人为拖动
                if (fromUser) {
                    mCurrentPercent = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //mPlayerPresenter.seekTo(mCurrentPercent);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayerPresenter.seekTo(mCurrentPercent);
            }
        });
    }

    /**
     * 找到各个控件
     */
    private void initView() {
        mControl = findViewById(R.id.play_or_pause_btn);
        mCurrentPos = findViewById(R.id.current_position);
        mTrackDuration = findViewById(R.id.track_duration);
        mTrackSeekBar = findViewById(R.id.track_seek_bar);
    }

    @Override
    public void onPlayStart() {
        //开始播放，修改ui的播放按钮
        if (mControl != null) {
            mControl.setImageResource(R.mipmap.stop);
        }

    }

    @Override
    public void onPlayPause() {
        if (mControl != null) {
            mControl.setImageResource(R.mipmap.play);
        }

    }

    @Override
    public void onPlayStop() {
        if (mControl != null) {
            mControl.setImageResource(R.mipmap.play);
        }
    }

    @Override
    public void onPlayError() {

    }

    @Override
    public void onNextPlay(Track track) {

    }

    @Override
    public void onPrePlay(Track track) {

    }

    @Override
    public void onListLoaded(List<Track> list) {

    }

    @Override
    public void onPlayModeChange(XmPlayListControl.PlayMode mode) {

    }

    @Override
    public void onProgressChange(int currentTime, int total) {
        if (mTrackSeekBar != null) {
            mTrackSeekBar.setMax(total);
        }
        String currentDuration;
        String totalDuration;
        LogUtil.d(TAG, "currentTime---->" + currentTime);
        if (total > 1000 * 3600) {
            currentDuration = DateFormatUtil.formatHour(currentTime);
            totalDuration = DateFormatUtil.formatHour(total);
        }else {
            currentDuration = DateFormatUtil.formatMin(currentTime);
            totalDuration = DateFormatUtil.formatMin(total);
        }
        //设置当前时长及总时长
        if (mCurrentPos != null) {
            mCurrentPos.setText(currentDuration);
        }
        if (mTrackDuration != null) {
            mTrackDuration.setText(totalDuration);
        }
        //设置进度条
        if (mTrackSeekBar != null) {
            mTrackSeekBar.setProgress(currentTime);
        }

    }

    @Override
    public void onAdLoading() {

    }

    @Override
    public void onAdFinish() {

    }
}