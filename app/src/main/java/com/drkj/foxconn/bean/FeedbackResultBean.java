package com.drkj.foxconn.bean;

/**
 * Created by ganlong on 2018/1/31.
 * 上报问题反馈的返回数据对象
 */

public class FeedbackResultBean {

    /**
     * ok : true
     * respCode : 0
     * data : {"createDate":"2018-03-01 07:59:22","updateDate":"2018-03-01 07:59:22","createName":"string","createBy":"string","equipmentName":"string","equipmentCode":"string","equipmentId":"string","updateBy":"string","updateName":"string","id":"8aaab77961e01cb90161e0988a4f002c","type":"string","content":"string"}
     * message : 成功
     */

    private boolean ok;
    private String respCode;
    private DataBean data;
    private String message;

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

    public static class DataBean {
        /**
         * createDate : 2018-03-01 07:59:22
         * updateDate : 2018-03-01 07:59:22
         * createName : string
         * createBy : string
         * equipmentName : string
         * equipmentCode : string
         * equipmentId : string
         * updateBy : string
         * updateName : string
         * id : 8aaab77961e01cb90161e0988a4f002c
         * type : string
         * content : string
         */

        private String createDate;
        private String updateDate;
        private String createName;
        private String createBy;
        private String equipmentName;
        private String equipmentCode;
        private String equipmentId;
        private String updateBy;
        private String updateName;
        private String id;
        private String type;
        private String content;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getEquipmentName() {
            return equipmentName;
        }

        public void setEquipmentName(String equipmentName) {
            this.equipmentName = equipmentName;
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

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateName() {
            return updateName;
        }

        public void setUpdateName(String updateName) {
            this.updateName = updateName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
