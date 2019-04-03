package com.zpush.xinge;

import android.content.Context;
import android.util.Log;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.zpush.core.ZMessageProvider;
import com.zpush.core.ZPushManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Author      : BWj
 * Date        : 2019/3/29
 * Description : 腾讯移动推送（信鸽）管理类
 */
public class XinGeManager implements ZPushManager {
    public static final String NAME = "XinGe";
    public static ZMessageProvider sMessageProvider;

    @Override
    public void registerPush(Context context) {
        XGPushConfig.enableDebug(context, true);
        XGPushManager.registerPush(context, new XGIOperateCallback() {
            /**
             * 操作成功时的回调。
             * @param data 操作成功的业务数据，如注册成功时的token信息等。
             * @param flag 标记码
             */
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.d("TPush", "注册成功，设备token为：" + data);
            }

            /**
             * 操作失败时的回调
             * @param data 操作失败的业务数据
             * @param errCode 错误码
             * @param msg 错误信息
             */
            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.e("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
        //注意在3.2.2 版本信鸽对账号绑定和解绑接口进行了升级具体详情请参考API文档。
        XGPushManager.bindAccount(context.getApplicationContext(), "XINGE");
    }

    @Override
    public void unRegisterPush(Context context) {
        XGPushManager.unregisterPush(context);
    }

    @Override
    public void setAlias(Context context, String alias) {
    }

    @Override
    public void unsetAlias(Context context, String alias) {

    }

    @Override
    public void setTags(Context context, String... tags) {
        Set<String> temps = new HashSet<String>();
        for (int i = 0; i < tags.length; i++) {
            temps.add(tags[i]);
        }
        XGPushManager.setTags(context, NAME, temps);
    }

   /* @Override
    public void unsetTags(Context context, String... tags) {
        Set<String> temps = new HashSet<String>();
        for (int i = 0; i < tags.length; i++) {
            temps.add(tags[i]);
        }
        XGPushManager.setTags(context, NAME, temps);
    }*/

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void setMessageProvider(ZMessageProvider provider) {
        sMessageProvider = provider;
    }

    @Override
    public void disable(Context context) {
        unRegisterPush(context);
    }
}
