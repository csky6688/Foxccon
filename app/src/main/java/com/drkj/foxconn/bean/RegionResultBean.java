package com.drkj.foxconn.bean;

import java.util.List;

/**
 * Created by ganlong on 2018/1/29.
 * 查询所有区域信息返回的数据对象
 */

public class RegionResultBean {

    /**
     * ok : true
     * respCode : 0
     * data : [{"code":"parent","createDate":"2018-01-25 18:55:35","parentId":null,"updateDate":null,"createName":"管理员","createBy":"admin","nfcCode":"parent","updateBy":null,"updateName":null,"name":"富士康厂区","id":"40288186612cf36801612cf5652a0001","type":"0"},{"code":"1","createDate":"2018-01-25 18:56:35","parentId":"40288186612cf36801612cf5652a0001","updateDate":null,"createName":"管理员","createBy":"admin","nfcCode":"1","updateBy":null,"updateName":null,"name":"1","id":"40288186612cf36801612cf652370003","type":"1"}]
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
         * code : parent
         * createDate : 2018-01-25 18:55:35
         * parentId : null
         * updateDate : null
         * createName : 管理员
         * createBy : admin
         * nfcCode : parent
         * updateBy : null
         * updateName : null
         * name : 富士康厂区
         * id : 40288186612cf36801612cf5652a0001
         * type : 0(厂区) 1 楼栋 2 楼层 3 机房
         */

        private String code;
        private String createDate;
        private String parentId;
        private String updateDate;
        private String createName;
        private String createBy;
        private String nfcCode;
        private String updateBy;
        private String updateName;
        private String name;
        private String id;
        private String type;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
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

        public String getNfcCode() {
            return nfcCode;
        }

        public void setNfcCode(String nfcCode) {
            this.nfcCode = nfcCode;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    }
}
