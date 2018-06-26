package com.sun.zh.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by sunZH on 2018-5-26.
 */

public class MyContentProvider extends ContentProvider {

    private static final int PERSON = 1;    // 匹配码
    private static final int STUDENT = 2;            // 匹配码
    private static final int PERSON_ID = 3;        // 匹配码

    private MyDBHelper helper;

    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        helper = new MyDBHelper(getContext());
        //添加Uri匹配模式，设置匹配码（参数3）Uri如果匹配就会返回相应的匹配码
        uriMatcher.addURI("com,sun.zh.provider", "person", PERSON);
        uriMatcher.addURI("com.sun.zh.provider", "#", PERSON_ID);
        uriMatcher.addURI("com.sun.zh.provider", "student", STUDENT);  //#表示匹配数字，*表示匹配文本

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case PERSON_ID:
                //根据ID查询
                long parseID = ContentUris.parseId(uri);    //获取传过来的ID
                selection = "id = ?";
                projection = new String[]{parseID + ""};
            case PERSON:
                Cursor cursor = db.query("person", projection, selection, selectionArgs, null, null, sortOrder);
                // 注意：此处的 db与cursor不能关闭 ==
                return cursor;
            default:
                throw new IllegalArgumentException(String.format("Uri：%s 不是合法的uri地址", uri));

        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = helper.getWritableDatabase();   //获取数据库对象

        switch (uriMatcher.match(uri)) { // 匹配uri
            case PERSON:
                long id = db.insert("person", "id", contentValues);
                db.close();
                return ContentUris.withAppendedId(uri, id);
            case STUDENT:
                long inset = db.insert("student", "id", contentValues);
                db.close();
                //在原uri上拼上id,生成新的uri
                return ContentUris.withAppendedId(uri, inset);

            default:
                throw new IllegalArgumentException(String.format("\"Uri：%s 不是合法的uri地址\", uri"));
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case PERSON_ID:
                //获取传过来的ID
                long parseID = ContentUris.parseId(uri);
                selection = "id=?"; //设置查询条件
                selectionArgs = new String[]{parseID + ""};  // 查询条件值
            case PERSON:
                int delete = db.delete("person", selection, selectionArgs);
                db.close();
                return delete;
            default:
                throw new IllegalArgumentException(String.format("Uri：%s 不是合法的uri地址", uri));
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case PERSON_ID:
                long parseId = ContentUris.parseId(uri);                // 获取传过来的ID值
                selection = "id=?";                                    // 设置查询条件
                selectionArgs = new String[]{parseId + ""};            // 查询条件值
            case PERSON:
                int update = db.update("person", contentValues, selection, selectionArgs);
                db.close();
                return update;
            default:
                throw new IllegalArgumentException(String.format("Uri：%s 不是合法的uri地址", uri));

        }
    }

    /**
     * 返回传入URI的类型，可用于测试URI是否正确
     *
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case PERSON_ID:
                return "vnd.android.cursor.item/person";        // 表示单条person记录
            case PERSON:
                return "vnd.android.cursor.dir/person";        // 表单多个person记录
            default:
                return null;
        }
    }
}
