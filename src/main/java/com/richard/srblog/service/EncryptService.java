package com.richard.srblog.service;

public interface EncryptService {
     /**
      * To hash a string by using bcrypt method
      * @param input
      * @return {String} the hashed string
      */
	public String bcryptEncrypt(String input);

	
	/**
	 * To check if hashed string a and b are matched
	 * @param a 
	 * @param b 
	 * @return {Boolean} the check result
	 */
	public Boolean bcryptCheck(String a, String b);

}
