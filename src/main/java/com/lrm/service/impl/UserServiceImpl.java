package com.lrm.service.impl;

import com.lrm.dao.UserRepository;
import com.lrm.log.User;
import com.lrm.service.UserService;
import com.lrm.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    /**
     * 检查用户名密码
     * @param username
     * @param password
     * @return
     */
    @Override
    public User checkUser(String username, String password) {
        User user = repository.findByUsernameAndPassword(username, MD5Utils.code(password));

        return user;
    }

    /**
     * 根据用户名查找用户是否存在
     * @param username
     * @return
     */
    @Override
    public User checkUserNameAndPhon(String username,String phone) {

        return repository.findByUsernameOrPhone(username,phone);
    }

    /**
     * 注册接口
     * @param user
     * @return
     */
    @Override
    public String register(User user) {
        //把密码进行加密
        String code = MD5Utils.code(user.getPassword());
        user.setPassword(code);
        User user1 = repository.save(user);
        if(user1!=null){
            return "新增用户成功";
        }
        return null;
    }


}
