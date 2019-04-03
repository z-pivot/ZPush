### 1、项目的build.gradle
    如果使用个推需要设置maven
    ```
    allprojects {
        repositories {
            google()
            jcenter()
            maven {
                url "http://mvn.gt.igexin.com/nexus/content/repositories/releases/"
            }
        }
    }
    ```
### 2、app的build.gradle
  * 添加依赖
  ```
  //lib-core必须依赖
  implementation 'com.github.z-pivot.ZPush:lib-core:1.0.1'
  //选择使用的平台依赖
  implementation 'com.github.z-pivot.ZPush:lib-jpush:1.0.1'
  implementation 'com.github.z-pivot.ZPush:lib-getui:1.0.1'
  implementation 'com.github.z-pivot.ZPush:lib-baidu:1.0.1'
  implementation 'com.github.z-pivot.ZPush:lib-xinge:1.0.1'
  ```
  * 设置必须的参数
  ```
  manifestPlaceholders = [
                  //必须参数
                  PACKAGE_NAME: applicationId,
                  /* 个推
                  GETUI_APP_ID : "12PuCZy4ov9PERRt0F4GT7",
                  GETUI_APP_KEY : "Hb6u7bT6rL9ttPXmhowMr1",
                  GETUI_APP_SECRET : "V0lrA7f4KP9nnIuyqxKC26"
                  */
                  //极光JPush
                  JPUSH_PKGNAME : applicationId,
                  JPUSH_APPKEY : "a13f1062e045150f67a2349e", //JPush 上注册的包名对应的 Appkey.
                  JPUSH_CHANNEL : "developer-default" //暂时填写默认值即可.
                  /*百度云推送
                  BAIDU_APIKEY:"aAY24FNKHxIahv6NegqVCyyi"
                  */
                  /*腾讯移动推送（信鸽）
                  XG_ACCESS_ID : "2100330744",
                  XG_ACCESS_KEY: "AJN7T4S7S65N"
                  */
          ]
  ```
### 3、编写自定义推送消息服务类
```
public class PushIntentService extends ZPushIntentService {
    @Override
    public void onReceivePassThroughMessage(ZPushMessage message) {
        Log.e(TAG, "收到透传消息 -> " + message.getPlatform());
        Log.e(TAG, "收到透传消息 -> " + message.getContent());
    }

    @Override
    public void onNotificationMessageClicked(ZPushMessage message) {
        Log.e(TAG, "通知栏消息点击 -> " + message.getPlatform());
        Log.e(TAG, "通知栏消息点击 -> " + message.getContent());
    }
}
```
### 4、Application设置
```
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
```