package cn.edu.xmu.g12.g12ooadgoods.model;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsCategoryPo;

import java.util.ArrayList;
import java.util.List;

public class VoListObject<T> extends ArrayList<T> implements VoObject {

    public Object createVo() {return this;}

    public Object createSimpleVo() {return this;}

}
