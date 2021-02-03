package com.example.spring_mybatis.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.example.spring_mybatis.model.User;

public interface UserMapper {
	public User selectUserByID(int id);

	public List<User> selectUsersByName(String userName);

	public void addUser(User user);

	public void updateUser(User user);

	public void deleteUser(int id);

	public void updateBatch(@Param("list") List<User> list);
}