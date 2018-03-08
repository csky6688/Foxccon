package com.drkj.foxconn.bean;

import java.util.List;

/**
 * Created by ganlong on 2018/1/31.
 */

public class StartTaskBean {

    /**
     * createBy : string
     * createDate : 2018-01-31T02:44:25.670Z
     * createName : string
     * id : string
     * inspectingPersonId : string
     * name : string
     * status : string
     * taskRecordList : [{"createBy":"string","createDate":"2018-01-31T02:44:25.670Z","createName":"string","equipmentAttributeId":"string","equipmentId":"string","equipmentName":"string","id":"string","recordTime":"2018-01-31T02:44:25.670Z","taskId":"string","updateBy":"string","updateDate":"2018-01-31T02:44:25.670Z","updateName":"string","value":0}]
     * updateBy : string
     * updateDate : 2018-01-31T02:44:25.670Z
     * updateName : string
     */

    private String createBy;
    private String createDate;
    private String createName;
    private String id;
    private String inspectingPersonId;
    private String name;
    private String status;
    private String updateBy;
    private String updateDate;
    private String updateName;
    private List<TaskRecordListBean> taskRecordList;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspectingPersonId() {
        return inspectingPersonId;
    }

    public void setInspectingPersonId(String inspectingPersonId) {
        this.inspectingPersonId = inspectingPersonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<TaskRecordListBean> getTaskRecordList() {
        return taskRecordList;
    }

    public void setTaskRecordList(List<TaskRecordListBean> taskRecordList) {
        this.taskRecordList = taskRecordList;
    }

    public static class TaskRecordListBean {
        /**
         * createBy : string
         * createDate : 2018-01-31T02:44:25.670Z
         * createName : string
         * equipmentAttributeId : string
         * equipmentId : string
         * equipmentName : string
         * id : string
         * recordTime : 2018-01-31T02:44:25.670Z
         * taskId : string
         * updateBy : string
         * updateDate : 2018-01-31T02:44:25.670Z
         * updateName : string
         * value : 0
         */

        private String createBy;
        private String createDate;
        private String createName;
        private String equipmentAttributeId;
        private String equipmentId;
        private String equipmentName;
        private String id;
        private String recordTime;
        private String taskId;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(String recordTime) {
            this.recordTime = recordTime;
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

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
