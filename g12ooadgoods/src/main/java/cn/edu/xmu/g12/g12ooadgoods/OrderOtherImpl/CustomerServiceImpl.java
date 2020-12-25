package cn.edu.xmu.g12.g12ooadgoods.OrderOtherImpl;

import cn.edu.xmu.g12.g12ooadgoods.mapper.AuthUserPoMapper;
import cn.edu.xmu.oomall.util.ResponseCode;
import cn.edu.xmu.oomall.util.ReturnObject;
import cn.edu.xmu.oomall.other.model.CustomerDTO;
import cn.edu.xmu.oomall.other.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired(required = false)
    AuthUserPoMapper authUserPoMapper;

    public ReturnObject<CustomerDTO> findCustomerByUserId(Long userId) {
        var userPo = authUserPoMapper.selectByPrimaryKey(userId);
        if (userPo == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        var customerDTO = new CustomerDTO(userPo.getUserName(), userPo.getName());
        return new ReturnObject<>(customerDTO);
    }
}
