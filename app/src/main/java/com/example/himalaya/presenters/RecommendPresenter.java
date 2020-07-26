package com.example.himalaya.presenters;

import com.example.himalaya.interfaces.IRecommendPresenter;
import com.example.himalaya.interfaces.IRecommendViewCallback;
import com.example.himalaya.utils.Constants;
import com.example.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendPresenter implements IRecommendPresenter {
    private static final String TAG = "RecommendPresenter";

    private List<IRecommendViewCallback> mCallbacks = new ArrayList<>();

    private RecommendPresenter() {

    }
    private static RecommendPresenter sInstance = null;

    /**
     * 获取单例
     * @return
     */
    public static RecommendPresenter getInstance() {
        if (sInstance == null) {
            synchronized (RecommendPresenter.class) {
                if (sInstance == null) {
                    sInstance = new RecommendPresenter();
                }
            }
        }
        return sInstance;
    }
    @Override
    public void getRecommendList() {
        //获取推荐内容
        updateLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.LIKE_COUNT, Constants.COUNT_RECOMMEND + "");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                //已经返回主线程。
                LogUtil.d(TAG, "thread_name---->" + Thread.currentThread().getName());
                //数据获取成功
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    //数据回来以后，我们要更新ui
                    //upRecommendUI(albumList);
                    handlerRecommendResult(albumList);
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, "error-->" + i);
                LogUtil.d(TAG, "errorMsg-->" + s);
                handlerError();
            }
        });

    }

    private void handlerError() {
        //通知UI更新
        if (mCallbacks != null) {
            for (IRecommendViewCallback callback : mCallbacks) {
                callback.onNetworkError();
            }
        }
    }

    private void handlerRecommendResult(List<Album> albumList) {
        //通知UI更新
        if (albumList != null) {
            //测试albums为空
            //albumList.clear();
            if (albumList.size() == 0) {
                for (IRecommendViewCallback callback : mCallbacks) {
                    callback.onEmpty();
                }
            } else{
                for (IRecommendViewCallback callback : mCallbacks) {
                    callback.onRecommendListLoaded(albumList);
                }
            }
        }
    }

    private void updateLoading() {
        //通知UI更新
        if (mCallbacks != null) {
            for (IRecommendViewCallback callback : mCallbacks) {
                callback.onLoading();
            }
        }
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IRecommendViewCallback callback) {
        if (mCallbacks != null && !mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unregisterViewCallback(IRecommendViewCallback callback) {

        if (mCallbacks != null) {
            mCallbacks.remove(callback);
        }
    }
}
