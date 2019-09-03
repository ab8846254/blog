package com.lrm.log;

import com.lrm.pojo.Blog;
import lombok.Data;

import javax.persistence.*;
import org.hibernate.validator.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@Entity
@Table(name = "t_type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;
    @OneToMany(mappedBy = "type")
    private List<Blog>blogs = new ArrayList<>();
}
