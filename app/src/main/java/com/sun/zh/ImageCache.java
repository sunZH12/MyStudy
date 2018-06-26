package com.sun.zh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.WeakHashMap;

/**
 * Created by sunZH on 2018-6-2.
 */

public class ImageCache extends WeakHashMap<String, Bitmap> {

    private static final String CACHE_FILE = "/cache";  //本地的缓存文件
    public static ImageCache imageCache;
    private boolean cacheFileIsExit;

    private ImageCache() {
    }

    public static ImageCache getImageCache() {
        if (imageCache == null) {
            synchronized (ImageCache.class) {
                if (imageCache == null) {
                    imageCache = new ImageCache();
                }
            }
        }
        return imageCache;
    }

    /**
     * 判断图片是否存在，首先判断内存中是否存在然后再判断本地是否存在
     */
    public boolean isBitmapExit(String url) {
        //有url作为key缓存到内存，判断该值是否存在
        boolean isEixt = containsKey(url);
        if (false == isEixt) {
            isEixt = isLocalHasBmp(url);
        }
        return isEixt;
    }

    /**
     * 判断本地是否有
     *
     * @param url
     * @return
     */
    private boolean isLocalHasBmp(String url) {
        boolean isEixt = true;

        String name = changeUrlToName(url);
        String filePath = isCacheFileIsExit();

        File file = new File(filePath, name);

        if (file.exists()) {
            //本地文件存并且缓存到内存中
            isEixt = caheBmpToMemory(file, url);
        } else {
            isEixt = false;
        }

        return isEixt;
    }

    /**
     * 将本地图片缓存到内存中
     *
     * @param file
     * @param url
     * @return
     */
    private boolean caheBmpToMemory(File file, String url) {
        boolean sucessed = true;
        InputStream inputStream = null;
        try {
            //获取图片的字节流
            inputStream = new FileInputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            sucessed = false;
        }

        byte[] bs = getBytesFromStream(inputStream);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);

        if (bitmap == null) {
            return false;
        }
        this.put(url, bitmap, false);
        return sucessed;
    }

    /**
     * 将字节流转换为字节数组
     *
     * @param inputStream
     * @return
     */
    private byte[] getBytesFromStream(InputStream inputStream) {
        boolean b2 = true;  // 若抛出异常则停止循环
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while (len == -1 && b2) {
            try {
                len = inputStream.read(b);
                if (len != -1) {
                    baos.write(b, 0, len);
                }
            } catch (IOException e) {
                b2 = false;
                try {
                    //将输入流关闭
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }


    /**
     * 将url变成图片的地址
     */
    private String changeUrlToName(String url) {

        String name = url.replaceAll(":", "_");

        name = name.replaceAll("//", "_");
        name = name.replaceAll("/", "_");
        name = name.replaceAll("=", "_");
        name = name.replaceAll(",", "_");
        name = name.replaceAll("&", "_");
        return name;
    }


    /**
     * 判断缓存文件夹是否存在如果存在怎返回文件夹路径，
     * 如果不存在新建文件夹并返回文件夹路径
     *
     * @return
     */
    public String isCacheFileIsExit() {
        String filePath = "";
        String rootPath = "";   //本地目录

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rootPath = Environment.getExternalStorageDirectory().toString();
        }
        filePath = rootPath + CACHE_FILE;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        return filePath;
    }

    @Override
    public Bitmap put(String key, Bitmap value) {

        String filePath = isCacheFileIsExit();
        String name = changeUrlToName(key);
        File file = new File(filePath, name);

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            //图片压缩，保留80%的原质
            value.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (null != outputStream)
                outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null != outputStream) {

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = null;
        }

        return super.put(key, value);
    }

    /**
     * @param key
     * @param value
     * @param isCachToLocal 是否缓存到本地
     * @return
     */
    public Bitmap put(String key, Bitmap value, boolean isCachToLocal) {
        if (isCachToLocal) {
            return this.put(key, value);
        } else {
            return super.put(key, value);
        }
    }
}
