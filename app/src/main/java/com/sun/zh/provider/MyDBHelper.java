package com.sun.zh.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sunZH on 2018-5-26.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    private static String name = "mydb.db"; //数据库的名称
    private static int version = 1; // 数据库的版本号

    public MyDBHelper(Context context) {
        super(context, name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //当数据库创建的时候，第一次被直线，完成对数据库的表的创建

        //创建数据库的sql语句
        String sql = "create table person(id integer primary key autoincrement," +
                "name varchar(64),address varchar(64))";

        sqLiteDatabase.execSQL(sql);    // 完成数据库的创建
    }

    /**
     * onUpgrade() 方法是在什么时候被执行呢？
     * 当数据库需要升级时调用这个方法[在开发过程中涉及到数据库的设计存在缺陷的时候进行升级，不会损坏原来的数据]，
     * 这种实现方式会使用方法来减少表，或者增加表，或者做版本更新的需求。
     * 在这里就可以执行 SQLite Alter语句了，你可以使用 ALTER TABLE 来增加新的列插入到一张表中，你可以使用 ALTER TABLE 语句来重命名列或者移除列，或者重命名旧的表。
     * 你也可以创建新的表然后将旧表的内容填充到新表中。
     * 此方法会将事务之内的事件一起执行，如果有异常抛出，任何改变都会自动回滚操作。
     * 参数：
     * db ： 数据库
     * oldVersion ： 旧版本数据库
     * newVersion ： 新版本数据库
     * 【注意】：这里的删除等操作必须要保证新的版本必须要比旧版本的版本号要大才行。[即 Version 2.0 > Version 1.0 ] 所以这边我们不需要对其进行操作。
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "alter table person add sex varchar(8)";
        sqLiteDatabase.execSQL(sql);
    }


}
