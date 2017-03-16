package com.database.service;

import com.database.exceptions.DIEException;
import com.database.vo.UserInfoVo;

/**
 * Created by 邓昌路 on 17-3-15.
 */
public interface UserService {

    /**
     * 登陆服务
     * @param account 账号
     * @param password 密码
     * @return 用户信息
     * @throws DIEException 异常信息
     */
    public UserInfoVo login(String account,String password) throws DIEException;

    /**
     * 注册服务
     * @param vo 用户信息
     * @return 用户信息
     * @throws DIEException 异常信息
     */
    public UserInfoVo register(UserInfoVo vo) throws DIEException;
}
