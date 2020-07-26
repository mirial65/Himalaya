package com.example.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

/**
 * Description :
 * Time : 2020/7/24 16:37
 * Author : zengkun
 */
public interface IAlbumDetailViewCallback {
    /**
     * 加载数据的回调
     * @param tracks
     */
    void onDetailListLoaded(List<Track> tracks);

    /**
     * 网络错误
     */
    void onNetworkError(int errorCode, String errorMessage);
    /**
     * 把album传给ui使用
     *
     * @param album
     */
    void onAlbumLoaded(Album album);
}
