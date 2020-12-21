package cn.edu.xmu.g12.g12ooadgoods.model.bo;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;

import java.util.ArrayList;

public class ListVoObject<T> extends ArrayList<T> implements VoObject {

    public ListVoObject() {
        super();
    }

    @Override
    public Object createVo() {
        return this;
    }

    @Override
    public Object createSimpleVo() {
        return this;
    }
}
