package com.zpush.core;

import android.content.Context;

public interface ZPushManager {

    /**
     * 初始化服务、注册
     *
     * @param context 上下文
     */
    void registerPush(Context context);

    /**
     * 关闭推送
     *
     * @param context 上下文
     */
    void unRegisterPush(Context context);

    /**
     * 绑定别名
     *
     * @param context 上下文
     * @param alias   别名
     */
    void setAlias(Context context, String alias);

    /**
     * 解绑别名
     *
     * @param context 上下文
     * @param alias   别名
     */
    void unsetAlias(Context context, String alias);

    /**
     * 设置标签
     *
     * @param context
     * @param tags
     */
    void setTags(Context context, String... tags);

//    void unsetTags(Context context, String... tags);

//    void setToken(String token);

    /**
     * 获取平台的名称
     *
     * @return 名称
     */
    String getName();

    void setMessageProvider(ZMessageProvider provider);

    /**
     * 如果从小米推送->小米&个推，所以上线新版可能会导致收到2个平台的推送，所以没有用得平台必须让其失效（取消注册）。
     */
    void disable(Context context);

}
