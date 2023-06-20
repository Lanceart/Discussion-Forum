package com.linqing.service;

import com.linqing.pojo.User;

public interface UserService {

    User checkUser(String username, String password);

}
