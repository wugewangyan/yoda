package com.napoleon.life.core.dao;

import com.napoleon.life.common.persistence.GenericDao;
import com.napoleon.life.core.entity.LifeSerial;

public interface LifeSerialDao extends GenericDao<LifeSerial> {

	public Long getLifeSerialByIp(String ipAddr);
	
}
