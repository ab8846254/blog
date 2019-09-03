package com.lrm.service;

import com.lrm.log.Tag;
import com.lrm.log.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Administrator
 */
public interface TagService {
    /**
     * 新增标签
     *
     * @param tag
     * @return
     */
    Tag saveTag(Tag tag);

    /**
     * 根据标签ID查询标签
     *
     * @param id
     * @return
     */
    Tag findByTagAndId(Long id);

    /**
     * 分页查询标签
     *
     * @param pageable
     * @return
     */
    Page<Tag> listTag(Pageable pageable);


    /**
     * 修改分类根据标签
     *
     * @param id
     * @param type
     * @return
     */
    Tag updateType(Long id, Tag tag);

    /**
     * 根据ID删除标签
     *
     * @param id
     */
    void deleteTagByIAndId(Long id);

    /**
     * 通过名称查询数据库已经存在的标签
     *
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * 查询所有标签
     */
    List<Tag> listTag();

    /**
     * 页面博客的标签ID
     * @param ids
     * @return
     */
    List<Tag> listTag(String ids);

    /**
     *  首页标签
     * @param size
     * @return
     */
    List<Tag> listTag(Integer size);
}
