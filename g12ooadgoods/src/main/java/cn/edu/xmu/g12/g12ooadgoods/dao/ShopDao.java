package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.mapper.*;
import cn.edu.xmu.g12.g12ooadgoods.model.po.*;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ShopDao {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    class HasShopException extends Exception {
        HasShopException() {
            super("Customer Has a Shop!");
        }
    }

    class SameNameShopException extends Exception {
        SameNameShopException() {
            super("已经有一个同名店铺!");
        }
    }

    class InsertFailException extends Exception {
        InsertFailException() {
            super("插入失败!");
        }
    }

    public ReturnObject<ShopPo> createShop(Long userId, String name) {
        var session = sqlSessionFactory.openSession();
        try {
            var userMapper = session.getMapper(AuthUserPoMapper.class);
            var user = userMapper.selectByPrimaryKey(userId);
            if (user.getDepartId() != 0) throw new HasShopException();

            var shopMapper = session.getMapper(ShopPoMapper.class);
            var shopReq = new ShopPoExample();
            shopReq.createCriteria().andNameEqualTo(name);
            var sameNameShop = shopMapper.selectByExample(shopReq);
            if (sameNameShop != null && sameNameShop.size() != 0) throw new SameNameShopException();

            var shop = new ShopPo();
            shop.setName(name);
            shop.setState((byte)0);
            shop.setGmtCreate(LocalDateTime.now());
            shop.setGmtModified(LocalDateTime.now());

            int rows = shopMapper.insert(shop);
            if (rows == 0) throw new InsertFailException();
            session.commit();
            return new ReturnObject<>(shop);
        } catch (HasShopException e) {
            session.rollback();
            return new ReturnObject<>(ResponseCode.USER_HASSHOP);
        } catch (SameNameShopException e) {
            session.rollback();
            return new ReturnObject<>(ResponseCode.SAME_NAME_SHOP);
        } catch (InsertFailException e) {
            session.rollback();
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        } finally {
            session.close();
        }
    }
}
