package com.roe21005.service;

import org.apache.ibatis.session.SqlSession;

import com.roe21005.dao.UserDao;
import com.roe21005.entity.User;
import com.roe21005.exception.UserException;
import com.roe21005.util.EncryptMd5;
import com.roe21005.util.MyBatisUtil;

public class UserService {
	
	public User login(String username, String password) throws UserException
	{
		
		System.out.println(username+" "+password);
		try (SqlSession session = MyBatisUtil.getSession()) 
		{
            UserDao userdao = session.getMapper(UserDao.class);
            User user = userdao.FindByUsername(username);
            if (user == null) 
            {
                System.out.println("UserService-Login:用户名不存在");
                throw new UserException("用户名不存在，请检查用户名");
            } 
            else 
            {
                if (user.getPassword().equals(EncryptMd5.String2MD5(password))) 
                {
                    System.out.println("UserService-Login:用户名密码匹配");
                    return user;
                } else {
                    System.out.println("UserService-Login:密码错误");
                    throw new UserException("密码错误，请重新输入密码");
                }
            }
        }
	}
	
	public void register(String username, String password) throws UserException{
        // 注册逻辑
        // 如果注册成功，执行相应的逻辑
        // 否则，抛出UserException
		System.out.println("coming register service");
		try (SqlSession session = MyBatisUtil.getSession()) {
			System.out.println("UserService-Register:正在获取");
			UserDao userdao = session.getMapper(UserDao.class);
			System.out.println("UserService-Register:findusername");
            User user = userdao.FindByUsername(username);
            System.out.println("UserService-Register:查询完成");
            if (user == null) {
            	System.out.println("UserService-Register:正在注册");
                userdao.AddUser(username, EncryptMd5.String2MD5(password));
                System.out.println("UserService-Register:注册成功");
            } else {
                System.out.println("UserService-Register:用户已存在");
                System.out.println(user.getUsername() + "&" + user.getPassword());
                throw new UserException("用户已存在！！！");
            }
        }
    }
}
