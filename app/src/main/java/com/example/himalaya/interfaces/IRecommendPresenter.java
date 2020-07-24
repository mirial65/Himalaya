package com.example.himalaya.interfaces;

/**
 * 逻辑层实现的接口
 * 也就是我们这个界面有什么功能
 */
public interface IRecommendPresenter {
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

    /**
     * 这个接口用于注册UI的回调
     *
     * @param callback
     */
    void registerViewCallback(IRecommendViewCallback callback);

    /**
     * 取消ui的回调
     * @param callback
     */
    void unregisterViewCallback(IRecommendViewCallback callback);
}
