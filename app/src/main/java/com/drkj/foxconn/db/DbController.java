package com.drkj.foxconn.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.drkj.foxconn.App;
import com.drkj.foxconn.bean.EndTaskBean;
import com.drkj.foxconn.bean.EquipmentFaultBean;
import com.drkj.foxconn.bean.EquipmentResultBean;
import com.drkj.foxconn.bean.FeedbackBean;
import com.drkj.foxconn.bean.RegionResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VeronicaRen on 2018/2/26.
 */
public class DbController {
    private static DbController instance;
    private SqlHelper sqlHelper;

    private DbController() {
        sqlHelper = new SqlHelper(App.getInstance(), DbConstant.DB_NAME, null, DbConstant.DB_VERSION);
    }

    public static DbController getInstance() {
        if (instance == null) {
            instance = new DbController();
        }
        return instance;
    }

    public void saveRegionInfo(RegionResultBean bean) {
        Log.e("tag", "saveRegionInfo");

        if (bean == null || bean.getData() == null)
            return;


        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        for (RegionResultBean.DataBean dataBean : bean.getData()) {
            ContentValues values = new ContentValues();
            values.put("code", dataBean.getCode());
            values.put("createBy", dataBean.getCreateBy());
            values.put("createDate", dataBean.getCreateDate());
            values.put("createName", dataBean.getCreateName());
            values.put("id", dataBean.getId());
            values.put("name", dataBean.getName());
            values.put("nfcCode", dataBean.getNfcCode());
            values.put("parentId", dataBean.getParentId());
            values.put("updateBy", dataBean.getUpdateBy());
            values.put("updateDate", dataBean.getUpdateDate());
            values.put("updateName", dataBean.getUpdateName());
            values.put("type", dataBean.getType());
            if (queryRegionById(dataBean.getId()) > 0) {
                db.update(DbConstant.TABLE_REGION, values, "id=?", new String[]{dataBean.getId()});
            } else {
                db.insert(DbConstant.TABLE_REGION, null, values);
            }
        }
    }

    public void saveEquipment(EquipmentResultBean bean) {
        if (bean == null || bean.getData() == null)
            return;
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        for (EquipmentResultBean.DataBean dataBean : bean.getData()) {
            ContentValues values = new ContentValues();
            values.put("buildingId", dataBean.getBuildingId());
            values.put("code", dataBean.getCode());
            values.put("createBy", dataBean.getCreateBy());
            values.put("createDate", dataBean.getCreateDate());
            values.put("createName", dataBean.getCreateName());
            values.put("id", dataBean.getId());
            values.put("name", dataBean.getName());
            values.put("nfcCode", dataBean.getNfcCode());
            values.put("roomId", dataBean.getRoomId());
            values.put("storeyId", dataBean.getStoreyId());
            values.put("type", dataBean.getType());
            values.put("isCheck", "false");//默认未巡检
            if (queryEquipmentById(dataBean.getId()) > 0) {
                db.update(DbConstant.TABLE_EQUIPMENT, values, "id=?", new String[]{dataBean.getId()});
                db.delete(DbConstant.TABLE_EQUIPMENTATTRIBUTE, "equipmentId=?", new String[]{dataBean.getId()});
            } else {
                db.insert(DbConstant.TABLE_EQUIPMENT, null, values);
            }
            for (EquipmentResultBean.DataBean.EquipmentAttributeListBean listBean : dataBean.getEquipmentAttributeList()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("createBy", listBean.getCreateBy());
                contentValues.put("createDate", listBean.getCreateDate());
                contentValues.put("createName", listBean.getCreateName());
                contentValues.put("equipmentId", listBean.getEquipmentId());
                contentValues.put("id", listBean.getId());
                contentValues.put("max", listBean.getMax());
                contentValues.put("min", listBean.getMin());
                contentValues.put("name", listBean.getName());
                contentValues.put("rating", listBean.getRating());
                contentValues.put("type", listBean.getType());
                contentValues.put("updateBy", listBean.getUpdateBy());
                contentValues.put("updateName", listBean.getUpdateName());
//                contentValues.put("value", listBean.getValue());
                db.insert(DbConstant.TABLE_EQUIPMENTATTRIBUTE, null, contentValues);
            }
        }
    }

    public EquipmentResultBean.DataBean queryEquipmentByNfcCode(String code) {
        EquipmentResultBean.DataBean bean = new EquipmentResultBean.DataBean();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstant.TABLE_EQUIPMENT, null, "nfcCode=?", new String[]{code}, null, null, null);
        if (cursor.moveToFirst()) {
            bean.setBuildingId(cursor.getString(cursor.getColumnIndex("buildingId")));
            bean.setCode(cursor.getString(cursor.getColumnIndex("code")));
            bean.setCreateBy(cursor.getString(cursor.getColumnIndex("createBy")));
            bean.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
            bean.setCreateName(cursor.getString(cursor.getColumnIndex("createName")));
            bean.setId(cursor.getString(cursor.getColumnIndex("id")));
            bean.setName(cursor.getString(cursor.getColumnIndex("name")));
            bean.setNfcCode(cursor.getString(cursor.getColumnIndex("nfcCode")));
            bean.setRoomId(cursor.getString(cursor.getColumnIndex("roomId")));
            bean.setStoreyId(cursor.getString(cursor.getColumnIndex("storeyId")));
            bean.setType(cursor.getString(cursor.getColumnIndex("type")));
            bean.setCheck(cursor.getString(cursor.getColumnIndex("isCheck")));
            Cursor cursor1 = db.query(DbConstant.TABLE_EQUIPMENTATTRIBUTE, null, "equipmentId=?", new String[]{bean.getId()}, null, null, null);
            List<EquipmentResultBean.DataBean.EquipmentAttributeListBean> list = new ArrayList<>();
            while (cursor1.moveToNext()) {
                EquipmentResultBean.DataBean.EquipmentAttributeListBean bean1 = new EquipmentResultBean.DataBean.EquipmentAttributeListBean();
                bean1.setCreateBy(cursor1.getString(cursor1.getColumnIndex("createBy")));
                bean1.setCreateDate(cursor1.getString(cursor1.getColumnIndex("createDate")));
                bean1.setCreateName(cursor1.getString(cursor1.getColumnIndex("createName")));
                bean1.setEquipmentId(cursor1.getString(cursor1.getColumnIndex("equipmentId")));
                bean1.setId(cursor1.getString(cursor1.getColumnIndex("id")));
                bean1.setMax(cursor1.getDouble(cursor1.getColumnIndex("max")));
                bean1.setMin(cursor1.getDouble(cursor1.getColumnIndex("min")));
                bean1.setName(cursor1.getString(cursor1.getColumnIndex("name")));
                bean1.setRating(cursor1.getDouble(cursor1.getColumnIndex("rating")));
                bean1.setType(cursor1.getString(cursor1.getColumnIndex("type")));
                bean1.setUpdateBy(cursor1.getString(cursor1.getColumnIndex("updateBy")));
                bean1.setUpdateName(cursor1.getString(cursor1.getColumnIndex("updateName")));
                bean1.setValue(cursor1.getDouble(cursor1.getColumnIndex("value")));
                list.add(bean1);
            }
            bean.setEquipmentAttributeList(list);
            cursor1.close();
        }
        cursor.close();
        return bean;
    }

    public List<EquipmentResultBean.DataBean> queryAllEquipment() {
        List<EquipmentResultBean.DataBean> dataBeans = new ArrayList<>();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstant.TABLE_EQUIPMENT, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            EquipmentResultBean.DataBean bean = new EquipmentResultBean.DataBean();
            bean.setBuildingId(cursor.getString(cursor.getColumnIndex("buildingId")));
            bean.setCode(cursor.getString(cursor.getColumnIndex("code")));
            bean.setCreateBy(cursor.getString(cursor.getColumnIndex("createBy")));
            bean.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
            bean.setCreateName(cursor.getString(cursor.getColumnIndex("createName")));
            bean.setId(cursor.getString(cursor.getColumnIndex("id")));
            bean.setName(cursor.getString(cursor.getColumnIndex("name")));
            bean.setNfcCode(cursor.getString(cursor.getColumnIndex("nfcCode")));
            bean.setRoomId(cursor.getString(cursor.getColumnIndex("roomId")));
            bean.setStoreyId(cursor.getString(cursor.getColumnIndex("storeyId")));
            bean.setType(cursor.getString(cursor.getColumnIndex("type")));
            bean.setCheck(cursor.getString(cursor.getColumnIndex("isCheck")));
            Cursor cursor1 = db.query(DbConstant.TABLE_EQUIPMENTATTRIBUTE, null, "equipmentId=?", new String[]{bean.getId()}, null, null, null);
            List<EquipmentResultBean.DataBean.EquipmentAttributeListBean> list = new ArrayList<>();
            while (cursor1.moveToNext()) {
                EquipmentResultBean.DataBean.EquipmentAttributeListBean bean1 = new EquipmentResultBean.DataBean.EquipmentAttributeListBean();
                bean1.setCreateBy(cursor1.getString(cursor1.getColumnIndex("createBy")));
                bean1.setCreateDate(cursor1.getString(cursor1.getColumnIndex("createDate")));
                bean1.setCreateName(cursor1.getString(cursor1.getColumnIndex("createName")));
                bean1.setEquipmentId(cursor1.getString(cursor1.getColumnIndex("equipmentId")));
                bean1.setId(cursor1.getString(cursor1.getColumnIndex("id")));
                bean1.setMax(cursor1.getDouble(cursor1.getColumnIndex("max")));
                bean1.setMin(cursor1.getDouble(cursor1.getColumnIndex("min")));
                bean1.setName(cursor1.getString(cursor1.getColumnIndex("name")));
                bean1.setRating(cursor1.getDouble(cursor1.getColumnIndex("rating")));
                bean1.setType(cursor1.getString(cursor1.getColumnIndex("type")));
                bean1.setUpdateBy(cursor1.getString(cursor1.getColumnIndex("updateBy")));
                bean1.setUpdateName(cursor1.getString(cursor1.getColumnIndex("updateName")));
                list.add(bean1);
            }
            bean.setEquipmentAttributeList(list);
            cursor1.close();
            dataBeans.add(bean);
        }
        cursor.close();
        return dataBeans;
    }

    public void updateEquipmentAttribute(EquipmentResultBean.DataBean.EquipmentAttributeListBean attributeListBean) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("value", attributeListBean.getValue());
        db.update(DbConstant.TABLE_EQUIPMENTATTRIBUTE, values, "equipmentId=? and id=?", new String[]{attributeListBean.getEquipmentId(), attributeListBean.getId()});
    }

    public void updateEquipmentAttribute(EquipmentResultBean.DataBean dataBean) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        for (EquipmentResultBean.DataBean.EquipmentAttributeListBean attributeListBean : dataBean.getEquipmentAttributeList()) {
            ContentValues values = new ContentValues();
            values.put("value", attributeListBean.getValue());
            db.update(DbConstant.TABLE_EQUIPMENTATTRIBUTE, values, "equipmentId=? and id=?", new String[]{attributeListBean.getEquipmentId(), attributeListBean.getId()});
        }
    }

    public void updateEquipmentCheck(EquipmentResultBean.DataBean dataBean) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isCheck", "true");
        db.update(DbConstant.TABLE_EQUIPMENT, values, "nfcCode=?", new String[]{dataBean.getNfcCode()});
    }

    /**
     * 取消已巡检状态
     */
    public void deleteEquipmentCheck() {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isCheck", "false");
        db.update(DbConstant.TABLE_EQUIPMENT, values, null, null);
    }

    private int queryRegionById(String id) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstant.TABLE_REGION, null, "id=?", new String[]{id}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public RegionResultBean.DataBean queryRegionInfoById(String id) {
        RegionResultBean.DataBean dataBean = new RegionResultBean.DataBean();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstant.TABLE_REGION, null, "id=?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            dataBean.setCode(cursor.getString(cursor.getColumnIndex("code")));
            dataBean.setCreateBy(cursor.getString(cursor.getColumnIndex("createBy")));
            dataBean.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
            dataBean.setCreateName(cursor.getString(cursor.getColumnIndex("createName")));
            dataBean.setId(cursor.getString(cursor.getColumnIndex("id")));
            dataBean.setName(cursor.getString(cursor.getColumnIndex("name")));
            dataBean.setNfcCode(cursor.getString(cursor.getColumnIndex("nfcCode")));
            dataBean.setParentId(cursor.getString(cursor.getColumnIndex("parentId")));
            dataBean.setUpdateBy(cursor.getString(cursor.getColumnIndex("updateBy")));
            dataBean.setUpdateDate(cursor.getString(cursor.getColumnIndex("updateDate")));
            dataBean.setUpdateName(cursor.getString(cursor.getColumnIndex("updateName")));
            dataBean.setType(cursor.getString(cursor.getColumnIndex("type")));
        }
        cursor.close();
        return dataBean;
    }

    public RegionResultBean.DataBean queryRegionByNfcCode(String nfcCode) {
        RegionResultBean.DataBean dataBean = new RegionResultBean.DataBean();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstant.TABLE_REGION, null, "nfcCode=?", new String[]{nfcCode}, null, null, null);
        if (cursor.moveToFirst()) {
            dataBean.setCode(cursor.getString(cursor.getColumnIndex("code")));
            dataBean.setCreateBy(cursor.getString(cursor.getColumnIndex("createBy")));
            dataBean.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
            dataBean.setCreateName(cursor.getString(cursor.getColumnIndex("createName")));
            dataBean.setId(cursor.getString(cursor.getColumnIndex("id")));
            dataBean.setName(cursor.getString(cursor.getColumnIndex("name")));
            dataBean.setNfcCode(cursor.getString(cursor.getColumnIndex("nfcCode")));
            dataBean.setParentId(cursor.getString(cursor.getColumnIndex("parentId")));
            dataBean.setUpdateBy(cursor.getString(cursor.getColumnIndex("updateBy")));
            dataBean.setUpdateDate(cursor.getString(cursor.getColumnIndex("updateDate")));
            dataBean.setUpdateName(cursor.getString(cursor.getColumnIndex("updateName")));
            dataBean.setType(cursor.getString(cursor.getColumnIndex("type")));
        }
        cursor.close();
        return dataBean;
    }

    public String queryWholeLocation(String nfcCode) {
        StringBuilder builder = new StringBuilder();
        RegionResultBean.DataBean bean = queryRegionByNfcCode(nfcCode);
        if (!TextUtils.isEmpty(bean.getName())) {
            builder.append(bean.getName());
        }

//        while (true) {
//
//            if () {
//
//            } else {
//                break;
//            }
//        }

        return builder.toString();
    }

    private int queryEquipmentById(String id) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstant.TABLE_EQUIPMENT, null, "id=?", new String[]{id}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public EndTaskBean queryAllAttribute() {
        EndTaskBean endTaskBean = new EndTaskBean();
        List<EndTaskBean.TaskRecordDetailListBean> list = new ArrayList<>();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstant.TABLE_EQUIPMENTATTRIBUTE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            EndTaskBean.TaskRecordDetailListBean recordListBean = new EndTaskBean.TaskRecordDetailListBean();
            recordListBean.setCreateBy(cursor.getString(cursor.getColumnIndex("createBy")));
            recordListBean.setCreateName(cursor.getString(cursor.getColumnIndex("createName")));
            recordListBean.setEquipmentAttributeId(cursor.getString(cursor.getColumnIndex("equipmentId")));
            recordListBean.setEquipmentAttributeName(cursor.getString(cursor.getColumnIndex("name")));
            recordListBean.setUpdateBy(cursor.getString(cursor.getColumnIndex("updateBy")));
            recordListBean.setUpdateName(cursor.getString(cursor.getColumnIndex("updateName")));
            recordListBean.setValue(cursor.getDouble(cursor.getColumnIndex("value")));
            list.add(recordListBean);
        }
        endTaskBean.setTaskRecordDetailList(list);
        cursor.close();
        return endTaskBean;
    }

    public void saveFeedback(FeedbackBean bean) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", bean.getContent());
        values.put("createBy", bean.getCreateBy());
        values.put("createDate", bean.getCreateDate());
        values.put("createName", bean.getCreateName());
        values.put("id", bean.getId());

        values.put("regionCode", bean.getRegionCode());
        values.put("regionId", bean.getRegionId());
        values.put("regionName", bean.getRegionName());
        values.put("type", bean.getType());
        values.put("updateBy", bean.getUpdateBy());
        values.put("updateDate", bean.getUpdateDate());
        values.put("updateName", bean.getUpdateName());
        if (!TextUtils.isEmpty(bean.getId())) {
            Cursor cursor = db.query(DbConstant.TABLE_FEEDBACK, null, "id=?", new String[]{bean.getId()}, null, null, null);
            if (cursor.getCount() > 0) {
                db.update(DbConstant.TABLE_FEEDBACK, values, "id=?", new String[]{bean.getId()});
            } else {
                db.insert(DbConstant.TABLE_FEEDBACK, null, values);
            }
            cursor.close();
        } else {
            db.insert(DbConstant.TABLE_FEEDBACK, null, values);
        }

        for (FeedbackBean.LocalFeedbackPictureListBean listBean : bean.getLocalFeedbackPictureList()) {
            ContentValues listValues = new ContentValues();
            listValues.put("createBy", listBean.getCreateBy());
            listValues.put("createDate", listBean.getCreateDate());
            listValues.put("createName", listBean.getCreateName());
            listValues.put("id", listBean.getId());
            listValues.put("localFeedbackId", listBean.getLocalFeedbackId());
            listValues.put("picture", listBean.getPicture());
            listValues.put("path", listBean.getPath());
            listValues.put("updateBy", listBean.getUpdateBy());
            listValues.put("updateDate", listBean.getUpdateDate());
            listValues.put("updateName", listBean.getUpdateName());
            db.insert(DbConstant.TABLE_FEEDBACK_PICTURE_LIST, null, listValues);
        }
    }

    public List<FeedbackBean> queryAllFeedback() {
        List<FeedbackBean> list = new ArrayList<>();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstant.TABLE_FEEDBACK, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            FeedbackBean feedbackBean = new FeedbackBean();
            feedbackBean.setContent(cursor.getString(cursor.getColumnIndex("content")));
            feedbackBean.setCreateBy(cursor.getString(cursor.getColumnIndex("createBy")));
            feedbackBean.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
            feedbackBean.setCreateName(cursor.getString(cursor.getColumnIndex("createName")));
            feedbackBean.setId(cursor.getString(cursor.getColumnIndex("id")));
            feedbackBean.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            feedbackBean.setRegionCode(cursor.getString(cursor.getColumnIndex("regionCode")));
            feedbackBean.setRegionId(cursor.getString(cursor.getColumnIndex("regionId")));
            feedbackBean.setRegionName(cursor.getString(cursor.getColumnIndex("regionName")));
            feedbackBean.setType(cursor.getString(cursor.getColumnIndex("type")));
            feedbackBean.setUpdateBy(cursor.getString(cursor.getColumnIndex("updateBy")));
            feedbackBean.setUpdateDate(cursor.getString(cursor.getColumnIndex("updateDate")));
            feedbackBean.setUpdateName(cursor.getString(cursor.getColumnIndex("updateName")));

            Cursor cursor1 = db.query(DbConstant.TABLE_FEEDBACK_PICTURE_LIST, null, "id=?", new String[]{feedbackBean.getId()}, null, null, null);
            List<FeedbackBean.LocalFeedbackPictureListBean> pictureList = new ArrayList<>();
            while (cursor1.moveToNext()) {
                FeedbackBean.LocalFeedbackPictureListBean tempBean = new FeedbackBean.LocalFeedbackPictureListBean();
                tempBean.setCreateBy(cursor1.getString(cursor1.getColumnIndex("createBy")));
                tempBean.setCreateDate(cursor1.getString(cursor1.getColumnIndex("createDate")));
                tempBean.setCreateName(cursor1.getString(cursor1.getColumnIndex("createName")));
                tempBean.setId(cursor1.getString(cursor1.getColumnIndex("id")));
                tempBean.setLocalFeedbackId(cursor1.getString(cursor1.getColumnIndex("localFeedbackId")));
                tempBean.setPicture(cursor1.getString(cursor1.getColumnIndex("picture")));
                tempBean.setPath(cursor1.getString(cursor1.getColumnIndex("path")));
                tempBean.setUpdateBy(cursor1.getString(cursor1.getColumnIndex("updateBy")));
                tempBean.setUpdateDate(cursor1.getString(cursor1.getColumnIndex("updateDate")));
                tempBean.setUpdateName(cursor1.getString(cursor1.getColumnIndex("updateName")));
                pictureList.add(tempBean);
            }
            feedbackBean.setLocalFeedbackPictureList(pictureList);
            cursor1.close();
            list.add(feedbackBean);
        }
        cursor.close();
        return list;
    }

    public FeedbackBean queryFeedbackById(String id) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        Cursor cursor = db.query(DbConstant.TABLE_FEEDBACK, null, "id=?", new String[]{id}, null, null, null);
        FeedbackBean feedbackBean = new FeedbackBean();
        if (cursor.moveToFirst()) {
            feedbackBean.setContent(cursor.getString(cursor.getColumnIndex("content")));
            feedbackBean.setCreateBy(cursor.getString(cursor.getColumnIndex("createBy")));
            feedbackBean.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
            feedbackBean.setCreateName(cursor.getString(cursor.getColumnIndex("createName")));
            feedbackBean.setId(cursor.getString(cursor.getColumnIndex("id")));
            feedbackBean.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            feedbackBean.setRegionCode(cursor.getString(cursor.getColumnIndex("regionCode")));
            feedbackBean.setRegionId(cursor.getString(cursor.getColumnIndex("regionId")));
            feedbackBean.setRegionName(cursor.getString(cursor.getColumnIndex("regionName")));
            feedbackBean.setType(cursor.getString(cursor.getColumnIndex("type")));
            feedbackBean.setUpdateBy(cursor.getString(cursor.getColumnIndex("updateBy")));
            feedbackBean.setUpdateDate(cursor.getString(cursor.getColumnIndex("updateDate")));
            feedbackBean.setUpdateName(cursor.getString(cursor.getColumnIndex("updateName")));
            Cursor cursor1 = db.query(DbConstant.TABLE_FEEDBACK_PICTURE_LIST, null, "id=?", new String[]{id}, null, null, null);
            List<FeedbackBean.LocalFeedbackPictureListBean> beanList = new ArrayList<>();
            while (cursor1.moveToNext()) {
                FeedbackBean.LocalFeedbackPictureListBean tempBean = new FeedbackBean.LocalFeedbackPictureListBean();
                tempBean.setCreateBy(cursor1.getString(cursor1.getColumnIndex("createBy")));
                tempBean.setCreateDate(cursor1.getString(cursor1.getColumnIndex("createDate")));
                tempBean.setCreateName(cursor1.getString(cursor1.getColumnIndex("createName")));
                tempBean.setId(cursor1.getString(cursor1.getColumnIndex("id")));
                tempBean.setLocalFeedbackId(cursor1.getString(cursor1.getColumnIndex("localFeedbackId")));
                tempBean.setPicture(cursor1.getString(cursor1.getColumnIndex("picture")));
                if (cursor1.getString(cursor1.getColumnIndex("path")) == null) {
                    continue;
                } else {
                    tempBean.setPath(cursor1.getString(cursor1.getColumnIndex("path")));
                }
                tempBean.setUpdateBy(cursor1.getString(cursor1.getColumnIndex("updateBy")));
                tempBean.setUpdateDate(cursor1.getString(cursor1.getColumnIndex("updateDate")));
                tempBean.setUpdateName(cursor1.getString(cursor1.getColumnIndex("updateName")));
                beanList.add(tempBean);
            }
            feedbackBean.setLocalFeedbackPictureList(beanList);
            cursor1.close();
        }
        cursor.close();
        return feedbackBean;
    }

    public void deleteFeedback(FeedbackBean bean) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(DbConstant.TABLE_FEEDBACK, "id=?", new String[]{bean.getId()});
        deleteFeedbackPictureById(bean.getId());
    }

    public void deleteFeedbackById(String id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(DbConstant.TABLE_FEEDBACK, "id=?", new String[]{id});
        deleteFeedbackPictureById(id);
    }

    public void deleteFeedbackPictureById(String id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(DbConstant.TABLE_FEEDBACK_PICTURE_LIST, "id=?", new String[]{id});
    }

    public void saveEquipmentFault(EquipmentFaultBean bean) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", bean.getContent());
        values.put("createBy", bean.getCreateBy());
        values.put("createDate", bean.getCreateDate());
        values.put("createName", bean.getCreateName());
        values.put("equipmentId", bean.getEquipmentId());
        values.put("equipmentName", bean.getEquipmentName());
        values.put("equipmentCode", bean.getEquipmentCode());
        values.put("id", bean.getId());
        values.put("location", bean.getLocation());
        values.put("type", bean.getType());
        values.put("updateBy", bean.getUpdateBy());
        values.put("updateDate", bean.getUpdateDate());
        values.put("updateName", bean.getUpdateName());
        if (!TextUtils.isEmpty(bean.getId())) {
            Cursor cursor = db.query(DbConstant.TABLE_EQUIPMENT_FAULT, null, "id=?", new String[]{bean.getId()}, null, null, null);
            if (cursor.getCount() > 0) {
                db.update(DbConstant.TABLE_EQUIPMENT_FAULT, values, "id=?", new String[]{bean.getId()});
            } else {
                db.insert(DbConstant.TABLE_EQUIPMENT_FAULT, null, values);
            }
            cursor.close();
        } else {
            db.insert(DbConstant.TABLE_EQUIPMENT_FAULT, null, values);
        }

        for (EquipmentFaultBean.EquipmentFeedbackPictureListBean pictureBean : bean.getEquipmentFeedbackPictureList()) {
            ContentValues picValue = new ContentValues();
            picValue.put("createBy", pictureBean.getCreateBy());
            picValue.put("createDate", pictureBean.getCreateDate());
            picValue.put("createName", pictureBean.getCreateName());
            picValue.put("equipmentFeedbackId", pictureBean.getEquipmentFeedbackId());
            picValue.put("id", pictureBean.getId());
            picValue.put("picture", pictureBean.getPicture());
            picValue.put("path", pictureBean.getPath());
            picValue.put("updateBy", pictureBean.getUpdateBy());
            picValue.put("updateDate", pictureBean.getUpdateDate());
            picValue.put("updateName", pictureBean.getUpdateName());
            db.insert(DbConstant.TABLE_EQUIPMENT_FAULT_PICTURE_LIST, null, picValue);
        }
    }

    public EquipmentFaultBean queryEquipmentFaultById(String id) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        EquipmentFaultBean equipmentFaultBean = new EquipmentFaultBean();
        Cursor cursor = db.query(DbConstant.TABLE_EQUIPMENT_FAULT, null, "id=?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            equipmentFaultBean.setContent(cursor.getString(cursor.getColumnIndex("content")));
            equipmentFaultBean.setCreateBy(cursor.getString(cursor.getColumnIndex("createBy")));
            equipmentFaultBean.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
            equipmentFaultBean.setCreateName(cursor.getString(cursor.getColumnIndex("createName")));
            equipmentFaultBean.setEquipmentCode(cursor.getString(cursor.getColumnIndex("equipmentCode")));
            equipmentFaultBean.setEquipmentId(cursor.getString(cursor.getColumnIndex("equipmentId")));
            equipmentFaultBean.setEquipmentName(cursor.getString(cursor.getColumnIndex("equipmentName")));
            equipmentFaultBean.setId(cursor.getString(cursor.getColumnIndex("id")));
            equipmentFaultBean.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            equipmentFaultBean.setType(cursor.getString(cursor.getColumnIndex("type")));
            equipmentFaultBean.setUpdateBy(cursor.getString(cursor.getColumnIndex("updateBy")));
            equipmentFaultBean.setUpdateDate(cursor.getString(cursor.getColumnIndex("updateDate")));
            equipmentFaultBean.setUpdateName(cursor.getString(cursor.getColumnIndex("updateName")));

            Cursor cursor1 = db.query(DbConstant.TABLE_EQUIPMENT_FAULT_PICTURE_LIST, null, "id=?", new String[]{id}, null, null, null);
            List<EquipmentFaultBean.EquipmentFeedbackPictureListBean> pictureList = new ArrayList<>();
            while (cursor1.moveToNext()) {
                EquipmentFaultBean.EquipmentFeedbackPictureListBean tempBean = new EquipmentFaultBean.EquipmentFeedbackPictureListBean();
                tempBean.setCreateBy(cursor1.getString(cursor1.getColumnIndex("createBy")));
                tempBean.setCreateDate(cursor1.getString(cursor1.getColumnIndex("createDate")));
                tempBean.setCreateName(cursor1.getString(cursor1.getColumnIndex("createName")));
                tempBean.setId(cursor1.getString(cursor1.getColumnIndex("id")));
                tempBean.setEquipmentFeedbackId(cursor1.getString(cursor1.getColumnIndex("equipmentFeedbackId")));
                tempBean.setPicture(cursor1.getString(cursor1.getColumnIndex("picture")));
                tempBean.setPath(cursor1.getString(cursor1.getColumnIndex("path")));
                tempBean.setUpdateBy(cursor1.getString(cursor1.getColumnIndex("updateBy")));
                tempBean.setUpdateDate(cursor1.getString(cursor1.getColumnIndex("updateDate")));
                tempBean.setUpdateName(cursor1.getString(cursor1.getColumnIndex("updateName")));
                pictureList.add(tempBean);
            }
            cursor1.close();
            equipmentFaultBean.setEquipmentFeedbackPictureList(pictureList);
        }
        cursor.close();
        return equipmentFaultBean;
    }

    public List<EquipmentFaultBean> queryAllEquipmentFault() {
        List<EquipmentFaultBean> list = new ArrayList<>();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstant.TABLE_EQUIPMENT_FAULT, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            EquipmentFaultBean equipmentFaultBean = new EquipmentFaultBean();
            equipmentFaultBean.setContent(cursor.getString(cursor.getColumnIndex("content")));
            equipmentFaultBean.setCreateBy(cursor.getString(cursor.getColumnIndex("createBy")));
            equipmentFaultBean.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
            equipmentFaultBean.setCreateName(cursor.getString(cursor.getColumnIndex("createName")));
            equipmentFaultBean.setEquipmentCode(cursor.getString(cursor.getColumnIndex("equipmentCode")));
            equipmentFaultBean.setEquipmentId(cursor.getString(cursor.getColumnIndex("equipmentId")));
            equipmentFaultBean.setEquipmentName(cursor.getString(cursor.getColumnIndex("equipmentName")));
            equipmentFaultBean.setId(cursor.getString(cursor.getColumnIndex("id")));
            equipmentFaultBean.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            equipmentFaultBean.setType(cursor.getString(cursor.getColumnIndex("type")));
            equipmentFaultBean.setUpdateBy(cursor.getString(cursor.getColumnIndex("updateBy")));
            equipmentFaultBean.setUpdateDate(cursor.getString(cursor.getColumnIndex("updateDate")));
            equipmentFaultBean.setUpdateName(cursor.getString(cursor.getColumnIndex("updateName")));

            Cursor cursor1 = db.query(DbConstant.TABLE_EQUIPMENT_FAULT_PICTURE_LIST, null, "id=?", new String[]{equipmentFaultBean.getId()}, null, null, null);
            List<EquipmentFaultBean.EquipmentFeedbackPictureListBean> pictureList = new ArrayList<>();
            while (cursor1.moveToNext()) {
                EquipmentFaultBean.EquipmentFeedbackPictureListBean tempBean = new EquipmentFaultBean.EquipmentFeedbackPictureListBean();
                tempBean.setCreateBy(cursor1.getString(cursor1.getColumnIndex("createBy")));
                tempBean.setCreateDate(cursor1.getString(cursor1.getColumnIndex("createDate")));
                tempBean.setCreateName(cursor1.getString(cursor1.getColumnIndex("createName")));
                tempBean.setId(cursor1.getString(cursor1.getColumnIndex("id")));
                tempBean.setEquipmentFeedbackId(cursor1.getString(cursor1.getColumnIndex("equipmentFeedbackId")));
                tempBean.setPicture(cursor1.getString(cursor1.getColumnIndex("picture")));
                tempBean.setPath(cursor1.getString(cursor1.getColumnIndex("path")));
                tempBean.setUpdateBy(cursor1.getString(cursor1.getColumnIndex("updateBy")));
                tempBean.setUpdateDate(cursor1.getString(cursor1.getColumnIndex("updateDate")));
                tempBean.setUpdateName(cursor1.getString(cursor1.getColumnIndex("updateName")));
                pictureList.add(tempBean);
            }
            cursor1.close();
            equipmentFaultBean.setEquipmentFeedbackPictureList(pictureList);
            list.add(equipmentFaultBean);
        }
        cursor.close();
        return list;
    }

    public void deleteEquipmentFault(EquipmentFaultBean bean) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(DbConstant.TABLE_EQUIPMENT_FAULT, "id=?", new String[]{bean.getId()});
    }

    public void deleteEquipmentFaultById(String id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(DbConstant.TABLE_EQUIPMENT_FAULT, "id=?", new String[]{id});
        deleteEquipmentFaultPictureById(id);
    }

    public void deleteEquipmentFaultPictureById(String id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(DbConstant.TABLE_EQUIPMENT_FAULT_PICTURE_LIST, "id=?", new String[]{id});
    }
}
