package cn.edu.xmu.g12.g12ooadgoods.model.vo;

public class ResponseData {
    private Integer errno;
    private String errmsg;
    private Object data;

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseData() { }

    public static ResponseData new0() {
        var r = new ResponseData();
        r.errno = 0;
        r.errmsg = "正确";
        return r;
    }
}
