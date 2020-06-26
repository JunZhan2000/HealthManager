package com.example.web.mapper;

import com.example.web.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //根据用户电话号码查询用户记录
    @Select("Select * from users where phone = #{phone}")
    User queryByPhone(String phone);

    //根据用户id查询用户记录
    @Select("Select * from users where id = ${id}")
    User queryByID(Integer id);

    //新建用户记录
    Integer insertUser(User user);

    //更新一条用户记录
    Integer updateUser(User user);
}
