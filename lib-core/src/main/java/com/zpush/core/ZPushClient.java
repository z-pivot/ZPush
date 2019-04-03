package com.zpush.core;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ZPushClient {


    private static Map<String, ZPushManager> sPushManagerMap = new HashMap<>();
    private static String sUsePushName;
//    private static Selector sSelector;
    private static String sReceiverPermission = null;// 避免被其它APP接收
    private static Class<? extends ZPushIntentService> sPushIntentServiceClass;


    private ZPushClient() {

    }

    public static void setPushIntentService(Class<? extends ZPushIntentService> mixPushIntentServiceClass) {
        ZPushClient.sPushIntentServiceClass = mixPushIntentServiceClass;
    }

   /* public static void setSelector(Selector selector) {
        sSelector = selector;
        sUsePushName = sSelector.select(sPushManagerMap, Build.BRAND);
    }*/

    public static String getUsePushName() {
        return sUsePushName;
    }

    public static void addPushManager(ZPushManager pushAdapter) {
        sPushManagerMap.put(pushAdapter.getName(), pushAdapter);
        pushAdapter.setMessageProvider(mMessageProvider);
    }

    public static void registerPush(Context context) {

        sReceiverPermission = context.getPackageName() + ".permission.ZPUSH_RECEIVE";

        Set<String> keys = sPushManagerMap.keySet();
        for (String key : keys) {
            if (key.equals(sUsePushName)) {
                sPushManagerMap.get(key).registerPush(context);
            } else {
                sPushManagerMap.get(key).unRegisterPush(context);
            }
        }
    }
    private static ZPushManager getPushManager(){
        if (sUsePushName == null){
            throw new RuntimeException("you need setSelector or setUsePushName");
        }
        return sPushManagerMap.get(sUsePushName);
    }

    public static void unRegisterPush(Context context) {
        getPushManager().unRegisterPush(context);
    }

    public static void setUsePushName(String sUsePushName) {
        ZPushClient.sUsePushName = sUsePushName;
    }

    public static void setAlias(Context context, String alias) {
        getPushManager().setAlias(context, alias);
    }

    public static void unsetAlias(Context context, String alias) {
        getPushManager().unsetAlias(context, alias);
    }

    public static void setTags(Context context, String... tags){
        getPushManager().setTags(context, tags);
    }

/*    public static void unsetTags(Context context, String... tags){
        getPushManager().unsetTags(context, tags);
    }*/

    /*public static class Selector {
        public String select(Map<String, ZPushManager> pushAdapterMap, String brand) {
            if (pushAdapterMap.containsKey("meizuPush") && brand.equalsIgnoreCase("meizu")) {
                return "meizuPush";
            } else if (pushAdapterMap.containsKey("mipush") && brand.equalsIgnoreCase("xiaomi")) {
                return "mipush";
            } else if (pushAdapterMap.containsKey("getui")) {
                return "getui";
            }
            return null;
        }
    }*/

    private static ZMessageProvider mMessageProvider = new ZMessageProvider() {
        @Override
        public void onReceivePassThroughMessage(Context context, ZPushMessage message) {
//            message.setNotify(0);
            Intent intent = new Intent(ZPushMessageReceiver.RECEIVE_THROUGH_MESSAGE);
            intent.putExtra("message", message);
            context.sendBroadcast(intent, sReceiverPermission);
            Log.d("onReceivePassThrough", message.getContent());

            if (sPushIntentServiceClass != null){
                intent.setClass(context,sPushIntentServiceClass);
                context.startService(intent);
            }
        }

        @Override
        public void onNotificationMessageClicked(Context context, ZPushMessage message) {
//            message.setNotify(1);
            Intent intent = new Intent(ZPushMessageReceiver.NOTIFICATION_CLICKED);
            intent.putExtra(ZPushMessageReceiver.MESSAGE, message);
            context.sendBroadcast(intent, sReceiverPermission);
            Log.d("onNotificationClicked", message.getContent());

            if (sPushIntentServiceClass != null){
                intent.setClass(context,sPushIntentServiceClass);
                context.startService(intent);
            }
        }

        @Override
        public void onNotificationMessageArrived(Context context, ZPushMessage message) {
            Intent intent = new Intent(ZPushMessageReceiver.NOTIFICATION_ARRIVED);
            intent.putExtra("message", message);
            context.sendBroadcast(intent, sReceiverPermission);
            Log.d("onNotificationArrived", message.getContent());

            if (sPushIntentServiceClass != null){
                intent.setClass(context,sPushIntentServiceClass);
                context.startService(intent);
            }
        }
    };
}
