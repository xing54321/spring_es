package com.example.spring_mybatis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_mybatis.mapper.UserMapper;
import com.example.spring_mybatis.model.User;

@Service
public class UserService  {
    @Autowired
    private UserMapper userMapper;

    public User selectUserByID(int id) {
        return userMapper.selectUserByID(id);
    }
    
    public List<User> selectUsersByName(String name) {
        return userMapper.selectUsersByName(name);
    }
    
    public void addUser(User user) {
        userMapper.addUser(user);
    }
    
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
    
    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }
    
    public void updateBatch() {
    	List<User> list=new ArrayList<>();
    	list.add(new User(1,"/http://localhost:8080/小明?name=123",22,"高新区"));
    	list.add(new User(2,"/http://localhost:8080/小红?name=123",21,"滨海区"));
        userMapper.updateBatch(list);
    }
}
