package com.database.service;

import com.database.vo.SystemUser;

/**
 * Created by perfection on 17-3-1.
 */
public interface LoginService {

    /**
     * 系统用户登陆
     * @param userInfo 系统用户对象
     * @return
     */
    public boolean systemlogin(SystemUser userInfo);
}
