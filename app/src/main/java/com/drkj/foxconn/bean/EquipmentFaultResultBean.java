package com.drkj.foxconn.bean;

/**
 * Created by ganlong on 2018/1/31.
 * 上报设备故障返回的数据对象
 */

public class EquipmentFaultResultBean {

    /**
     * data : {"content":"string","createBy":"string","createDate":"2018-01-31 02:44:25","equipmentName":"string","id":"8aaa8601613feca201614a1da422006a","picture":"string","recordTime":"2018-01-31 02:44:25","type":"string","updateBy":"string","updateDate":"2018-01-31 02:44:25","updateName":"string"}
     * message : 成功
     * ok : true
     * respCode : 0
     */

    private DataBean data;
    private String message;
    private boolean ok;
    private String respCode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public static class DataBean {
        /**
         * content : string
         * createBy : string
         * createDate : 2018-01-31 02:44:25
         * equipmentName : string
         * id : 8aaa8601613feca201614a1da422006a
         * picture : string
         * recordTime : 2018-01-31 02:44:25
         * type : string
         * updateBy : string
         * updateDate : 2018-01-31 02:44:25
         * updateName : string
         */

        private String content;
        private String createBy;
        private String createDate;
        private String equipmentName;
        private String id;
        private String picture;
        private String recordTime;
        private String type;
        private String updateBy;
        private String updateDate;
        private String updateName;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(String recordTime) {
            this.recordTime = recordTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
    }
}
