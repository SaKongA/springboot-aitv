package com.sakonga.aitv.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class UserPojo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "mobile")
    private String mobile;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "score")
    private int score;

    @TableField(value = "token")
    private String token;

    @TableField(value = "createtime")
    private Long createtime;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRequest {
        private String mobile;
        private String password;
    }
}
