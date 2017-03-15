package com.database.service;

import com.database.exceptions.DIEException;
import com.database.vo.UserInfoVo;

/**
 * Created by perfection on 17-3-15.
 */
public interface UserService {

    public UserInfoVo login(String account,String password) throws DIEException;
}
