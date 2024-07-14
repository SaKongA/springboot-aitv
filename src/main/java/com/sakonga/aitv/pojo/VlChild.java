package com.sakonga.aitv.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "vlChild")
public class VlChild {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "library_id")
    @JsonProperty("library_id")
    private Long libraryId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "file_name")
    @JsonProperty("file_name")
    private String fileName;

    @TableField(value = "image")
    private String image;

    @TableField(value = "image_type")
    @JsonProperty("image_type")
    private int imageType;

    @TableField(value = "number")
    private int number;

    @TableField(value = "createtime")
    private Long createtime;

    @TableField(value = "status")
    private int status;

    @TableField(value = "miao")
    private int miao;

    // 计算属性，不映射到数据库
    @JsonProperty("voice_url")
    private String voiceUrl;
}
