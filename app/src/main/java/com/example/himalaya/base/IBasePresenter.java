package com.example.himalaya.base;

/**
 * Description :
 * Time : 2020/7/25 15:44
 * Author : zengkun
 */
public interface IBasePresenter<T> {
    /**
     * 注册通知ui的接口
     * @param
     */
    void registerViewCallback(T t);

    /**
     * 取消ui通知的接口
     * @param
     */
    void unregisterViewCallback(T t);
}