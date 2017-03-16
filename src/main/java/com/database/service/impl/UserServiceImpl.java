package com.database.service.impl;

import com.database.dao.DIEDatabaseDao;
import com.database.exceptions.DIEException;
import com.database.service.UserService;
import com.database.vo.UserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 邓昌路 on 17-3-15.
 */
@Service
public class UserServiceImpl implements UserService{

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private DIEDatabaseDao<UserInfoVo> databaseDao;

    public UserInfoVo login(String account, String password) throws DIEException{
        if(null==account||null!=password){
            throw DIEException.USER_INFO_LOGIN_PARAM_NULL;
        }
        List<UserInfoVo> vos = databaseDao.getIndex("account",account,1);
        if(vos.size()==0){
            throw DIEException.USER_INFO_LOGIN_QUERY_NULL;
        }
        if(!vos.get(0).getPassword().equals(password)){
            throw DIEException.USER_INFO_LOGIN_QUERY_ERROR;
        }
        return vos.get(0);
    }

    public UserInfoVo register(UserInfoVo vo) throws DIEException{
        if(null==vo.getAccount()||null==vo.getEmail()||null==vo.getPassword()){
            throw DIEException.USER_INFO_REGISTER_NULL;
        }
        if(databaseDao.saveIndex(vo)){
         return vo;
        }
        return null;
    }

}
