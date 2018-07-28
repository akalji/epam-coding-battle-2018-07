package com.epam.server.service;


import com.epam.server.model.User;

public interface UserService {
  public User findUserByEmail(String email);
  public void saveUser(User user);
}