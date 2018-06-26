package com.sun.zh;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.util.LinkedHashMap;

/**
 * Created by sunZH on 2018-6-21.
 */

public class BitmapLruCache extends LruCache<String,Bitmap> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }



}
