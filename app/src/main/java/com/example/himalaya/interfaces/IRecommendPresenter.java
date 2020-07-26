package com.example.himalaya.interfaces;

import com.example.himalaya.base.IBasePresenter;

/**
 * 逻辑层实现的接口
 * 也就是我们这个界面有什么功能
 */
public interface IRecommendPresenter extends IBasePresenter<IRecommendViewCallback> {
    /**
     * 获取推荐内容
     */
    void getRecommendList();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

}
