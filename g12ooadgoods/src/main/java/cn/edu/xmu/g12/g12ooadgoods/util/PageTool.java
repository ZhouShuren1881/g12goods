package cn.edu.xmu.g12.g12ooadgoods.util;

public class PageTool {
    private Integer page;
    private Integer pageSize;

    public static PageTool newPageTool(Integer page, Integer pageSize) {
        if (page != null && page <= 0 || pageSize != null && pageSize <= 0)
            return null;
        var pt = new PageTool();
        pt.page = page==null?1:page;
        pt.pageSize = pageSize==null?10:pageSize;
        return pt;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
