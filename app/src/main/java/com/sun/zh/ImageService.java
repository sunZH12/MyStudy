package com.sun.zh;

import android.text.Html;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sunZH on 2018-6-2.
 */

public class ImageService {

    // 回调函数使用
    public static final ImageService imageService = new ImageService();

    //固定五个线程来执行任务
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    private ImageService() {
    }

    public static ImageService getImageService() {
        return imageService;
    }


}
