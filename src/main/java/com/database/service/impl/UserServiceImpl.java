package com.database.service.impl;

import com.database.exceptions.DIEException;
import com.database.service.UserService;
import com.database.vo.UserInfoVo;
import org.springframework.stereotype.Service;

/**
 * Created by perfection on 17-3-15.
 */
@Service
public class UserServiceImpl implements UserService{

    public UserInfoVo login(String account, String password) throws DIEException{
        if(null!=account){
            throw DIEException.USER_INFO_LOGIN_ACCOUNT_NULL;
        }
        if(null!=password){
            throw DIEException.USER_INFO_LOGIN_PASSWORD_NULL;
        }

        return null;
    }

}
