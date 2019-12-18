package com.lrm.service.impl;

import com.lrm.NotFoundException;
import com.lrm.dao.TagReposIyoy;
import com.lrm.log.Tag;
import com.lrm.log.Type;
import com.lrm.log.User;
import com.lrm.service.TagService;
import com.lrm.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagReposIyoy tagReposIyoy;

    /**
     * 新增标签
     *
     * @param tag
     * @return
     */
    @Override
    public Tag saveTag(Tag tag) {
        return tagReposIyoy.save(tag);
    }

    /**
     * 根据标签查询ID
     *
     * @param id
     * @return
     */
    @Override
    public Tag findByTagAndId(Long id) {
        return tagReposIyoy.findById(id).get();
    }

    /**
     * 分页查询标签ID
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Tag> listTag(Pageable pageable, HttpServletRequest request , BlogQuery blog) {

        return tagReposIyoy.findAll(new Specification<Tag>() {
            @Override
            public Predicate toPredicate(Root<Tag> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //1.首先先构建一个装查询条件的集合容器类型为Predicate
                List<Predicate> list = new ArrayList<>();

                HttpSession session = request.getSession();
                User user1 = null;
                // BlogQuery blogQuery = null;
                //2.获取User对象
                User user = (User) request.getSession().getAttribute("user");
                //3.通过if判断UserId是否为空
                if (user == null) {
                    //进来就说明用户是在首页进行点击分类，此时session拿不到值就给她new一个值并且给其ID赋个默认值
                    user1 = new User();
                    user1.setId(3L);
                    Long user1Id = user1.getId();
                    //  blogQuery = new BlogQuery();
                    blog.setUserId(user1Id);
                } else {
                    Long userId = user.getId();
                    //  blogQuery = new BlogQuery();
                    blog.setUserId(userId);
                }
                if (blog.getUserId() != null) {
                    list.add(cb.equal(root.<Tag>get("userId"), blog.getUserId()));
                }
                //5.最后在这里将集合转换为一个数组然后执行查询SQL语句
                cq.where(list.toArray(new Predicate[list.size()]));
                //6.若用户什么都没选择则返回null
                return null;
            }
        },pageable);

    }

    /**
     * 修改标签根据标签
     *
     * @param id
     * @param tag
     * @return
     */
    @Override
    public Tag updateType(Long id, Tag tag) {
        Optional<Tag> tagid = tagReposIyoy.findById(id);
        Tag tag1 = tagid.get();
        if (tag1 == null) {
            throw new NotFoundException("不存在该标签");
        } else {
            BeanUtils.copyProperties(tag, tag1);
            //最后保存
            return tagReposIyoy.save(tag);
        }
    }

    /**
     * 根据ID删除标签
     *
     * @param id
     */
    @Override
    public void deleteTagByIAndId(Long id) {
        tagReposIyoy.deleteById(id);
    }

    /**
     * 通过名称查询数据库已经存在的标签
     *
     * @param name
     * @return
     */
    @Override
    public Tag getTagByName(String name,Long userId) {
        return tagReposIyoy.findByNameAndUserId(name,userId);
    }

    /**
     * 查询所有标签
     * @return
     */
    @Override
    public List<Tag> listTag() {
        return tagReposIyoy.findAll();
    }

    /**
     * 查询所有标签
     *
     * @return
     */
    @Override
    public List<Tag> listTag(Long userId) {
        return tagReposIyoy.findByUserId(userId);
    }

    /**
     * 页面博客的标签ID
     *
     * @param ids
     * @return
     */
    @Override
    public List<Tag> listTag(String ids) {
        return tagReposIyoy.findAllById(ConversionToString(ids));
    }

    /**
     * 首页标签
     * @param size
     * @return
     */
    @Override
    public List<Tag> listTag(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);
        return tagReposIyoy.findTop(pageable);
    }

    /**
     * 根据用户ID查询当前用户下面的所有标签信息
     * @param request
     * @param blog
     * @return
     */
    @Override
    public List<Tag> listTag(HttpServletRequest request, BlogQuery blog) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Long userId = user.getId();
        return tagReposIyoy.findByUserId(userId);
    }

    /**
     * 字符串转换
     *
     * @param ids
     * @return
     */
    public List<Long> ConversionToString(String ids) {
        //构建一个list集合
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idsArry = ids.split(",");
            for (int i = 0; i < idsArry.length; i++) {
                list.add(new Long(idsArry[i]));
            }
        }
        return list;
    }
}
