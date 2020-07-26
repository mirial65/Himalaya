package com.example.himalaya.presenters;

import com.example.himalaya.interfaces.IAlbumDetailPresenter;
import com.example.himalaya.interfaces.IAlbumDetailViewCallback;
import com.example.himalaya.utils.Constants;
import com.example.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description :
 * Time : 2020/7/24 16:30
 * Author : zengkun
 */
public class AlbumDetailPresenter implements IAlbumDetailPresenter {
    private static final String TAG = "AlbumDetailPresenter";
    private List<IAlbumDetailViewCallback> mCallback = new ArrayList<>();
    private static AlbumDetailPresenter sInstance = null;
    private Album mTargetAlbum = null;

    private AlbumDetailPresenter() {

    }

    public static AlbumDetailPresenter getInstance() {
        if (sInstance == null) {
            synchronized (AlbumDetailPresenter.class) {
                if (sInstance == null) {
                    sInstance = new AlbumDetailPresenter();
                }
            }
        }
        return sInstance;
    }
    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void getAlbumDetail(int albumId, int page) {
        //根据页码和专辑id获取列表
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_ID, albumId + "");
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, page + "");
        //可以自己设置一次加载多少条
        map.put(DTransferConstants.PAGE_SIZE, Constants.COUNT_DEFAULT + "");
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(TrackList trackList) {
                if (trackList != null) {
                    LogUtil.d(TAG, "current thread-->" + Thread.currentThread().getName());
                    List<Track> tracks = trackList.getTracks();
                    LogUtil.d(TAG, "track size--->" + tracks.size());
                    handlerAlbumDetail(tracks);
                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                handlerError(errorCode, errorMessage);
                LogUtil.d(TAG, "errorCode--->" + errorCode);
                LogUtil.d(TAG, "errorMessage--->" + errorMessage);
            }
        });

    }

    /**
     * 网络错误，通知ui
     * @param errorCode
     * @param errorMessage
     */
    private void handlerError(int errorCode, String errorMessage) {
        for (IAlbumDetailViewCallback callback : mCallback) {
            callback.onNetworkError(errorCode,  errorMessage);
        }
    }

    private void handlerAlbumDetail(List<Track> tracks) {
        for (IAlbumDetailViewCallback callback : mCallback) {
            callback.onDetailListLoaded(tracks);
        }
    }

    @Override
    public void registerViewCallback(IAlbumDetailViewCallback callback) {
        if (!mCallback.contains(callback)) {
            mCallback.add(callback);
            if (mTargetAlbum != null) {
                callback.onAlbumLoaded(mTargetAlbum);
            }
        }
    }

    @Override
    public void unregisterViewCallback(IAlbumDetailViewCallback callback) {
        mCallback.remove(callback);
    }

    public void setTargetAlbum(Album targetAlbum) {
        this.mTargetAlbum = targetAlbum;
    }
}