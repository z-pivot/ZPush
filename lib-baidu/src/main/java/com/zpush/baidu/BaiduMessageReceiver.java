package com.zpush.baidu;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.zpush.core.ZPushMessage;

import java.util.List;

/**
 * Author      : BWj
 * Date        : 2019/3/28
 * Description : 百度云推送 消息接收处理类
 */
public class BaiduMessageReceiver extends PushMessageReceiver {
    private final String TAG = "BaiduMessageReceiver";

    /**
     * 调用PushManager.startWork后，sdk将对push server发起绑定请求，这个过程是异步的。
     * 绑定请求的结果通过onBind返回
     *
     * @param context   BroadcastReceiver的执行Context
     * @param errorCode 绑定接口返回值，0 - 成功
     * @param appId     应用id，errorCode非0时为null
     * @param userId    应用user id，errorCode非0时为null
     * @param channelId 应用channel id，errorCode非0时为null
     * @param requestId 向服务端发起的请求id，在追查问题时有用
     */
    @Override
    public void onBind(Context context, int errorCode, String appId, String userId, String channelId, String requestId) {

    }

    /**
     * PushManager.stopWork() 的回调函数。
     *
     * @param context   上下文
     * @param errorCode 错误码，0表示从云推送解绑定成功，非0表示失败
     * @param requestId 分配给对云推送的请求的id
     */
    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {

    }

    /**
     * setTags() 的回调函数。
     *
     * @param context     上下文
     * @param errorCode   错误码，0表示某些tag已经设置成功，非0表示所有tag的设置均失败
     * @param successTags 设置成功的tag
     * @param failTags    设置失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
    @Override
    public void onSetTags(Context context, int errorCode, List successTags, List failTags, String requestId) {

    }

    /**
     * delTags() 的回调函数。
     *
     * @param context     上下文
     * @param errorCode   错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败
     * @param successTags 成功删除的tag
     * @param failTags    删除失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
    @Override
    public void onDelTags(Context context, int errorCode, List successTags, List failTags, String requestId) {

    }

    /**
     * listTags() 的回调函数。
     *
     * @param context   上下文
     * @param errorCode 错误码。0表示列举tag成功；非0表示失败
     * @param tags      当前应用设置的所有tag
     * @param requestId 分配给对云推送的请求的id
     */
    @Override
    public void onListTags(Context context, int errorCode, List tags, String requestId) {

    }

    /**
     * 收到透传消息
     *
     * @param context             上下文
     * @param message             推送的消息
     * @param customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onMessage(Context context, String message, String customContentString) {
        if (TextUtils.isEmpty(message) && TextUtils.isEmpty(customContentString)) {
            Log.e(TAG, "透传消息: message=null, customContentString=null");
            return;
        }
        if (BaiduManager.sMessageProvider != null) {
            ZPushMessage msg = new ZPushMessage();
            msg.setContent(message);
            msg.setCustomContent(customContentString);
            msg.setPlatform(BaiduManager.NAME);
            BaiduManager.sMessageProvider.onReceivePassThroughMessage(context, msg);
        }
    }


    /**
     * 通知点击
     *
     * @param context             上下文
     * @param title               推送的通知的标题
     * @param description         推送的通知的描述
     * @param customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {
        if (TextUtils.isEmpty(description) && TextUtils.isEmpty(customContentString)) {
            Log.e(TAG, "通知点击: description=null, customContentString=null");
            return;
        }
        if (BaiduManager.sMessageProvider != null) {
            ZPushMessage msg = new ZPushMessage();
            msg.setTitle(title);
            msg.setContent(description);
            msg.setCustomContent(customContentString);
            msg.setPlatform(BaiduManager.NAME);
            BaiduManager.sMessageProvider.onNotificationMessageClicked(context, msg);
        }
    }

    /**
     * 收到通知
     *
     * @param context             上下文
     * @param title               推送的通知的标题
     * @param description         推送的通知的描述
     * @param customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationArrived(Context context, String title, String description, String customContentString) {
        if (TextUtils.isEmpty(description) && TextUtils.isEmpty(customContentString)) {
            Log.e(TAG, "通知到达: description=null, customContentString=null");
            return;
        }
        if (BaiduManager.sMessageProvider != null) {
            ZPushMessage msg = new ZPushMessage();
            msg.setTitle(title);
            msg.setContent(description);
            msg.setCustomContent(customContentString);
            msg.setPlatform(BaiduManager.NAME);
            BaiduManager.sMessageProvider.onNotificationMessageArrived(context, msg);
        }
    }
}
