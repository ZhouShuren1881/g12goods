package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.mapper.*;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.IdNameOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.ListBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SpuOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.groupon.GrouponBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.groupon.GrouponOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.GrouponActivityPo;
import cn.edu.xmu.g12.g12ooadgoods.model.po.GrouponActivityPoExample;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.ActivityState;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.groupon.ModifyGrouponVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.groupon.NewGrouponVo;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class GrouponDao {
    private static final Logger logger = LoggerFactory.getLogger(GrouponDao.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    SkuPriceDao skuPriceDao;

    @Resource
    GrouponActivityPoMapper grouponActivityPoMapper;
    @Resource
    ShopPoMapper shopPoMapper;
    @Resource
    GoodsSpuPoMapper goodsSpuPoMapper;

    public List<ActivityState> getAllStates() {
        return ActivityState.getAllStates();
    }

    /**
     * Private Method..
     */
    private ListBo<GrouponOverview> packupGrouponActivityListBo(
            List<GrouponActivityPo> grouponList, @NotNull Integer page, @NotNull Integer pageSize) {
        var grouponBoList = grouponList.stream()
                .map(GrouponOverview::new).collect(Collectors.toList());

        // 返回分页信息
        var pageInfo = new PageInfo<>(grouponList);
        return new ListBo<>(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), grouponBoList);
    }

    // GET /groupons
    public ListBo<GrouponOverview> getAllGrouponByCustomer(@Nullable Integer timeline,
                                                           @Nullable Long spuId,
                                                           @Nullable Long shopId,
                                                           @NotNull Integer page,
                                                           @NotNull Integer pageSize) {
        var grouponExample = new GrouponActivityPoExample();
        var criteria = grouponExample.createCriteria();
        criteria.andStateEqualTo((byte)1);

        if (shopId != null) criteria.andShopIdEqualTo(shopId);
        if (spuId  != null) criteria.andGoodsSpuIdEqualTo(spuId);

        if (timeline != null) {
            // timeline : 0 还未开始的， 1 明天开始的，2 正在进行中的，3 已经结束的
            var now = LocalDateTime.now();
            switch (timeline) {
                case 0:
                    criteria.andBeginTimeGreaterThan(now);
                    break;
                case 1:
                    var nextday = now.plusDays(1);
                    var nextZero = LocalDateTime.of(
                            now.getYear(), now.getMonth(), now.getDayOfMonth(),0,0);
                    var doubleNextZero = LocalDateTime.of(
                            nextday.getYear(), nextday.getMonth(), nextday.getDayOfMonth(),0,0);
                    criteria.andBeginTimeGreaterThanOrEqualTo(nextZero);
                    criteria.andBeginTimeLessThan(doubleNextZero);
                    break;
                case 2:
                    criteria.andBeginTimeLessThan(now);
                    criteria.andEndTimeGreaterThan(now);
                    break;
                default:
                    criteria.andEndTimeLessThan(now);
            }
        }

        PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
        var grouponList = grouponActivityPoMapper.selectByExample(grouponExample);

        return packupGrouponActivityListBo(grouponList, page, pageSize);
    }

    // GET /shops/{shopId}/groupons
    public ListBo<GrouponOverview> getAllGrouponByAdmin(
            Long shopId,
            @Nullable Long spuId,
            @Nullable LocalDateTime beginTime,
            @Nullable LocalDateTime endTime,
            @Nullable Byte state,
            @NotNull Integer page,
            @NotNull Integer pageSize) {
        var grouponExample = new GrouponActivityPoExample();
        var criteria = grouponExample.createCriteria();
        criteria.andShopIdEqualTo(shopId);

        /* beginTime & endTime 必须同时为null或同时不为null */
        var now = LocalDateTime.now();
        if (beginTime != null) criteria.andEndTimeGreaterThan(now);
        if (endTime   != null) criteria.andBeginTimeLessThan(now);
        if (spuId     != null) criteria.andGoodsSpuIdEqualTo(spuId);
        if (state     != null) criteria.andStateEqualTo(state);

        PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
        var grouponList = grouponActivityPoMapper.selectByExample(grouponExample);

        return packupGrouponActivityListBo(grouponList, page, pageSize);
    }

    public ReturnObject<GrouponBo> newGroupon(Long shopId, Long spuId, NewGrouponVo vo) {
        var shopPo = shopPoMapper.selectByPrimaryKey(shopId);
        var spuPo = goodsSpuPoMapper.selectByPrimaryKey(spuId);

        var grouponExample = new GrouponActivityPoExample();
        grouponExample.createCriteria().andGoodsSpuIdEqualTo(spuId);
        var grouponPoList = grouponActivityPoMapper.selectByExample(grouponExample);
        if (grouponPoList.size() > 0) return new ReturnObject<>(ResponseCode.FIELD_NOTVALID);/* Spu预售已经存在 */

        var newPo = new GrouponActivityPo();
        newPo.setName(UUID.randomUUID().toString());
        newPo.setBeginTime(vo.getBeginTime());
        newPo.setEndTime(vo.getEndTime());
        newPo.setState((byte)0);
        newPo.setShopId(shopId);
        newPo.setGoodsSpuId(spuId);
        newPo.setStrategy(vo.getStrategy());
        newPo.setGmtCreate(LocalDateTime.now());
        newPo.setGmtModified(LocalDateTime.now());

        grouponActivityPoMapper.insertSelective(newPo);
        return new ReturnObject<>(new GrouponBo(
                newPo,
                new SpuOverview(spuPo),
                new IdNameOverview(shopPo.getId(), shopPo.getName())
        ));
    }

    // TODO 是不是 下线状态才能修改？
    public ResponseCode modifyGroupon(Long shopId, Long grouponId, ModifyGrouponVo vo) {
        var shopPo = shopPoMapper.selectByPrimaryKey(shopId);
        if (shopPo == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

        var grouponPo = grouponActivityPoMapper.selectByPrimaryKey(grouponId);
        if (grouponPo == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

        if (vo.isInvalid(grouponPo)) return ResponseCode.FIELD_NOTVALID;

//        if (grouponPo.getState() != 0) return ResponseCode.GROUPON_STATENOTALLOW;

        var updatePo = new GrouponActivityPo();
        updatePo.setId(grouponId);
        updatePo.setBeginTime(vo.getBeginTime());
        updatePo.setEndTime(vo.getEndTime());
        updatePo.setStrategy(vo.getStrategy());
        updatePo.setGmtModified(LocalDateTime.now());

        grouponActivityPoMapper.updateByPrimaryKeySelective(updatePo);
        return ResponseCode.OK;
    }

    public ResponseCode changeGrouponState(Long grouponId, Byte state) {
        var grouponPo = grouponActivityPoMapper.selectByPrimaryKey(grouponId);

        logger.info("<changeGrouponState> exist state="+grouponPo.getState()+" aim state="+state);

        if (state == 2 && grouponPo.getState() != 0) return ResponseCode.GROUPON_STATENOTALLOW;
        if (state == 1 && grouponPo.getState() != 0) return ResponseCode.GROUPON_STATENOTALLOW;
        if (state == 0 && grouponPo.getState() != 1) return ResponseCode.GROUPON_STATENOTALLOW;

        var updatePo = new GrouponActivityPo();
        updatePo.setId(grouponId);
        updatePo.setState(state);
        grouponActivityPoMapper.updateByPrimaryKeySelective(updatePo);
        return ResponseCode.OK;
    }
}
