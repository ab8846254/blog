package com.lrm.service;

import com.lrm.pojo.Blog;
import com.lrm.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface BlogService {
    /**
     * 根据ID查询博客
     * @param id
     * @return
     */
   Blog getBlog(Long id);

    /**
     * 分页查询
     * @param pageable
     * @param blog
     * @return
     */
   Page<Blog> listBlog(Pageable pageable , BlogQuery blog);

    /**
     * 分页
     * @param pageable
     * @return
     */
    Page<Blog> listBlog(Pageable pageable );
    /**
     *新增博客
     * @param blog
     * @return
     */
   Blog saveBlog(Blog blog);

    /**
     * 修改博客
     * @param id
     * @param blog
     * @return
     */
   Blog updateBlog(Long id,Blog blog);

    /**
     * 删除博客
     * @param id
     */
   void deleteBlog(Long id);

    /**
     * 首页最新推荐
     * @param size
     * @return
     */
   List<Blog> listBlogTop(Integer size);

    /**
     * 全局搜索
     * @param query
     * @param pageable
     * @return
     */
   Page<Blog>listBlog(String query,Pageable pageable);

    /**
     * 根据标签ID查询所有标签
     * @param tagId
     * @param pageable
     * @return
     */
   Page<Blog> listBlog(Long tagId,Pageable pageable );

    /**
     * 归档页面查询
     * @return
     */
    Map<String,List<Blog>>archivesBlog();

    Long countBlog();
   Blog getAndConvert(Long id);
}
