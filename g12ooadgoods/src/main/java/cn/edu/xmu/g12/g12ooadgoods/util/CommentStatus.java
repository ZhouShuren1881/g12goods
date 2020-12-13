package cn.edu.xmu.g12.g12ooadgoods.util;
/**
 * 商品状态码
 * created by snow 2020/12/13 18:50
 */
public enum CommentStatus {
    UN_CONFIRMED(0, "未审核"),
    SUCCESS(1, "评论成功"),
    FAILED(2, "未通过");

    private int code;
    private String description;
    CommentStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode(){
        return this.code;
    }

    public String getDescription() {
        return description;
    }
}