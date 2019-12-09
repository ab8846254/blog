package com.lrm.dao;

import com.lrm.log.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Administrator
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);

    /**
     * 根据用户名和电话查找用户是否已经注册
     * @param username
     * @param phone
     * @return
     */
   // @Query(value = "select u from t_user u where u.username=?1 or u.phone=?1")
    User findByUsernameOrPhone(String username ,String phone);


}
