package com.richard.srblog.service;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

/**
 * The abstract class of all domain classes
 * @author Richard
 * @param <BaseDomain>
 */
public abstract class BaseService<BaseDomain> {
	
	protected CrudRepository<BaseDomain,Long> repository;
	
	protected CrudRepository<BaseDomain,Long> getRepository(){
		return this.repository;
	}
	
	/**
	 * to fetch an entity by id
	 * @param id
	 * @return
	 */
	public Optional<BaseDomain> findById(Long id){
		return getRepository().findById(id);
	}
	
	/**
	 * To fetch all entities
	 * @return
	 */
	public Iterable<BaseDomain> findAll(){
		return getRepository().findAll();
	}
	
	/**
	 * To save the entity
	 * @param entity
	 * @return
	 */
	public BaseDomain save(BaseDomain entity) {
		return getRepository().save(entity);
	}
	
	/**
	 * To delete an entity
	 * @param entity
	 */
	public void delete(BaseDomain entity) {
		getRepository().delete(entity);
	}
	
	/**
	 * to delete an entity by id
	 * @param id
	 */
	public void deleteById(Long id) {
		getRepository().deleteById(id);
	}

}
