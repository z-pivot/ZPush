package com.zpush.jpush;

import android.content.Context;

import com.zpush.core.ZMessageProvider;
import com.zpush.core.ZPushManager;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Author      : BWj
 * Date        : 2019/3/27
 * Description : 极光推送管理类
 */
public class JPushManager implements ZPushManager {
    public static final String NAME = "JPush";
    public static ZMessageProvider sMessageProvider;
    /**
     * 用户自定义的操作序列号，同操作结果一起返回，用来标识一次操作的唯一性。
     */
    public static final int sequence = 5050;
    @Override
    public void registerPush(Context context) {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);

    }

    @Override
    public void unRegisterPush(Context context) {
        JPushInterface.stopPush(context);
    }

    @Override
    public void setAlias(Context context, String alias) {
        JPushInterface.setAlias(context,sequence,alias);
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        JPushInterface.deleteAlias(context,sequence);
    }

    @Override
    public void setTags(Context context, String... tags) {
        Set<String> temps = new HashSet<String>();
        for (int i = 0; i < tags.length; i++) {
            temps.add(tags[i]);
        }
        JPushInterface.setTags(context,sequence,temps);
    }

    /*@Override
    public void unsetTags(Context context, String... tags) {
        Set<String> temps = new HashSet<String>();
        if (tags.length>=1){
            temps.add(tags[0]);
        }
        JPushInterface.setTags(context,sequence,temps);
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
