package cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion;

import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import cn.edu.xmu.oomall.other.model.CustomerDTO;
import cn.edu.xmu.oomall.other.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceUnion {

    @Autowired
    ICustomerService customerService;

    public ReturnObject<CustomerDTO> findCustomerByUserId(Long userId) {
        return new Trans<CustomerDTO>().oomall(customerService.findCustomerByUserId(userId));
    }
}
