package com.database.controllers;

import com.database.vo.Customer;
import com.database.vo.TestRequestVo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by perfection on 17-2-28.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/hello",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Customer hello(HttpServletRequest request, @RequestBody TestRequestVo vo) {
        System.out.print("测试");
        Customer ke = new Customer() ;
        ke.setAge("24");
        ke.setSex("男");
        return ke;
    }
}
