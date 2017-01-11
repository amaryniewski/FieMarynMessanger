package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.UserDao;
import com.websystique.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    public User findById(int id) {
        return dao.findById(id);
    }

    public void saveUser(User user) {
        dao.saveUser(user);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateUser(User user) {
        User entity = dao.findById(user.getId());
        if (entity != null) {
            entity.setFirstName(user.getFirstName());
            entity.setNickname(user.getNickname());
        }
    }

    public void deleteUserByNickname(String nickname) {
        dao.deleteUserByNickname(nickname);
    }

    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    public User findUserByNickname(String nickname) {
        return dao.findUserByNickname(nickname);
    }

    public boolean isUserNicknameUnique(Integer id, String nickname) {
        User user = findUserByNickname(nickname);
        return (user == null || ((id != null) && (user.getId() == id)));
    }

    public boolean isUserPasswordCorrect(String nickname, String password) {
        User entity = dao.findUserByNickname(nickname);
        boolean flag = false;

        if (isPasswordLengthCorrect(password, entity)) {
            if(isPasswordContentCorrect(password, entity)) {
                flag = true;
            }
        }

        if (password.length() > entity.minPasswordLength && password.length() < entity.maxPasswordLength) {

            if (!entity.getPassword().isEmpty() && !password.isEmpty() && password.equals(entity.getPassword())) {
                flag = true;
            }
        }
        return flag;
    }

    public boolean nicknameExists(String nickname) {

        Optional<User> someUser = Optional.ofNullable(dao.findUserByNickname(nickname));
        if (!someUser.isPresent()) {
            return false;
        }
        return true;
    }

    private boolean isPasswordLengthCorrect(String password, User entity) {
        return (password.length() > entity.minPasswordLength) && (password.length() < entity.maxPasswordLength);
    }

    private boolean isPasswordContentCorrect(String password, User entity) {
        return password.equals(entity.getPassword());
    }
}
