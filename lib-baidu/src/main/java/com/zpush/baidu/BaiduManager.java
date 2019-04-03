package com.zpush.baidu;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.zpush.core.ZMessageProvider;
import com.zpush.core.ZPushManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : BWj
 * Date        : 2019/3/28
 * Description : 百度云推送管理类
 */
public class BaiduManager implements ZPushManager {
    public static final String NAME = "Baidu";
    public static ZMessageProvider sMessageProvider;

    @Override
    public void registerPush(Context context) {
        String apiKey = getApiKey(context);
        PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY, apiKey);
    }

    @Override
    public void unRegisterPush(Context context) {
        PushManager.stopWork(context);
    }

    @Override
    public void setAlias(Context context, String alias) {
    }

    @Override
    public void unsetAlias(Context context, String alias) {

    }

    @Override
    public void setTags(Context context, String... tags) {
        List<String> temps = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            temps.add(tags[i]);
        }
        PushManager.setTags(context, temps);
    }

    /*@Override
    public void unsetTags(Context context, String... tags) {
        List<String> temps = new ArrayList<>();
        if (tags.length > 0) {
            temps.add(tags[0]);
        }
        PushManager.setTags(context, temps);
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

    /**
     * 获取api_key
     * AndroidManifest.xml中设置了 <meta-data android:name="apiKey" android:value="${BAIDU_APIKEY}"/>
     * 使用时在module的build.gradle中的manifestPlaceholders里添加BAIDU_APIKEY即可
     */
    private String getApiKey(Context context) {
        String value = "";
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = applicationInfo.metaData.getString("apiKey");
        } catch (Exception e) {
            value = "";
        }
        return value;
    }
}
