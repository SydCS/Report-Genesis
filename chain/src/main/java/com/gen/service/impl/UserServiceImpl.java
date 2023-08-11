package com.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gen.entity.User;
import com.gen.mapper.UserMapper;
import com.gen.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public boolean isExist(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return userMapper.selectCount(wrapper) == 1;
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    //根据用户查询角色信息
    @Override
    public List<String> getUserRole(String principal) {
        return userMapper.getUserRole(principal);
    }

    //获取用户角色权限信息
    @Override
    public List<String> getUserPermission(List<String> roles) {
        return userMapper.getUserPermission(roles);
    }

}
