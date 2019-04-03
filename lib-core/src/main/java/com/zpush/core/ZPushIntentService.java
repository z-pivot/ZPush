package com.zpush.core;

import android.app.IntentService;
import android.content.Intent;


/**
 * 透传服务类
 */
public abstract class ZPushIntentService extends IntentService {

    public static final String TAG = "ZPushIntentService";

    public ZPushIntentService() {
        super("ZPushIntentService");
    }

    @Override
    public final void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            try {
                ZPushMessage message = (ZPushMessage) intent.getSerializableExtra(ZPushMessageReceiver.MESSAGE);
                if (ZPushMessageReceiver.RECEIVE_THROUGH_MESSAGE.equals(action)) {
                    onReceivePassThroughMessage(message);
                } else if (ZPushMessageReceiver.NOTIFICATION_ARRIVED.equals(action)) {
                    onNotificationMessageArrived(message);
                } else if (ZPushMessageReceiver.NOTIFICATION_CLICKED.equals(action)) {
                    onNotificationMessageClicked(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 透传
     */
    public abstract void onReceivePassThroughMessage(ZPushMessage message);

    /**
     * 通知栏消息点击
     */
    public abstract void onNotificationMessageClicked(ZPushMessage message);

    /**
     * 通知栏消息到达
     */
    public void onNotificationMessageArrived(ZPushMessage message) {

    }
}