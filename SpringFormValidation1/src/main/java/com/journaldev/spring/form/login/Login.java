package com.journaldev.spring.form.login;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login {
	
	byte[] bytesOfPass;
	MessageDigest md;
	private String user;
	private String password;
	
	public String getUser(){
		return user;
	}
	
	public void setUser(String user){
		this.user=user;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
	
	 public String ReturnedHash(String pass) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		 md = MessageDigest.getInstance("MD5");
		 bytesOfPass = pass.getBytes("UTF-8");
		 md.reset();
		 md.update(bytesOfPass);
		 byte[] thedigest = md.digest();
		 BigInteger bigInt = new BigInteger(1,thedigest);
		 String hashtext = bigInt.toString(16);
		 while(hashtext.length() < 32){
			 hashtext = "0"+hashtext;
		 }
		 return hashtext;
	 }
}
