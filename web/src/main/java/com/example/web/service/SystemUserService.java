package com.example.web.service;

import com.example.web.entity.User;
import com.example.web.mapper.UserMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SystemUserService {

    @Autowired
    private UserMapper userMapper;

    //根据用户id查询用户记录
    public User queryByID(Integer id){
        return userMapper.queryByID(id);
    }

    //通过电话号码查询用户记录
    public User queryByPhone(String phone) {
        return userMapper.queryByPhone(phone);
    }

    //插入一条用户记录
    public Integer insertUser(User user){
        return userMapper.insertUser(user);
    }

    //更新一条用户记录
    public Integer updateUser(User user){
        return userMapper.updateUser(user);
    }
}