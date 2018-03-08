package com.drkj.foxconn.bean;

/**
 * Created by ganlong on 2018/1/30.
 * 创建任务返回的对象
 */

public class StartTaskResultBean {


    /**
     * ok : true
     * respCode : 0
     * data : {"createDate":null,"updateDate":null,"createName":null,"createBy":null,"inspectingPersonId":null,"updateBy":null,"updateName":null,"status":null,"name":null,"id":"8aaa8601613feca201614618953f004b"}
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
         * createDate : null
         * updateDate : null
         * createName : null
         * createBy : null
         * inspectingPersonId : null
         * updateBy : null
         * updateName : null
         * status : null
         * name : null
         * id : 8aaa8601613feca201614618953f004b
         */

        private Object createDate;
        private Object updateDate;
        private Object createName;
        private Object createBy;
        private Object inspectingPersonId;
        private Object updateBy;
        private Object updateName;
        private Object status;
        private Object name;
        private String id;

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
            this.updateDate = updateDate;
        }

        public Object getCreateName() {
            return createName;
        }

        public void setCreateName(Object createName) {
            this.createName = createName;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getInspectingPersonId() {
            return inspectingPersonId;
        }

        public void setInspectingPersonId(Object inspectingPersonId) {
            this.inspectingPersonId = inspectingPersonId;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateName() {
            return updateName;
        }

        public void setUpdateName(Object updateName) {
            this.updateName = updateName;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
