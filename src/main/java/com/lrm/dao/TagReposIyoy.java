package com.lrm.dao;

import com.lrm.log.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface TagReposIyoy extends JpaRepository<Tag,Long> {
    /**
     * 根据用户输入的标签名称查询数据库是否有相同的标签
     * @param name
     * @return
     */
    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
