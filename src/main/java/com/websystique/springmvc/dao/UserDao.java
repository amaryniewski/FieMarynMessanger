package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.User;

import java.util.List;

public interface UserDao {

	User findById(int id);

	void saveUser(User user);
	
	void deleteUserByNickname(String nickname);
	
	List<User> findAllUsers();

	User findUserByNickname(String nickname);
}
