package com.example.web.service;

import com.example.web.entity.User;
import com.example.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemUserService {

    @Autowired
    private UserMapper userMapper;

    public User queryByPhone(String phone) {
        return userMapper.queryByPhone(phone);
    }
}