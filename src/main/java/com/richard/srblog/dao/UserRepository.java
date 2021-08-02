package com.richard.srblog.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.richard.srblog.domain.User;


/**
 * The DAO for user
 * @extends {CrudRepository}
 * @author Richard
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * To find a user record by the user name
	 * @param name the user name
	 * @return
	 */
	Optional<User> findTopOneByNameOrderByIdDesc(String name);

}
