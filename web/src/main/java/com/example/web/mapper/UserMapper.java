package com.example.web.mapper;

import com.example.web.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("Select * from users where phone = #{phone}")
    User queryByPhone(String phone);
}
