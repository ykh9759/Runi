package com.example.runi.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String index() {

        return "admin/index";
    }

    @GetMapping("/order-list")
    public String orderList() {

        return "admin/page/order_list";
    }

}
