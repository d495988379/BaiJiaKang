package com.jess.arms.mvp;

import android.app.Activity;
import android.content.Intent;

import com.jess.arms.utils.ArmsUtils;

import androidx.annotation.NonNull;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public interface IView {

    /**
     * 显示加载
     */
    default void showLoading() {

    }

    /**
     * 隐藏加载
     */
    default void hideLoading() {

    }

    /**
     *  加载更多开始
     */
    default void startLoadMore() {

    }

    /**
     *  加载更多结束
     */
    default void endLoadMore() {

    }

    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    default void showMessage(@NonNull String message) {

    }

    /**
     * 跳转 {@link Activity}
     *
     * @param
     */
    default void launchActivity() {

    }

    /**
     * 杀死自己
     */
    default void killMyself() {

    }
}
