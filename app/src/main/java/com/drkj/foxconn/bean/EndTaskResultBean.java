package com.drkj.foxconn.bean;

/**
 * Created by ganlong on 2018/1/30.
 * 结束任务时返回的对象
 */

public class EndTaskResultBean {

    /**
     * ok : true
     * respCode : 0
     * data : null
     * message : 成功
     */

    private boolean ok;
    private String respCode;
    private Object data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
