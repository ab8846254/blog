package com.lrm.service;

import com.lrm.log.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    Type saveType(Type type);

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
    Page<Type> listType(Pageable pageable);

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
    Type getTypeByName(String name);

    /**
     * 查询所有分类
     * @return
     */
    List<Type> listType();

    /**
     * 首页标签
     * @return
     */
    List<Type> listTypeTop(Integer size);
}
