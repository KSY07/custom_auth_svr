package com.scarf.authsvr.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping(value = "/")
    public String indexPage() {

        return "index";
    }

    @GetMapping(value = "/userManage")
    public String userManagePage() {

        return "userManage";
    }
}
