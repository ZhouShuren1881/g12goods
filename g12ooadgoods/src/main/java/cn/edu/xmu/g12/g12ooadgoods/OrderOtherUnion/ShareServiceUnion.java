package cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion;

import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import cn.edu.xmu.oomall.other.model.TimeDTO;
import cn.edu.xmu.oomall.other.service.IShareService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceUnion {
    @DubboReference
    IShareService shareService;

    public ReturnObject<Boolean> skuSharable(Long skuId) {
        return new Trans<Boolean>().oomall(shareService.skuSharable(skuId));
    }
}
