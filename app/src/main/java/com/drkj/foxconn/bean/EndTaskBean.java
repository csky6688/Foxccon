package com.drkj.foxconn.bean;

import java.util.List;

/**
 * Created by VeronicaRen on 2018/2/27.
 * 结束任务时上报的对象
 * 修改了字段equipmentId = equipmentAttributeId
 */

public class EndTaskBean {


    /**
     * buildingName : string
     * createBy : string
     * createDate : 2018-03-22T06:19:57.103Z
     * createName : string
     * equipmentCode : string
     * equipmentId : string
     * equipmentName : string
     * equipmentType : string
     * id : string
     * roomName : string
     * storeyName : string
     * taskId : string
     * taskRecordDetailList : [{"createBy":"string","createDate":"2018-03-22T06:19:57.103Z","createName":"string","equipmentAttributeId":"string","equipmentAttributeName":"string","id":"string","taskRecordId":"string","updateBy":"string","updateDate":"2018-03-22T06:19:57.103Z","updateName":"string","value":0}]
     * updateBy : string
     * updateDate : 2018-03-22T06:19:57.103Z
     * updateName : string
     */

    private String buildingName;
    private String createBy;
    private String createDate;
    private String createName;
    private String equipmentCode;
    private String equipmentId;
    private String equipmentName;
    private String equipmentType;
    private String id;
    private String roomName;
    private String storeyName;
    private String taskId;
    private String updateBy;
    private String updateDate;
    private String updateName;
    private List<TaskRecordDetailListBean> taskRecordDetailList;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getStoreyName() {
        return storeyName;
    }

    public void setStoreyName(String storeyName) {
        this.storeyName = storeyName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public List<TaskRecordDetailListBean> getTaskRecordDetailList() {
        return taskRecordDetailList;
    }

    public void setTaskRecordDetailList(List<TaskRecordDetailListBean> taskRecordDetailList) {
        this.taskRecordDetailList = taskRecordDetailList;
    }

    public static class TaskRecordDetailListBean {
        /**
         * createBy : string
         * createDate : 2018-03-22T06:19:57.103Z
         * createName : string
         * equipmentAttributeId : string
         * equipmentAttributeName : string
         * id : string
         * taskRecordId : string
         * updateBy : string
         * updateDate : 2018-03-22T06:19:57.103Z
         * updateName : string
         * value : 0
         */

        private String createBy;
        private String createDate;
        private String createName;
        private String equipmentAttributeId;
        private String equipmentAttributeName;
        private String id;
        private String taskRecordId;
        private String updateBy;
        private String updateDate;
        private String updateName;
        private double value;

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getEquipmentAttributeId() {
            return equipmentAttributeId;
        }

        public void setEquipmentAttributeId(String equipmentAttributeId) {
            this.equipmentAttributeId = equipmentAttributeId;
        }

        public String getEquipmentAttributeName() {
            return equipmentAttributeName;
        }

        public void setEquipmentAttributeName(String equipmentAttributeName) {
            this.equipmentAttributeName = equipmentAttributeName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTaskRecordId() {
            return taskRecordId;
        }

        public void setTaskRecordId(String taskRecordId) {
            this.taskRecordId = taskRecordId;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getUpdateName() {
            return updateName;
        }

        public void setUpdateName(String updateName) {
            this.updateName = updateName;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
