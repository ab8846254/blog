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

    /**
     * 根据用户名查找用户是否已经注册
     * @param username
     * @return
     */
    User checkUserNameAndPhon(String username,String phone);

    /**
     * 注册用户
     * @param user
     * @return
     */
    String register(User user);
}
