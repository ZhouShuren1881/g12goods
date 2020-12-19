package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.mapper.*;
import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
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

    static class HasShopException extends Exception {
        HasShopException() {
            super("Customer Has a Shop!");
        }
    }

    static class InsertFailException extends Exception {
        InsertFailException() {
            super("插入失败!");
        }
    }

    static class UpdateFailException extends Exception {
        UpdateFailException() {
            super("更新失败!");
        }
    }

    public ReturnObject<ShopPo> createShop(Long userId, String name) {
        var session = sqlSessionFactory.openSession();
        try {
            var userMapper = session.getMapper(AuthUserPoMapper.class);
            var user = userMapper.selectByPrimaryKey(userId);
            if (user.getDepartId() != 0) throw new HasShopException();

            var shopMapper = session.getMapper(ShopPoMapper.class);
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
        } catch (InsertFailException e) {
            session.rollback();
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        } finally {
            session.close();
        }
    }

    public ResponseCode modifyShop(Long shopId, String name) {
        var session = sqlSessionFactory.openSession();
        try {
            var shopMapper = session.getMapper(ShopPoMapper.class);
            var shopGet = shopMapper.selectByPrimaryKey(shopId);
            if (shopGet == null || shopGet.getId() == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

            var shopSet = new ShopPo();
            shopSet.setId(shopId);
            shopSet.setName(name);
            shopSet.setGmtModified(LocalDateTime.now());
            var rows = shopMapper.updateByPrimaryKey(shopSet);

            if (rows == 0) throw new UpdateFailException();
            session.commit();
            return ResponseCode.OK;
        } catch (UpdateFailException e) {
            session.rollback();
            return ResponseCode.INTERNAL_SERVER_ERR;
        } finally {
            session.close();
        }
    }

    public ResponseCode changeShopState(Long shopId, Byte state) {
        var session = sqlSessionFactory.openSession();
        try {
            var shopMapper = session.getMapper(ShopPoMapper.class);
            var shopGet = shopMapper.selectByPrimaryKey(shopId);
            if (shopGet == null || shopGet.getId() == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

            var shopSet = new ShopPo();
            shopSet.setId(shopId);
            shopSet.setState(state);
            shopSet.setGmtModified(LocalDateTime.now());
            var rows = shopMapper.updateByPrimaryKey(shopSet);

            if (rows == 0) throw new UpdateFailException();
            session.commit();
            return ResponseCode.OK;
        } catch (UpdateFailException e) {
            session.rollback();
            return ResponseCode.INTERNAL_SERVER_ERR;
        } finally {
            session.close();
        }
    }

}
