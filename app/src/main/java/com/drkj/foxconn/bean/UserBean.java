package com.drkj.foxconn.bean;

import java.util.List;

/**
 * Created by VeronicaRen on 2018/3/20 in Java
 */
public class UserBean {

    /**
     * ok : true
     * respCode : 0
     * data : [{"createDate":"2018-03-15 02:13:26","userId":"8a8ab0b246dc81120146dc8181950052","updateDate":null,"createName":"管理员","createBy":"admin","updateName":null,"updateBy":null,"userAccount":"admin","equipmentType":"1","userName":"管理员","id":"8aaa4f1c621ed8ef0162276ef65f0050"},{"createDate":"2018-03-15 02:16:31","userId":"402880e74d75c4dd014d75d44af30005","updateDate":null,"createName":"管理员","createBy":"admin","updateName":null,"updateBy":null,"userAccount":"demo","equipmentType":"1","userName":"real demo","id":"8aaa4f1c621ed8ef01622771c6f90057"},{"createDate":"2018-03-15 02:19:52","userId":"8aaa4f1c621ed8ef0162239a0c5c0003","updateDate":null,"createName":"管理员","createBy":"admin","updateName":null,"updateBy":null,"userAccount":"ddddd","equipmentType":"0","userName":"ddddd","id":"8aaa4f1c621ed8ef01622774db1f005c"}]
     * message : 成功
     */

    private boolean ok;
    private String respCode;
    private String message;
    private List<DataBean> data;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createDate : 2018-03-15 02:13:26
         * userId : 8a8ab0b246dc81120146dc8181950052
         * updateDate : null
         * createName : 管理员
         * createBy : admin
         * updateName : null
         * updateBy : null
         * userAccount : admin
         * equipmentType : 1
         * userName : 管理员
         * id : 8aaa4f1c621ed8ef0162276ef65f0050
         */

        private String createDate;
        private String userId;
        private Object updateDate;
        private String createName;
        private String createBy;
        private Object updateName;
        private Object updateBy;
        private String userAccount;
        private String equipmentType;
        private String userName;
        private String id;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
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

        public Object getUpdateName() {
            return updateName;
        }

        public void setUpdateName(Object updateName) {
            this.updateName = updateName;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getEquipmentType() {
            return equipmentType;
        }

        public void setEquipmentType(String equipmentType) {
            this.equipmentType = equipmentType;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
