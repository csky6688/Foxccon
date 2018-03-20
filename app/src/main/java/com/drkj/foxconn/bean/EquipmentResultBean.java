package com.drkj.foxconn.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ganlong on 2018/1/29.
 * 查询所有设备返回的数据对象
 */

public class EquipmentResultBean implements Serializable {

    /**
     * data : [{"buildingId":"40288186612cf36801612cf5652a0001","code":"a","createBy":"admin","createDate":"2018-01-28 00:14:10","createName":"管理员","equipmentAttributeList":[{"createBy":"admin","createDate":"2018-01-28 00:14:10","createName":"管理员","equipmentId":"402880f46138644a01613865c86f0001","id":"402880f46138644a01613865c8700002","max":3,"min":2,"name":"a","rating":1,"type":"0","updateBy":"","updateName":""},{"createBy":"admin","createDate":"2018-01-28 00:14:10","createName":"管理员","equipmentId":"402880f46138644a01613865c86f0001","id":"402880f46138644a01613865c8700003","max":6,"min":5,"name":"b","rating":4,"type":"1"}],"id":"402880f46138644a01613865c86f0001","name":"a","nfcCode":"a","roomId":"","storeyId":"40288186612cf36801612cf652370003","type":"0"},{"buildingId":"40288186612cf36801612cf5652a0001","code":"h","createBy":"admin","createDate":"2018-01-28 00:14:53","createName":"管理员","equipmentAttributeList":[{"createBy":"admin","createDate":"2018-01-28 00:14:53","createName":"管理员","equipmentId":"402880f46138644a016138666fef0005","id":"402880f46138644a016138666fef0006","max":9,"min":8,"name":"c","rating":7,"type":"0","updateBy":"","updateName":""},{"createBy":"admin","createDate":"2018-01-28 00:14:53","createName":"管理员","equipmentId":"402880f46138644a016138666fef0005","id":"402880f46138644a016138666fef0007","max":12,"min":11,"name":"d","rating":10,"type":"0"}],"id":"402880f46138644a016138666fef0005","name":"h","nfcCode":"h","roomId":"","storeyId":"40288186612cf36801612cf652370003","type":"0"}]
     * message : 成功
     * ok : true
     * respCode : 0
     */

    private String message;
    private boolean ok;
    private String respCode;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * buildingId : 40288186612cf36801612cf5652a0001
         * code : a
         * createBy : admin
         * createDate : 2018-01-28 00:14:10
         * createName : 管理员
         * equipmentAttributeList : [{"createBy":"admin","createDate":"2018-01-28 00:14:10","createName":"管理员","equipmentId":"402880f46138644a01613865c86f0001","id":"402880f46138644a01613865c8700002","max":3,"min":2,"name":"a","rating":1,"type":"0","updateBy":"","updateName":""},{"createBy":"admin","createDate":"2018-01-28 00:14:10","createName":"管理员","equipmentId":"402880f46138644a01613865c86f0001","id":"402880f46138644a01613865c8700003","max":6,"min":5,"name":"b","rating":4,"type":"1"}]
         * id : 402880f46138644a01613865c86f0001
         * name : a
         * nfcCode : a
         * roomId :
         * storeyId : 40288186612cf36801612cf652370003
         * type : 0
         */

        private String buildingId;
        private String code;
        private String createBy;
        private String createDate;
        private String createName;
        private String id;
        private String name;
        private String nfcCode;
        private String roomId;
        private String storeyId;
        private String type;
        private String isCheck;//TODO 非服务器数据，上传时记得设置为null
        private List<EquipmentAttributeListBean> equipmentAttributeList;

        public String getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(String buildingId) {
            this.buildingId = buildingId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNfcCode() {
            return nfcCode;
        }

        public void setNfcCode(String nfcCode) {
            this.nfcCode = nfcCode;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getStoreyId() {
            return storeyId;
        }

        public void setStoreyId(String storeyId) {
            this.storeyId = storeyId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String isCheck() {
            return isCheck;
        }

        public void setCheck(String check) {
            isCheck = check;
        }

        public List<EquipmentAttributeListBean> getEquipmentAttributeList() {
            return equipmentAttributeList;
        }

        public void setEquipmentAttributeList(List<EquipmentAttributeListBean> equipmentAttributeList) {
            this.equipmentAttributeList = equipmentAttributeList;
        }

        public static class EquipmentAttributeListBean {
            /**
             * createBy : admin
             * createDate : 2018-01-28 00:14:10
             * createName : 管理员
             * equipmentId : 402880f46138644a01613865c86f0001
             * id : 402880f46138644a01613865c8700002
             * max : 3.0
             * min : 2.0
             * name : a
             * rating : 1.0
             * type : 0
             * updateBy :
             * updateName :
             */

            private String createBy;
            private String createDate;
            private String createName;
            private String equipmentId;
            private String id;
            private double max;
            private double min;
            private String name;
            private double rating;
            private String type;
            private String updateBy;
            private String updateName;

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

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

            public String getEquipmentId() {
                return equipmentId;
            }

            public void setEquipmentId(String equipmentId) {
                this.equipmentId = equipmentId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public double getMax() {
                return max;
            }

            public void setMax(double max) {
                this.max = max;
            }

            public double getMin() {
                return min;
            }

            public void setMin(double min) {
                this.min = min;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getRating() {
                return rating;
            }

            public void setRating(double rating) {
                this.rating = rating;
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

            public String getUpdateName() {
                return updateName;
            }

            public void setUpdateName(String updateName) {
                this.updateName = updateName;
            }
        }
    }
}
