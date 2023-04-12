package com.scarf.authsvr.Controller;

import com.scarf.authsvr.DTO.JqGridDTO;
import com.scarf.authsvr.DTO.UserDTO;
import com.scarf.authsvr.Entity.ScarfUser;
import com.scarf.authsvr.Service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BaseController {

    private final BaseService baseService;

    @GetMapping(value = "/")
    public String indexPage() {

        return "index";
    }

    @GetMapping(value = "/userManage")
    public String userManagePage(Model model) {

        return "userManage";
    }

    @GetMapping(value = "/userAddPopUp")
    public String userAddPopUp() {

        return "userAddPopUp";
    }

    @GetMapping(value = "/userList.json")
    @ResponseBody
    public JqGridDTO getUserList() {
        JqGridDTO result = new JqGridDTO(baseService.getAllUserList());
        return result;
    }
}
