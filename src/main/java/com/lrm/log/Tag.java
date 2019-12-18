package com.lrm.log;

import com.lrm.pojo.Blog;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@Entity
@Table(name = "t_tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long userId;
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs =new ArrayList<>();
}
