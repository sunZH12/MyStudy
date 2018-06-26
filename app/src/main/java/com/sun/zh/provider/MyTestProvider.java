package com.sun.zh.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * 内容提供者的测试类
 * Created by sunZH on 2018-5-26.
 */

public class MyTestProvider extends AndroidTestCase {


    private String TAG = "test";

    /**
     * 测试添加数据
     */
    public void testInsert() {
        //创建内容解析者对象
        ContentResolver contentResolver = this.getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.sun.zh.provider/person");
        ContentValues values = new ContentValues();
        values.put("name", "小明");
        values.put("balance", 1300);
        Uri insert = contentResolver.insert(uri, values);

        Log.d(TAG, " " + insert);
    }

    /**
     * 测试删除数据
     */
    public void testDelete() {
        ContentResolver contentResolver = this.getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.sun.zh.provider/person");
        int count = contentResolver.delete(uri, "id=?", new String[]{3 + ""});
        Log.d(TAG, "删除了" + count + "行");
    }

    /**
     * 测试更新数据
     */
    public void testUpdate() {
        ContentResolver contentResolver = this.getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.sun.zh.provider/person");
        ContentValues values = new ContentValues();
        values.put("name", "小赵 update");
        values.put("balance", 56789);
        int update = contentResolver.update(uri, values, "id=?", new String[]{6 + ""});
        Log.d(TAG, "更新了" + update + "行");
    }

    /**
     * 查询数据
     */
    public void testQueryOne() {
        ContentResolver contentResolver = this.getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.sun.zh.provider/person");
        Cursor query = contentResolver.query(uri, new String[]{"name", "balance"}, "id =?", new String[]{101 + ""}, null);

        if (query.moveToNext()) {
            Log.d(TAG, query.getString(0));
            Log.d(TAG, "" + query.getInt(1));
        }

        query.close();
    }


    @Override
    protected void runTest() throws Throwable {
        super.runTest();
        testInsert();
    }
}
