package com.gen.service;

import com.gen.entity.User;

import java.util.List;

public interface UserService {
    User getUserByName(String name);

    boolean isExist(String name);

    void addUser(User user);

    //根据用户查询角色信息
    List<String> getUserRole(String principal);

    //获取用户角色权限信息
    List<String> getUserPermission(List<String> roles);


}
