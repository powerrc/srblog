package com.richard.srblog.service.impl;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.richard.srblog.service.EncryptService;

/**
 * The service class implement for encrypt service
 * @author Richard
 *
 */
@Service("EncryptService")
public class EncryptServiceImpl implements EncryptService {


	/** @inheritdoc */
	@Override
	public String bcryptEncrypt(String input) {
		return BCrypt.hashpw(input, BCrypt.gensalt());
	}
	
	/** @inheritdoc */
	@Override
	public Boolean bcryptCheck(String a , String b) {
		 return BCrypt.checkpw( a, b);
	}

}
