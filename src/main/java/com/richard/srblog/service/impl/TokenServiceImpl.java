package com.richard.srblog.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.richard.srblog.dao.TokenRepository;
import com.richard.srblog.domain.Token;
import com.richard.srblog.domain.User;
import com.richard.srblog.service.BaseService;
import com.richard.srblog.service.TokenService;

/**
 * The domain service class implement for token
 * @author Richard
 *
 */
@Service("TokenService")
public class TokenServiceImpl extends BaseService<Token> implements TokenService {


	@Value("${com.richard.srblog.token.lifetime}")
	private Long TOKEN_LIFETIME;
	
	@Autowired
	protected TokenRepository repository;
	
	protected TokenRepository getRepository() {
		return this.repository;
	}
	
	/** @inheritdoc */
	@Override
	public Optional<Token> findByTokenString(String tokenString) {
		return this.getRepository().findOneByTokenStringOrderByIdDesc(tokenString);
	}

	/** @inheritdoc */
	@Override
	public Token addUser(User user) {
		//using uuid to generate the token string
		String tokenString = UUID.randomUUID().toString();
		Token newToken = new Token().setExpireTime(new Date(System.currentTimeMillis() + TOKEN_LIFETIME*1000))
									.setTokenString(tokenString)
									.setUserId(user.getId());
		
		this.save(newToken);
				
		return newToken;
	}
	

}
