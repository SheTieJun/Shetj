package com.shetj.diyalbume.test;

import me.shetj.fresco.FrescoImageLoader;
import me.shetj.fresco.ImageLoader;

/**
 * 工厂？
 * 策略？
 * 模板？
 */
public class ImageLoaderFactory {

    private static Class<? extends ImageLoader> sPlayerManager;

    public static void setPlayManager(Class<? extends ImageLoader> playManager) {
        sPlayerManager = playManager;
    }

    public static ImageLoader getPlayManager() {
        if (sPlayerManager == null) {
            sPlayerManager = FrescoImageLoader.class;
        }
        try {
            return sPlayerManager.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}