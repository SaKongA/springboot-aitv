package com.sakonga.aitv.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "reply")
public class Reply {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long user_id;

    @TableField(value = "token")
    private String token;

    @TableField(value = "name")
    private String name;

    @TableField(value = "memo")
    private String memo;

    @TableField(value = "number")
    private Integer number;

    @TableField(value = "child_list")
    private List<ReplyChild> child_list;
}
