package com.drkj.foxconn.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库创建
 * Created by VeronicaRen on 2018/1/30.
 */
public class SqlHelper extends SQLiteOpenHelper {

    public SqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConstant.CREATE_TABLE_REGION);
        db.execSQL(DbConstant.CREATE_TABLE_EQUIPMENT);
        db.execSQL(DbConstant.CREATE_TABLE_EQUIPMENTATTRIBUTE);
        db.execSQL(DbConstant.CREATE_TABLE_EQUIPMENT_FAULT);
        db.execSQL(DbConstant.CREATE_TABLE_EQUIPMENT_FAULT_PICTURE_LIST);
        db.execSQL(DbConstant.CREATE_TABLE_FEEDBACK);
        db.execSQL(DbConstant.CREATE_TABLE_FEEDBACK_PICTURE_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
