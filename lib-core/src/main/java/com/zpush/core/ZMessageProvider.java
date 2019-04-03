package com.zpush.core;

import android.content.Context;


public interface ZMessageProvider {
    /**
     * 透传（自定义消息）
     */
    public void onReceivePassThroughMessage(Context context, ZPushMessage message);

    /**
     * 通知栏消息点击
     */
    public void onNotificationMessageClicked(Context context, ZPushMessage message);

    /**
     * 通知栏消息到达
     */
    public void onNotificationMessageArrived(Context context, ZPushMessage message);

}
