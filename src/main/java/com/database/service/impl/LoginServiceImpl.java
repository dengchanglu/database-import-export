package com.database.service.impl;

import com.database.service.LoginService;
import com.database.vo.SystemUser;
import org.springframework.stereotype.Service;

/**
 * Created by perfection on 17-3-1.
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Override
    public boolean systemlogin(SystemUser userInfo) {
        return false;
    }
}
