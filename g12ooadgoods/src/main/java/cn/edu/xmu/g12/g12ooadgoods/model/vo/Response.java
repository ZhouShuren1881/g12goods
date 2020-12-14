package cn.edu.xmu.g12.g12ooadgoods.model.vo;

//{
//  "errno": 0,
//  "errmsg": "成功"
//}

import lombok.Data;

@Data
public class Response {
    private Integer errno;
    private String errmsg;

    public Response() { }

    public static Response new0() {
        var r = new Response();
        r.errno = 0;
        r.errmsg = "正确";
        return r;
    }
}
