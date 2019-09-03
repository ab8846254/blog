package com.lrm.service;

import com.lrm.log.User;

/**
 * @author Administrator
 */
public interface UserService {
    /**
     * 检查用户名和密码接口
     * @param username
     * @param password
     * @return
     */
    User checkUser(String username,String password);

}
