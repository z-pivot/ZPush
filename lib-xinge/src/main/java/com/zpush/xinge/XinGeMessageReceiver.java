package com.zpush.xinge;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;
import com.zpush.core.ZPushMessage;


/**
 * Author      : BWj
 * Date        : 2019/3/29
 * Description : 腾讯移动推送（信鸽）消息处理类
 */
public class XinGeMessageReceiver extends XGPushBaseReceiver {
    private final String TAG = "XinGeMessageReceiver";

    /**
     * 注册回调
     *
     * @param context         APP上下文对象
     * @param errorCode       错误码，{@link XGPushBaseReceiver#SUCCESS} 0 - 表示成功，其它表示失败
     * @param registerMessage 返回的注册信息结构体
     */
    @Override
    public void onRegisterResult(Context context, int errorCode, XGPushRegisterResult registerMessage) {

    }

    /**
     * 反注册回调
     *
     * @param context   APP上下文对象
     * @param errorCode 错误码，{@link XGPushBaseReceiver#SUCCESS} 0 - 表示成功，其它表示失败
     */
    @Override
    public void onUnregisterResult(Context context, int errorCode) {

    }

    /**
     * 设置标签回调
     *
     * @param context   APP上下文对象
     * @param errorCode 错误码，{@link XGPushBaseReceiver#SUCCESS} 0 - 表示成功，其它表示失败
     * @param tagName
     */
    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {

    }

    /**
     * 删除标签回调
     *
     * @param context   APP上下文对象
     * @param errorCode 错误码，{@link XGPushBaseReceiver#SUCCESS} 0 - 表示成功，其它表示失败
     * @param tagName
     */
    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {

    }

    /**
     * 消息透传
     *
     * @param context APP上下文对象
     * @param message 接收到消息结构体，其中XGPushTextMessage的方法列表如下：
     *                message.getTitle()		    消息标题（注意：从前台下发应用内消息字中的描述不属于标题）
     *                message.getContent()		    消息正文内容，通常只需要下发本字段即可
     *                message.getCustomContent()	消息自定义key-value
     */
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        if (TextUtils.isEmpty(message.getContent()) && TextUtils.isEmpty(message.getCustomContent())) {
            Log.e(TAG, "透传消息: content=null, customContent=null");
            return;
        }
        if (XinGeManager.sMessageProvider != null) {
            ZPushMessage msg = new ZPushMessage();
            msg.setTitle(message.getTitle());
            msg.setContent(message.getContent());
            msg.setCustomContent(message.getCustomContent());
            msg.setPlatform(XinGeManager.NAME);
            XinGeManager.sMessageProvider.onReceivePassThroughMessage(context, msg);
        }
    }


    /**
     * 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
     *
     * @param context APP上下文对象
     * @param message 通知被打开的对象，如果该activity是由信鸽的通知引起打开动作的，返回XGPushClickedResult，否则返回null。
     *                message.getTitle()		    通知标题
     *                message.getActivityName()		被打开的页面名称
     *                message.getContent()		    通知正文内容
     *                message.getCustomContent()	消息自定义key-value
     */
    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
        if (TextUtils.isEmpty(message.getContent()) && TextUtils.isEmpty(message.getCustomContent())) {
            Log.e(TAG, "通知点击: content=null, customContent=null");
            return;
        }
        if (XinGeManager.sMessageProvider != null && message.getActionType() == 0) {
            ZPushMessage msg = new ZPushMessage();
            msg.setTitle(message.getTitle());
            msg.setContent(message.getContent());
            msg.setCustomContent(message.getCustomContent());
            msg.setPlatform(XinGeManager.NAME);
            XinGeManager.sMessageProvider.onNotificationMessageClicked(context, msg);
        }
    }

    /**
     * 通知展示，主要控制推送来的信息在状态栏的展示，当然如果想自定义可以修改这个方法
     *
     * @param context APP上下文对象
     * @param message 被展示的通知对象其中 XGPushShowedResult 的方法列表如下：
     *                message.getTitle()		    通知标题
     *                message.getContent()		    通知正文内容
     *                message.getCustomContent()	消息自定义key-value
     */
    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult message) {
        if (TextUtils.isEmpty(message.getContent()) && TextUtils.isEmpty(message.getCustomContent())) {
            Log.e(TAG, "通知到达: content=null, customContent=null");
            return;
        }
        if (XinGeManager.sMessageProvider != null) {
            ZPushMessage msg = new ZPushMessage();
            msg.setTitle(message.getTitle());
            msg.setContent(message.getContent());
            msg.setCustomContent(message.getCustomContent());
            msg.setPlatform(XinGeManager.NAME);
            XinGeManager.sMessageProvider.onNotificationMessageArrived(context, msg);
        }
    }
}
