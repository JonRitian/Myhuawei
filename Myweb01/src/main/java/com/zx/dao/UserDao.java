package com.zx.dao;

import com.zx.domain.User;

public interface UserDao {
    public User findByName (String name);

    public User findByUserName(String username);
    public void save(User user2);


    int updateStatus(String activeCode);
}
