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
                System.out.println("UserService-Login:�û���������");
                throw new UserException("�û��������ڣ������û���");
            } 
            else 
            {
                if (user.getPassword().equals(EncryptMd5.String2MD5(password))) 
                {
                    System.out.println("UserService-Login:�û�������ƥ��");
                    return user;
                } else {
                    System.out.println("UserService-Login:�������");
                    throw new UserException("���������������������");
                }
            }
        }
	}
	
	public void register(String username, String password) throws UserException{
        // ע���߼�
        // ���ע��ɹ���ִ����Ӧ���߼�
        // �����׳�UserException
		System.out.println("coming register service");
		try (SqlSession session = MyBatisUtil.getSession()) {
			System.out.println("UserService-Register:���ڻ�ȡ");
			UserDao userdao = session.getMapper(UserDao.class);
			System.out.println("UserService-Register:findusername");
            User user = userdao.FindByUsername(username);
            System.out.println("UserService-Register:��ѯ���");
            if (user == null) {
            	System.out.println("UserService-Register:����ע��");
                userdao.AddUser(username, EncryptMd5.String2MD5(password));
                System.out.println("UserService-Register:ע��ɹ�");
            } else {
                System.out.println("UserService-Register:�û��Ѵ���");
                System.out.println(user.getUsername() + "&" + user.getPassword());
                throw new UserException("�û��Ѵ��ڣ�����");
            }
        }
    }
}
