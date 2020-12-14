package cn.edu.xmu.g12.g12ooadgoods.model.vo;


import lombok.Data;

@Data
public class ResponseData {
    private Integer errno;
    private String errmsg;
    private Object data;

    public ResponseData() { }

    public static ResponseData new0() {
        var r = new ResponseData();
        r.errno = 0;
        r.errmsg = "正确";
        return r;
    }
}
