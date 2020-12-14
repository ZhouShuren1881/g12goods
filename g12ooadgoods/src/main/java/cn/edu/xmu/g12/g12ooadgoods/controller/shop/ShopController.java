package cn.edu.xmu.g12.g12ooadgoods.controller.shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @GetMapping("/states")
    public void getStates(HttpServletResponse response) {

    }
}
