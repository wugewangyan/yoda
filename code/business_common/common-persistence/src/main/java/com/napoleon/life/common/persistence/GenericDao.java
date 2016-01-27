package com.napoleon.life.common.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface GenericDao<E extends Entity> {

	public int add(E entity);
	
	public int update(E entity);
	
	public int update(String sql, Object... args);
	
	public int delete(Serializable id);
	
	public int delete(String sql, Object... args);
	
	public int delete(E entity);
	
	public E get(Serializable id);
	
	public List query(String sql, Object... args);
	
	public Object queryOne(String sql, Object... args);
	
	public Map getMap(String sql, Object... args);
}
