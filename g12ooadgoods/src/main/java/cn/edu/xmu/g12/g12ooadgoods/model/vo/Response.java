package cn.edu.xmu.g12.g12ooadgoods.model.vo;

//{
//  "errno": 0,
//  "errmsg": "成功"
//}

public class Response {
    private Integer errno;
    private String errmsg;

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

    public Response() { }

    public static Response new0() {
        var r = new Response();
        r.errno = 0;
        r.errmsg = "正确";
        return r;
    }
}
