package com.example.spring_mybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_mybatis.model.User;
import com.example.spring_mybatis.service.UserService;

@RestController
@RequestMapping("/web")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/selectUserByID")
    public User selectUserByID(){
        return userService.selectUserByID(1);
    }
    
    @GetMapping("/selectUsersByName")
    public List<User> selectUsersByName(){
        return userService.selectUsersByName("Jack");
    }
    
    @GetMapping("/addUser")
    public String addUser() {
    	userService.addUser(new User("Haha",22,"高新区"));
    	return "插入成功";
    }
    
    @GetMapping("/updateUser")
    public String updateUser(){
    	userService.updateUser(new User(1,"Jack",22,"高新区"));
        return "更新成功";
    }
    
    @GetMapping("/updateBatch")
    public String updateBatch() {
    	userService.updateBatch();
    	return "批量更新成功";
    }
    
    @GetMapping("/deleteUser")
    public String deleteUser(){
    	userService.deleteUser(1);
        return "删除成功";
    }
    
}