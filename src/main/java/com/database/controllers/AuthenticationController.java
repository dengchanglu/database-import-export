package com.database.controllers;

import com.database.main.Start;
import com.database.service.AuthenticationService;
import com.database.utils.FileUtil;
import com.database.vo.ParamVo;
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
 * Created by perfection on 17-3-1.
 */
@Controller
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/saveAuthenticationPhoneData",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public boolean saveAuthenticationPhoneData(HttpServletRequest request, @RequestBody ParamVo vo) {
        log.info("处理请求中");
        try {
            List<Object> authenticationVos = FileUtil.analysisAuthenticationPhoneInfoFile(vo.getFilePath());
            authenticationService.saveAuthenticationInfo(authenticationVos);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
