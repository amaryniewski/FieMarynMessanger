package com.websystique.springmvc.service;

import com.websystique.springmvc.model.User;

import java.util.List;

public interface UserService {

    User findById(int id);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserByNickname(String nickname);

    List<User> findAllUsers();

    User findUserByNickname(String nickname);

    boolean isUserNicknameUnique(Integer id, String nickname);

    boolean isUserPasswordCorrect(String nickname, String password);

    boolean nicknameExists(String nickname);

}
