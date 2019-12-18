package com.lrm.service.impl;

import com.lrm.NotFoundException;
import com.lrm.dao.TypeReposiyory;
import com.lrm.log.Type;

import com.lrm.log.User;
import com.lrm.pojo.Blog;
import com.lrm.service.TypeService;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeReposiyory typeReposiyory;
    /**
     * 新增分类
     * @param type
     * @return
     */
    @Transactional
    @Override
    public Type saveType(Type type,Long userId) {
        type.setUserId(userId);
        Type save = typeReposiyory.save(type);

        return save;
    }
    /**
     * 根据分类ID查询分类
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Type findTypeAndId(Long id) {
        Optional<Type> typeId = typeReposiyory.findById(id);
        Type type = typeId.get();
        return type;
    }

    /**
     * 分页查询分类ID
     * @param pageable
     * @return
     */
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable , HttpServletRequest request, BlogQuery blog) {


        return typeReposiyory.findAll(new Specification<Type>() {
            @Override
            public Predicate toPredicate(Root<Type> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
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
                    list.add(cb.equal(root.<Type>get("userId"), blog.getUserId()));
                }
                //5.最后在这里将集合转换为一个数组然后执行查询SQL语句
                cq.where(list.toArray(new Predicate[list.size()]));
                //6.若用户什么都没选择则返回null
                return null;
            }
        },pageable);

    }
    /**
     * 修改分类根据分类
     * @param id
     * @param type
     * @return
     */
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Optional<Type> type1 = typeReposiyory.findById(id);
        Type type2 = type1.get();
       if(type2==null){
           throw new NotFoundException("不存在该分类");
       }
       //查出来的对象用把新值赋值给旧值
        BeanUtils.copyProperties(type,type2);
        //最后保存
        return  typeReposiyory.save(type2);
    }

    /**
     * 根据ID删除分类
     * @param id
     */
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeReposiyory.deleteById(id);
    }

    /**
     * 根据名称查询分类
     * @param name
     * @return
     */
    @Override
    public Type getTypeByName(String name,Long userId) {
        Type byName = typeReposiyory.findByNameAndUserId(name,userId);
        return byName;
    }

    /**
     * 查询所有的分类
     * @return
     */
    @Override
    public List<Type> listType() {

        return typeReposiyory.findAll();
    }

    /**
     * 根据用户ID查询该用户下所有分类
     * @param userId
     * @return
     */
    @Override
    public List<Type> listType(Long userId) {
        return  typeReposiyory.findByUserId(userId);
    }

    /**
     *  首页分类
     * @param size
     * @return
     */
    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,6,sort);
        return typeReposiyory.findTop(pageable);
    }
}
