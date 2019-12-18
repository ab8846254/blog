package com.lrm.dao;

import com.lrm.log.Type;
import com.lrm.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Administrator
 */
public interface TypeReposiyory extends JpaRepository<Type, Long>,JpaSpecificationExecutor<Type> {
    /**
     * 根据用户的分类名称和用户ID查找对应的分类
     * @param name
     * @param userId
     * @return
     */
    Type findByNameAndUserId(String name,Long userId);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

    /**
     * 根据用户ID查询该用户下所有分类
     *
     * @param userId
     * @return
     */
    List<Type> findByUserId(Long userId);

}
