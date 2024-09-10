package com.roe21005.dao;

import org.apache.ibatis.annotations.Param;

import com.roe21005.entity.User;

public interface UserDao {
	User FindByUsername(String username);
	void AddUser(@Param("username") String username, @Param("password") String password);
}
