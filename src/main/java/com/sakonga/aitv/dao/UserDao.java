package com.sakonga.aitv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakonga.aitv.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao extends BaseMapper<UserPojo> {

    @Select("SELECT * FROM user WHERE mobile = #{mobile}")
    UserPojo selectByMobile(@Param("mobile") String mobile);

    @Select("SELECT * FROM user WHERE mobile = #{mobile} AND password = #{password}")
    UserPojo selectByMobileAndPassword(@Param("mobile") String mobile, @Param("password") String password);

    @Select("SELECT * FROM user WHERE token = #{token}")
    UserPojo getUserByToken(String token);
}
