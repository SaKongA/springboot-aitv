package com.sakonga.aitv.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "ReplyChild")
public class ReplySon {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "reply_id")
    @JsonProperty("reply_id")
    private Long replyId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "number")
    private String number;

    @TableField(value = "file_name")
    @JsonProperty("file_name")
    private String fileName;

    // 计算属性，不映射到数据库
    @JsonProperty("voice_url")
    private String voiceUrl;
}
