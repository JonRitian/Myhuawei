package com.zx.dao;

import com.zx.domain.User;

public interface UserDao {
    public User findByName (String name);

    public void save(User user);
}
