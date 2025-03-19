package com.fit;

import ninja.standalone.NinjaJetty;
import ninja.utils.NinjaConstant;

/**
 * @AUTO 启动入口
 * @Author AIM
 * @DATE 2025/3/18
 */
public class GuiceWeb {

    public static void main(String[] args) {
        String profile = System.getProperty("ninja.mode");
        // 设置 Ninja 运行模式
        if (profile != null) {
            System.setProperty(NinjaConstant.MODE_KEY_NAME, profile);
        } else {
            System.setProperty(NinjaConstant.MODE_KEY_NAME, NinjaConstant.MODE_DEV);
        }
        NinjaJetty ninjaJetty = new NinjaJetty();
        ninjaJetty.port(8080);
        ninjaJetty.run();
    }
}