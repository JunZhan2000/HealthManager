package com.example.web.service;

import com.example.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService  implements UserDetailsService {

    @Autowired
    private SystemUserService systemUserService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        //根据用户名从数据库查询对应记录
        User user = systemUserService.queryByPhone(phone);
        if (user == null) {
            throw new UsernameNotFoundException("user is not exists");
        }
        System.out.println("username is : " + user.getName() + ", password is :" + user.getPassword());

        return user;
    }
}
