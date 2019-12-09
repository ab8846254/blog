package com.lrm.log;

import com.lrm.pojo.Blog;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "用户名不能为空")
    //@Pattern(regexp = "/^[A-Za-z0-9]{4,15}$/",message ="用户名必须包括数字和字母的组合,长度为4-15位" )
    @Size(min = 4,max = 15,message ="用户名长度必须为4-15位" )
    private String username;


    @NotBlank(message = "密码不能为空")
    //@Pattern(regexp="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$",message="密码至少包含 数字和英文，长度6-20")
    private String password;
    private String email;


    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String phone;


    private String avatar;
    private Integer type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();

}
