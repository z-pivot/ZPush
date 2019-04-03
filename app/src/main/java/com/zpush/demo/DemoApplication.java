package com.zpush.demo;

import android.app.Application;

import com.zpush.core.ZPushClient;
import com.zpush.jpush.JPushManager;


public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initPush();
    }

    private void initPush() {
        //个推
        //ZPushClient.addPushManager(new GeTuiManager());
        //ZPushClient.setUsePushName(GeTuiManager.NAME);

        //JPush极光
        ZPushClient.addPushManager(new JPushManager());
        ZPushClient.setUsePushName(JPushManager.NAME);

        //百度
        //ZPushClient.addPushManager(new BaiduManager());
        //ZPushClient.setUsePushName(BaiduManager.NAME);

        //信鸽（腾讯TPush）
        //ZPushClient.addPushManager(new XingeManager());
        //ZPushClient.setUsePushName(XingeManager.NAME);

        // 配置接收推送消息的服务类
        ZPushClient.setPushIntentService(PushIntentService.class);

        // 注册推送
        ZPushClient.registerPush(this);
    }
}
