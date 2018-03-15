package com.drkj.foxconn.bean;

import java.util.List;

/**
 * Created by VeronicaRen on 2018/2/27.
 * 上报现场反馈的对象
 * 修改字段
 */

public class FeedbackBean {

    /**
     * content : string
     * createBy : string
     * createDate : 2018-02-28T02:25:28.461Z
     * createName : string
     * id : string
     * localFeedbackPictureList : [{"createBy":"string","createDate":"2018-02-28T02:25:28.461Z","createName":"string","id":"string","localFeedbackId":"string","picture":"string","updateBy":"string","updateDate":"2018-02-28T02:25:28.461Z","updateName":"string"}]
     * regionCode : string
     * regionId : string
     * regionName : string
     * type : string
     * updateBy : string
     * updateDate : 2018-02-28T02:25:28.462Z
     * updateName : string
     */

    private String content;
    private String createBy;
    private String createDate;
    private String createName;
    private String id;
    private String loaction;
    private String regionCode;
    private String regionId;
    private String regionName;
    private String type;
    private String updateBy;
    private String updateDate;
    private String updateName;
    private List<LocalFeedbackPictureListBean> localFeedbackPictureList;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoaction() {
        return loaction;
    }

    public void setLoaction(String loaction) {
        this.loaction = loaction;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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

    public List<LocalFeedbackPictureListBean> getLocalFeedbackPictureList() {
        return localFeedbackPictureList;
    }

    public void setLocalFeedbackPictureList(List<LocalFeedbackPictureListBean> localFeedbackPictureList) {
        this.localFeedbackPictureList = localFeedbackPictureList;
    }

    public static class LocalFeedbackPictureListBean {
        /**
         * createBy : string
         * createDate : 2018-02-28T02:25:28.461Z
         * createName : string
         * id : string
         * localFeedbackId : string
         * picture : string
         * updateBy : string
         * updateDate : 2018-02-28T02:25:28.461Z
         * updateName : string
         */

        private String createBy;
        private String createDate;
        private String createName;
        private String id;
        private String localFeedbackId;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLocalFeedbackId() {
            return localFeedbackId;
        }

        public void setLocalFeedbackId(String localFeedbackId) {
            this.localFeedbackId = localFeedbackId;
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
