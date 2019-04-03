package com.zpush.jpush;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.zpush.core.ZPushMessage;

import cn.jpush.android.api.JPushInterface;

/**
 * Author      : BWj
 * Date        : 2019/3/27
 * Description : 极光推送信息接收处理类
 */
public class JPushMessageReceiver extends BroadcastReceiver {
    private final String TAG = "JPushMessageReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();


        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (TextUtils.isEmpty(message) && TextUtils.isEmpty(extras)){
                Log.e(TAG, "透传消息: message=null, extras=null");
                return;
            }
            ZPushMessage msg = new ZPushMessage();
            msg.setTitle(title);
            msg.setContent(message);
            msg.setCustomContent(extras);
            msg.setPlatform(JPushManager.NAME);
            JPushManager.sMessageProvider.onReceivePassThroughMessage(context, msg);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_ALERT);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (TextUtils.isEmpty(message) && TextUtils.isEmpty(extras)){
                Log.e(TAG, "通知到达: message=null, extras=null");
                return;
            }
            ZPushMessage msg = new ZPushMessage();
            msg.setTitle(title);
            msg.setContent(message);
            msg.setCustomContent(extras);
            msg.setPlatform(JPushManager.NAME);
            JPushManager.sMessageProvider.onNotificationMessageArrived(context, msg);


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_ALERT);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (TextUtils.isEmpty(message) && TextUtils.isEmpty(extras)){
                Log.e(TAG, "通知点击: message=null, extras=null");
                return;
            }
            ZPushMessage msg = new ZPushMessage();
            msg.setTitle(title);
            msg.setContent(message);
            msg.setCustomContent(extras);
            msg.setPlatform(JPushManager.NAME);
            JPushManager.sMessageProvider.onNotificationMessageClicked(context, msg);

        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}