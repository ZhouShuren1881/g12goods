package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import lombok.Data;

import java.util.List;

@Data
public class ListBo<T> implements VoObject {
    protected Integer page;
    protected Integer pageSize;
    protected Long    total;
    protected Integer pages;
    protected List<T> list;

    public ListBo() { }

    public ListBo(Integer page, Integer pageSize, Long total, Integer pages,
                     List<T> boList) {
        this();
        this.page       = page;
        this.pageSize   = pageSize;
        this.total      = total;
        this.pages      = pages;
        this.list       = boList;
    }

    public Object createVo() {return this;}

    public Object createSimpleVo() {return this;}
}
