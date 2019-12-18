package com.lrm.dao;

import com.lrm.log.Tag;
import com.lrm.log.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface TagReposIyoy extends JpaRepository<Tag,Long> , JpaSpecificationExecutor<Tag> {
    /**
     * 根据用户输入的标签名称和用户ID查询数据库是否有相同的标签
     * @param name
     * @return
     */
    Tag findByNameAndUserId(String name,Long userId);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

    /**
     * 根据当前用户ID查询出所属用户的所有标签
     * @param userId
     * @return
     */
    List<Tag> findByUserId(Long userId);


}
