package com.lrm.dao;

import com.lrm.pojo.Blog;
import com.lrm.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Administrator
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    @Query("select b from Blog b where b.recommend=true ")
    List<Blog> findTop(Pageable pageable);

    /**
     * 全局搜索
     * @param query
     * @param pageable
     * @return
     */
    @Query("select b from Blog  b where b.title like ?1 or b.content like ?1")
    Page<Blog>findByQuery(String query,Pageable pageable);

    /**
     * 归档查询
     * @return
     */
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroudYear();
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);



}
