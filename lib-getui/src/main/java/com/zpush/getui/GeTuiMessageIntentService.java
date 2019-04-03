package com.zpush.getui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.zpush.core.ZPushMessage;


public class GeTuiMessageIntentService extends GTIntentService {
    private final String TAG = "GeTuiMessageIntentService";

    public GeTuiMessageIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        super.onHandleIntent(intent);
    }

    @Override
    public void onReceiveClientId(Context context, String clientId) {
        Log.d(TAG, "onReceiveClientId -> " + "clientId = " + clientId);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        Log.d(TAG, "onReceiveCommandResult -> " + "action = " + cmdMessage.getAction());
    }

    /**
     * 透传消息
     *
     * @param context 上下文
     * @param message 透传信息结构体GTTransmitMessage
     *                message.getPayload()		    透传内容
     */
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage message) {
        if (message.getPayload() == null) {
            Log.e(TAG, "透传消息: payload=null");
            return;
        }
        String data = new String(message.getPayload());
        if (GeTuiManager.sMessageProvider != null) {
            ZPushMessage msg = new ZPushMessage();
            msg.setContent(data);
            msg.setPlatform(GeTuiManager.NAME);
            GeTuiManager.sMessageProvider.onReceivePassThroughMessage(context, msg);
        }
    }

    /**
     * 通知到达
     *
     * @param context 上下文
     * @param message 通知信息结构体GTNotificationMessage
     *                message.getTitle()		    通知标题
     *                message.getContent()		    通知正文内容
     */
    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage message) {
        if (message.getContent() == null) {
            Log.e(TAG, "通知到达: content=null");
            return;
        }
        if (GeTuiManager.sMessageProvider != null) {
            ZPushMessage msg = new ZPushMessage();
            msg.setTitle(message.getTitle());
            msg.setContent(message.getContent());
            msg.setPlatform(GeTuiManager.NAME);
            GeTuiManager.sMessageProvider.onNotificationMessageArrived(context, msg);
        }
    }

    /**
     * 点击通知回调
     *
     * @param context 上下文
     * @param message 通知信息结构体GTNotificationMessage
     *                message.getTitle()		    通知标题
     *                message.getContent()		    通知正文内容
     */
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage message) {
        if (message.getContent() == null) {
            Log.e(TAG, "通知点击: content=null");
            return;
        }
        if (GeTuiManager.sMessageProvider != null) {
            ZPushMessage msg = new ZPushMessage();
            msg.setTitle(message.getTitle());
            msg.setContent(message.getContent());
            msg.setPlatform(GeTuiManager.NAME);
            GeTuiManager.sMessageProvider.onNotificationMessageClicked(context, msg);
        }
    }
}