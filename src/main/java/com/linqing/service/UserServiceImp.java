package com.linqing.service;

import com.linqing.dao.UserRepository;
import com.linqing.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password){
        User user = userRepository.findByUsernameAndPassword(username,password);
        return user;
    }

}
