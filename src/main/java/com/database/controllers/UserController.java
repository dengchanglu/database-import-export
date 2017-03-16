package com.database.controllers;

import com.database.service.UserService;
import com.database.utils.FileUtil;
import com.database.vo.ParamVo;
import com.database.vo.UserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by 邓昌路 on 17-3-15.
 */
@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public UserInfoVo login(HttpServletRequest request, @RequestBody UserInfoVo vo) {
        log.info("登陆信息---账号:"+vo.getAccount()+"  密码:"+vo.getPassword());
        return userService.login(vo.getAccount(),vo.getPassword());
    }

    @RequestMapping(value = "/register",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public UserInfoVo register(HttpServletRequest request, @RequestBody UserInfoVo vo) {
        log.info("注册信息---账号:"+vo.getAccount()+"  密码:"+vo.getPassword()+"  用户名:"+vo.getUserName());
        return userService.register(vo);
    }
}
