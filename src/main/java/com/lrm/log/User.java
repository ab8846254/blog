package com.lrm.log;

import com.lrm.pojo.Blog;
import lombok.Data;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();

}
