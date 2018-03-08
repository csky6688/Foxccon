package com.drkj.foxconn.bean;

import java.util.List;

/**
 * Created by VeronicaRen on 2018/2/27.
 * 上报设备故障的对象
 */
public class EquipmentFaultBean {

    /**
     * content : string
     * createBy : string
     * createDate : 2018-03-01T02:04:35.366Z
     * createName : string
     * equipmentCode : string
     * equipmentFeedbackPictureList : [{"createBy":"string","createDate":"2018-03-01T02:04:35.366Z","createName":"string","equipmentFeedbackId":"string","id":"string","picture":"string","updateBy":"string","updateDate":"2018-03-01T02:04:35.366Z","updateName":"string"}]
     * equipmentId : string
     * equipmentName : string
     * id : string
     * type : string
     * updateBy : string
     * updateDate : 2018-03-01T02:04:35.366Z
     * updateName : string
     */

    private String content;
    private String createBy;
    private String createDate;
    private String createName;
    private String equipmentCode;
    private String equipmentId;
    private String equipmentName;
    private String id;
    private String type;
    private String updateBy;
    private String updateDate;
    private String updateName;
    private List<EquipmentFeedbackPictureListBean> equipmentFeedbackPictureList;

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

    public List<EquipmentFeedbackPictureListBean> getEquipmentFeedbackPictureList() {
        return equipmentFeedbackPictureList;
    }

    public void setEquipmentFeedbackPictureList(List<EquipmentFeedbackPictureListBean> equipmentFeedbackPictureList) {
        this.equipmentFeedbackPictureList = equipmentFeedbackPictureList;
    }

    public static class EquipmentFeedbackPictureListBean {
        /**
         * createBy : string
         * createDate : 2018-03-01T02:04:35.366Z
         * createName : string
         * equipmentFeedbackId : string
         * id : string
         * picture : string
         * updateBy : string
         * updateDate : 2018-03-01T02:04:35.366Z
         * updateName : string
         */

        private String createBy;
        private String createDate;
        private String createName;
        private String equipmentFeedbackId;
        private String id;
        private String picture;
        private String path;
        private String updateBy;
        private String updateDate;
        private String updateName;

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

        public String getEquipmentFeedbackId() {
            return equipmentFeedbackId;
        }

        public void setEquipmentFeedbackId(String equipmentFeedbackId) {
            this.equipmentFeedbackId = equipmentFeedbackId;
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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
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
