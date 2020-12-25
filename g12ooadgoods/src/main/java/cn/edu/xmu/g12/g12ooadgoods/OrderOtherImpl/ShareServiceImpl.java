package cn.edu.xmu.g12.g12ooadgoods.OrderOtherImpl;

import cn.edu.xmu.g12.g12ooadgoods.mapper.BrandPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.mapper.ShareActivityPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.model.po.ShareActivityPoExample;
import cn.edu.xmu.oomall.other.service.IShareService;
import cn.edu.xmu.oomall.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShareServiceImpl implements IShareService {

    @Autowired(required = false)
    ShareActivityPoMapper shareActivityPoMapper;

    @Override
    public ReturnObject<List<Long>> setShareRebate(Long orderItemId, Long userId, Integer quantity, Long price, Long skuId, LocalDateTime gmtCreated) {
        return null;
    }

    @Override
    public ReturnObject<Boolean> shareUserSkuMatch(Long sid, Long skuId, Long userId) {
        var shareActPo = shareActivityPoMapper.selectByPrimaryKey(sid);
        return new ReturnObject<>(shareActPo.getGoodsSkuId().equals(skuId));
    }

    @Override
    public ReturnObject<Boolean> skuSharable(Long skuId) {
        var now = LocalDateTime.now();
        var shareActExample = new ShareActivityPoExample();
        shareActExample.createCriteria()
                .andGoodsSkuIdEqualTo(skuId)
                .andStateEqualTo((byte)1)
                .andBeginTimeLessThan(now)
                .andEndTimeGreaterThan(now);
        var shareActPo = shareActivityPoMapper.selectByExample(shareActExample);
        return new ReturnObject<>(shareActPo != null);
    }
}
