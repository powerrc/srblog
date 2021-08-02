package com.richard.srblog.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.richard.srblog.domain.Token;


/**
 * The DAO for token.
 * As a quick solution , token info is stored in a memory table of the database (mysql) 
 * @extends {CrudRepository}
 * @author Richard
 *
 */
public interface TokenRepository extends CrudRepository<Token, Long> {

	public Optional<Token> findOneByTokenStringOrderByIdDesc(String tokenString);
}
