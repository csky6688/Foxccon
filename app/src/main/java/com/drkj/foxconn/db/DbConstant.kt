package com.drkj.foxconn.db

/**
 * SQLite语句
 * Created by VeronicaRen on 2018/1/30.
 */
object DbConstant {

    const val DB_NAME = "foxconn.db"

    const val DB_VERSION = 1

    const val TABLE_REGION = "region_list"

    // 1 楼栋 2 楼层 3 机房
    const val TYPE_BUILDING = "1"

    const val TYPE_STOREY = "2"

    const val TYPE_ROOM = "3"

    const val NFC_HEAD = "XJ"

    const val CREATE_TABLE_REGION = "create table $TABLE_REGION" +
            "(_id int auto_increment primary key," +
            "code text," +
            "createBy text," +
            "createDate text," +
            "createName text," +
            "id text," +
            "name text," +
            "nfcCode text," +
            "parentId text," +
            "updateBy text," +
            "updateDate text," +
            "updateName text," +
            "type text)"

    const val TABLE_EQUIPMENT = "equipment_list"

    const val CREATE_TABLE_EQUIPMENT = "create table $TABLE_EQUIPMENT" +
            "(_id int auto_increment primary key," +
            "buildingId text," +
            "code text," +
            "createBy text," +
            "createDate text," +
            "createName text," +
            "id text," +
            "name text," +
            "nfcCode text," +
            "roomId text," +
            "storeyId text," +
            "isCheck text," +//TODO 非服务器数据，上传时记得设置为null
            "type text)"

    const val TABLE_EQUIPMENTATTRIBUTE = "equipmentAttribute_list"

    const val CREATE_TABLE_EQUIPMENTATTRIBUTE = "create table $TABLE_EQUIPMENTATTRIBUTE" +
            "(_id int auto_increment primary key," +
            "createBy text," +
            "createDate text," +
            "createName text," +
            "equipmentId text," +
            "id text," +
            "max float," +
            "min float," +
            "name text," +
            "rating float," +
            "value float," +
            "type text," +
            "updateBy text," +
            "updateName text)"

    const val TABLE_EQUIPMENT_FAULT = "equipment_fault"

    const val CREATE_TABLE_EQUIPMENT_FAULT = "create table $TABLE_EQUIPMENT_FAULT" +
            "(_id int auto_increment primary key," +
            "content text," +
            "createBy text," +
            "createDate text," +
            "createName text," +
            "equipmentCode text," +
            "equipmentId text," +
            "equipmentName text," +
            "id text," +
            "location text," +//TODO 非服务器数据，上传时设置为null
            "type text," +
            "updateBy text," +
            "updateDate text," +
            "updateName text" +
            ")"

    const val TABLE_EQUIPMENT_FAULT_PICTURE_LIST = "equipment_fault_picture_list"

    const val CREATE_TABLE_EQUIPMENT_FAULT_PICTURE_LIST = "create table $TABLE_EQUIPMENT_FAULT_PICTURE_LIST" +
            "(_id int auto_increment primary key," +
            "createBy text," +
            "createDate text," +
            "createName text," +
            "equipmentFeedbackId text," +
            "id text," +
            "path text," +//TODO 非服务器数据，上传时记得设置为null
            "picture text," +
            "updateBy text," +
            "updateDate text," +
            "updateName text" +
            ")"

    const val TABLE_FEEDBACK = "feedback"

    const val CREATE_TABLE_FEEDBACK = "create table $TABLE_FEEDBACK" +
            "(_id int auto_increment primary key," +
            "content text," +
            "createBy text," +
            "createDate text," +
            "createName text," +
            "id text," +
            "location text," +
            "regionCode text," +
            "regionId text," +
            "regionName text," +
            "type text," +
            "updateBy text," +
            "updateDate text," +
            "updateName text" +
            ")"

    const val TABLE_FEEDBACK_PICTURE_LIST = "feedback_picture_list"

    const val CREATE_TABLE_FEEDBACK_PICTURE_LIST = "create table $TABLE_FEEDBACK_PICTURE_LIST" +
            "(_id int auto_increment primary key," +
            "createBy text," +
            "createDate text," +
            "createName text," +
            "id text," +
            "localFeedbackId text," +
            "picture text," +
            "path text," +//TODO 非服务器数据，上传时记得设置为null
            "updateBy text," +
            "updateDate text," +
            "updateName text" +
            ")"
}
