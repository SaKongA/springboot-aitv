package com.sakonga.aitv.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "replychild")
public class ReplyChild {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "type_id")
    @JsonProperty("type_id")
    private Long typeId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "number")
    private String number;

    @TableField(value = "keywords")
    @JsonProperty("keywords")
    private String keyWords;

    @TableField(value = "son")
    private String son_id;

    @TableField(value = "word_list")
    @JsonProperty("word_list")
    private String wordList;

    @TableField(value = "text_list")
    @JsonProperty("text_list")
    private String textList;

    @TableField(value = "miao")
    private int miao;

    @TableField(value = "son_list")
    private List<ReplySon> son;
}
