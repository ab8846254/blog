package com.lrm.service;

import com.lrm.log.Type;
import com.lrm.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author Administrator
 */

public interface TypeService {


    /**
     * 新增分类
     * @param type
     * @return
     */
    Type saveType(Type type,Long userId);

    /**
     * 根据分类查询ID
     * @param id
     * @return
     */
    Type findTypeAndId(Long id);

    /**
     * 分页查询分类ID
     * @param pageable
     * @return
     */
    Page<Type> listType(Pageable pageable, HttpServletRequest request, BlogQuery blog);

    /**
     * 修改分类根据分类
     * @param id
     * @param type
     * @return
     */
    Type updateType(Long id,Type type);

    /**
     * 根据ID删除分类
     * @param id
     */
    void deleteType(Long id);

    /**
     *  通过名称啦查询数据库已经存在的分类
     * @param name
     * @return
     */
    Type getTypeByName(String name,Long userId);

    /**
     * 查询所有分类
     * @return
     */
    List<Type> listType();
    /**
     * 根据用户ID查询该用户下所有分类
     * @return
     */
    List<Type> listType(Long userId);

    /**
     * 首页标签
     * @return
     */
    List<Type> listTypeTop(Integer size);
}
