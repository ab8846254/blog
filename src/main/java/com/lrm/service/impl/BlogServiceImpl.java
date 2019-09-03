package com.lrm.service.impl;

import com.lrm.NotFoundException;
import com.lrm.dao.BlogRepository;
import com.lrm.log.Type;
import com.lrm.pojo.Blog;
import com.lrm.service.BlogService;
import com.lrm.util.MarkdownUtils;
import com.lrm.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class BlogServiceImpl implements BlogService {

    /**
     * 注入持久层接口
     */
    @Autowired
    private BlogRepository blogRepository;

    /**
     * 根据ID查询博客
     *
     * @param id
     * @return
     */
    @Override
    public Blog getBlog(Long id) {

        Blog blog = blogRepository.findById(id).get();
        return blog;
    }

    /**
     * 分页动态查询
     *
     * @param pageable
     * @param blog
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //首先先构建一个装查询条件的集合容器类型为Predicate
                List<Predicate> list = new ArrayList<>();
                //2.通过if判断blog对象中也就是前端用户是否选择填写了这选项判断是否为空
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    //3.若用户选择了则将其添加到集合中 这里渠通过root对象取到用户填写的值然后放进容器
                    list.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                //4.以上同理
                if (blog.getTypeId() != null) {
                    list.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    list.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                //5.最后在这里将集合转换为一个数组然后执行查询SQL语句
                cq.where(list.toArray(new Predicate[list.size()]));
                //6.若用户什么都没选择则返回null
                return null;
            }
        }, pageable);
    }

    /**
     * 分页
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    /**
     * 新增博客和更新博客公用一个方法
     *
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        // 如果没有id就代表是新增博客
        if (blog.getId() == null) {
            //博客创建时间
            blog.setCreateTime(new Date());

            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            //否则就是更新博客设置狭隘博客更新时间
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    /**
     * 修改博客
     *
     * @param id
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        //先根据ID查询一遍是否存在该博客
        Blog blog1 = blogRepository.findById(id).get();
        if (blog1 == null) {
            throw new NotFoundException("不存在该博客");
        }
        BeanUtils.copyProperties(blog1, blog);
        Blog blog2 = blogRepository.save(blog1);
        return blog2;
    }

    /**
     * 删除博客
     *
     * @param id
     */
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    /**
     * 首页最新推荐
     * @param size
     * @return
     */
    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    /**
     * 全局搜索
     * @param query
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }



    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).get();
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }
}
