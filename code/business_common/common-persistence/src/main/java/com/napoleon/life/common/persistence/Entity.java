package com.napoleon.life.common.persistence;

import java.io.Serializable;

/**
 * Entity base class, support identity;
 * @author wuge
 *
 * @param <E>
 */
public interface Entity<E extends java.io.Serializable> extends Serializable {

	/**
	 * get Identity of the entity
	 * @return id
	 */
	public E getId();
	
	/**
	 * Set the identity of the entity to the specified value.
	 * @param id
	 */
	public void setId(E id);
}
