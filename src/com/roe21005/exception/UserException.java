package com.roe21005.exception;

public class UserException extends Exception{
	public UserException(String message)
	{
		super(message);
	}

	public static void main(String[] args) throws UserException {
		// TODO Auto-generated method stub
		throw new UserException("�û��������ڣ�");
	}

}
