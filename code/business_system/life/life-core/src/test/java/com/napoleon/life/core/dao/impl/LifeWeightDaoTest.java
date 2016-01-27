package com.napoleon.life.core.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.napoleon.life.core.dao.LifeWeightDao;
import com.napoleon.life.core.entity.LifeWeight;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-*.xml")
public class LifeWeightDaoTest {
	
	@Autowired
	private LifeWeightDao lifeWeightDao;

	@Test
	public void add(){
		LifeWeight entity = new LifeWeight();
		entity.setUserId("wuge");
		entity.setWeight(new BigDecimal(65));
		entity.setBmi(new Double(65/(1.65 * 1.65)).toString());
		entity.setMeasurementTime(new Timestamp(new Date().getTime()));
		entity.setYear(2015);
		entity.setMonth(12);
		entity.setDay(5);
		entity.setWeek(6);
		
		int rows = this.lifeWeightDao.add(entity);
		Assert.assertEquals(1, rows);
	}
	
}
