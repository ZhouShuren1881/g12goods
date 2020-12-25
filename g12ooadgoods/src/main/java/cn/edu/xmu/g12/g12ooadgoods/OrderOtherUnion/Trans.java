package cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion;

public class Trans<T> {
    public cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject<T> oomall(cn.edu.xmu.oomall.util.ReturnObject<T> returnObject) {
        T object = returnObject.getData();
        cn.edu.xmu.oomall.util.ResponseCode code = returnObject.getCode();

        if (object != null) return new cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject<>(object);

        String enumName = code.name();
        cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode g12ResponseCode
                = cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode.valueOf(enumName);
        return new cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject<>(g12ResponseCode);
    }

    public cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject<T> ooad(cn.edu.xmu.ooad.util.ReturnObject<T> returnObject) {
        T object = returnObject.getData();
        cn.edu.xmu.ooad.util.ResponseCode code = returnObject.getCode();

        if (object != null) return new cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject<>(object);

        String enumName = code.name();
        cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode g12ResponseCode
                = cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode.valueOf(enumName);
        return new cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject<>(g12ResponseCode);
    }
}
