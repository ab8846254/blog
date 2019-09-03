package com.lrm.service.impl;

import com.lrm.NotFoundException;
import com.lrm.dao.TypeReposiyory;
import com.lrm.log.Type;
import com.lrm.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Type saveType(Type type) {
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
    public Page<Type> listType(Pageable pageable) {
        Page<Type> typePage = typeReposiyory.findAll(pageable);
        return typePage;
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
    public Type getTypeByName(String name) {
        Type byName = typeReposiyory.findByName(name);
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
