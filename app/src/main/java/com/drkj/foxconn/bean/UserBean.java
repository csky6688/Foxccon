package com.drkj.foxconn.bean;

import java.util.List;

/**
 * Created by VeronicaRen on 2018/3/20 in Java
 */
public class UserBean {

    /**
     * message : 成功
     * data : [{"id":"40288187622c7c7501622c8101c60016","userName":"管理员","userAccount":"admin","equipmentType":"2","createName":"管理员","createBy":"admin","createDate":"2018-03-16 09:51:14","updateName":null,"updateBy":null,"updateDate":null,"userId":"8a8ab0b246dc81120146dc8181950052"},{"id":"402881876247861a0162478750560001","userName":"real demo","userAccount":"demo","equipmentType":"1","createName":"管理员","createBy":"admin","createDate":"2018-03-21 15:47:53","updateName":null,"updateBy":null,"updateDate":null,"userId":"402880e74d75c4dd014d75d44af30005"},{"id":"402881876247861a016247877a1e0003","userName":"巡检员","userAccount":"system","equipmentType":"0","createName":"管理员","createBy":"admin","createDate":"2018-03-21 15:48:03","updateName":null,"updateBy":null,"updateDate":null,"userId":"40288187622c844801622c8a698d0013"}]
     * respCode : 0
     * ok : true
     */

    private String message;
    private String respCode;
    private boolean ok;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 40288187622c7c7501622c8101c60016
         * userName : 管理员
         * userAccount : admin
         * equipmentType : 2
         * createName : 管理员
         * createBy : admin
         * createDate : 2018-03-16 09:51:14
         * updateName : null
         * updateBy : null
         * updateDate : null
         * userId : 8a8ab0b246dc81120146dc8181950052
         */

        private String id;
        private String userName;
        private String userAccount;
        private String equipmentType;
        private String createName;
        private String createBy;
        private String createDate;
        private Object updateName;
        private Object updateBy;
        private Object updateDate;
        private String userId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
            this.updateDate = updateDate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
