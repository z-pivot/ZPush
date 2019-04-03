package com.zpush.getui;

import android.content.Context;

import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.zpush.core.ZMessageProvider;
import com.zpush.core.ZPushManager;


public class GeTuiManager implements ZPushManager {
    public static final String NAME = "GeTui";

    public static ZMessageProvider sMessageProvider;

    @Override
    public void registerPush(Context context) {
        // 初始化服务
        PushManager.getInstance().initialize(context, null);
        // 注册消息接收服务
        PushManager.getInstance().registerPushIntentService(context, GeTuiMessageIntentService.class);
    }

    @Override
    public void unRegisterPush(Context context) {
        // 停止SDK服务
        PushManager.getInstance().stopService(context);
    }


    @Override
    public void setAlias(Context context, String alias) {
        PushManager.getInstance().bindAlias(context, alias);
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        PushManager.getInstance().unBindAlias(context, alias, false);
    }

    @Override
    public void setTags(Context context, String... tags) {
        Tag[] temps = new Tag[tags.length];
        for (int i = 0; i < tags.length; i++) {
            Tag tag = new Tag();
            tag.setName(tags[i]);
            temps[i] = tag;
        }
        //设置标签
        //标签的设定，一定要在获取到 Clientid 之后才可以设定。标签的设定，服务端限制一天只能成功设置一次
        PushManager.getInstance().setTag(context, temps, null);
    }

   /* @Override
    public void unsetTags(Context context, String... tags) {
        PushManager.getInstance().setTag(context, new Tag[0], null);
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
