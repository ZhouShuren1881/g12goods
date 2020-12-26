package cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion;

import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import cn.edu.xmu.oomall.other.model.TimeDTO;
import cn.edu.xmu.oomall.other.service.IShareService;
import com.alibaba.nacos.api.annotation.NacosInjected;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceUnion {
    @NacosInjected
    IShareService shareService;

    public ReturnObject<Boolean> skuSharable(Long skuId) {
        return new Trans<Boolean>().oomall(shareService.skuSharable(skuId));
    }
}
