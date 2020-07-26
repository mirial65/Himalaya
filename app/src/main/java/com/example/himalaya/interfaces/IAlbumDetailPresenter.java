package com.example.himalaya.interfaces;

import com.example.himalaya.base.IBasePresenter;

/**
 * Description :
 * Time : 2020/7/24 16:27
 * Author : zengkun
 */
public interface IAlbumDetailPresenter extends IBasePresenter<IAlbumDetailViewCallback> {
    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 获取专辑详情
     * @param albumId
     * @param page
     */
    void getAlbumDetail(int albumId, int page);

}
