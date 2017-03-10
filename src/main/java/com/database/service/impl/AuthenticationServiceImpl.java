package com.database.service.impl;

import com.database.constants.HbaseConstants;
import com.database.service.AuthenticationService;
import com.database.utils.HbaseUtil;
import com.database.vo.AuthenticationVo;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by perfection on 17-3-1.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Override
    public void saveAuthenticationInfo(List<Object> vo) {
        try {
            HbaseUtil.saveMulDatas(HbaseConstants.AUTHENTICATION_PHONE_INFO_HBASE_TABLE, vo,AuthenticationVo.class,"getPhone");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
