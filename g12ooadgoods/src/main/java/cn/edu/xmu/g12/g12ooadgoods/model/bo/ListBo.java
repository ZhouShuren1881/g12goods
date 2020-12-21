package cn.edu.xmu.g12.g12ooadgoods.model.bo;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import lombok.Data;

import java.util.List;

@Data
public class ListBo<T> implements VoObject {
    protected Integer page;
    protected Integer pageSize;
    protected Long    total;
    protected Integer pages;
    protected List<T> list;

    public ListBo(ResponseCode code) {
        this.page       = 0;
        this.pageSize   = 0;
        this.total      = 0L;
        this.pages      = 0;
    }

    public ListBo(Integer page, Integer pageSize, Long total, Integer pages,
                     List<T> boList) {
        this.page       = page;
        this.pageSize   = pageSize;
        this.total      = total;
        this.pages      = pages;
        this.list       = boList;
    }

    public Object createVo() {return this;}

    public Object createSimpleVo() {return this;}
}
