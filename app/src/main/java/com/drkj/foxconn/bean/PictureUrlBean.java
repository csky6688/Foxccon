package com.drkj.foxconn.bean;

/**
 * 图片url返回
 * Created by VeronicaRen on 2018/2/27.
 */

public class PictureUrlBean {

    /**
     * success : true
     * msg : 操作成功
     * jsonStr : {"msg":"操作成功","success":true,"attributes":{"name":"pic_welcome.jpg","swfpath":"upload/20180227/20180227081444pdSZvvA0.swf","url":"upload/20180227/20180227081444pdSZvvA0.jpg"}}
     * obj : null
     * attributes : {"name":"pic_welcome.jpg","swfpath":"upload/20180227/20180227081444pdSZvvA0.swf","url":"upload/20180227/20180227081444pdSZvvA0.jpg"}
     */

    private boolean success;
    private String msg;
    private String jsonStr;
    private Object obj;
    private AttributesBean attributes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public AttributesBean getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesBean attributes) {
        this.attributes = attributes;
    }

    public static class AttributesBean {
        /**
         * name : pic_welcome.jpg
         * swfpath : upload/20180227/20180227081444pdSZvvA0.swf
         * url : upload/20180227/20180227081444pdSZvvA0.jpg
         */

        private String name;
        private String swfpath;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSwfpath() {
            return swfpath;
        }

        public void setSwfpath(String swfpath) {
            this.swfpath = swfpath;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
