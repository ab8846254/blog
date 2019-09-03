package com.lrm.service.impl;

import com.lrm.NotFoundException;
import com.lrm.dao.TagReposIyoy;
import com.lrm.log.Tag;
import com.lrm.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public Page<Tag> listTag(Pageable pageable) {
        Page<Tag> tagPage = tagReposIyoy.findAll(pageable);
        return tagPage;
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
    public Tag getTagByName(String name) {
        return tagReposIyoy.findByName(name);
    }

    /**
     * 查询所有标签
     *
     * @return
     */
    @Override
    public List<Tag> listTag() {
        return tagReposIyoy.findAll();
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
