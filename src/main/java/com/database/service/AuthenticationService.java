package com.database.service;

import java.util.List;

/**
 * Created by perfection on 17-3-1.
 */
public interface AuthenticationService {

    /**
     * 保存三元认证的手机号段信息
     * @param vo 号段信息的文件路径(包含文件名)
     */
    public void saveAuthenticationInfo(List<Object> vo);
}
