package com.napoleon.life.core.dao.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.napoleon.life.core.bean.SumMonthRunBean;
import com.napoleon.life.core.bean.SumWeekRunBean;
import com.napoleon.life.core.dao.LifeRunDao;
import com.napoleon.life.core.entity.LifeRun;
import com.napoleon.life.core.enums.QuarterEnum;
import com.napoleon.life.core.service.LifeEmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-*.xml")
public class LifeRunDaoTest {
	
	@Autowired
	private LifeRunDao lifeRunDao;
	
	@Autowired
	private LifeEmailService lifeEmailService;

	
	@Test
	public void add(){
		LifeRun entity = new LifeRun();
		entity.setUserId("吴革");
		entity.setRunType("长跑");
		entity.setDurationTime(45);
		entity.setDistance(3410);
		entity.setAddress("传媒大学");
		entity.setRunTime(new Timestamp(new Date().getTime()));
		entity.setYear(2015);
		entity.setMonth(12);
		entity.setDay(5);
		entity.setWeek(6);
		
		int rows = this.lifeRunDao.add(entity);
		Assert.assertEquals(1, rows);
	}
	
	@Test
	public void update(){
		LifeRun entity = new LifeRun();
		entity.setId(2L);
		entity.setUserId("刘玉勇");
		entity.setRunType("长跑－短跑");
		entity.setDurationTime(50);
		entity.setDistance(4210);
		entity.setAddress("传媒大学");
		entity.setRunTime(new Timestamp(new Date().getTime()));
		entity.setYear(2014);
		entity.setMonth(10);
		entity.setDay(6);
		entity.setWeek(15);
		
		int rows = this.lifeRunDao.update(entity);
		Assert.assertEquals(1, rows);
	}
	
	
	@Test
	public void findById(){
		LifeRun lifeRun = this.lifeRunDao.get(2L);
		Assert.assertEquals(2014, lifeRun.getYear().intValue());
	}
	
	
	@Test
	public void delete(){
		int rows = this.lifeRunDao.delete(2L);
		Assert.assertEquals(1, rows);
	}
	
	
	@Test
	public void findByDay(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = new Date();
		Date endTime = new Date();
		try {
			startTime = format.parse("2015-12-07");
			endTime = format.parse("2015-12-08");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<LifeRun> lifeRuns = this.lifeRunDao.findByDay("吴革", startTime, endTime);
		
		Assert.assertNotNull(lifeRuns);
		Assert.assertEquals(7, lifeRuns.size());
		for(LifeRun lifeRun : lifeRuns){
			System.out.println(lifeRun.getDistance() + "---->" + lifeRun.getDurationTime());
		}
	}
	
	@Test
	public void findByYearAndWeek(){
		List<LifeRun> lifeRuns = this.lifeRunDao.findByYearAndWeek("吴革", 2015, 44);
		Assert.assertNotNull(lifeRuns);
		Assert.assertEquals(7, lifeRuns.size());
		for(LifeRun lifeRun : lifeRuns){
			System.out.println(lifeRun.getDistance() + "---->" + lifeRun.getDurationTime());
		}
	}
	
	
	@Test
	public void findByYearAndMonth(){
		List<LifeRun> lifeRuns = this.lifeRunDao.findByYearAndMonth("吴革", 2015, 12);
		Assert.assertNotNull(lifeRuns);
		Assert.assertEquals(25, lifeRuns.size());
		for(LifeRun lifeRun : lifeRuns){
			System.out.println(lifeRun.getRunType() + "---->" + lifeRun.getAddress());
		}
	}
	
	
	
	@Test
	public void findByYearAndQuarter(){
		List<LifeRun> lifeRuns = this.lifeRunDao.findByYearAndQuarter("吴革", 2015, QuarterEnum.QUARTER_4);
		Assert.assertNotNull(lifeRuns);
		Assert.assertEquals(25, lifeRuns.size());
		for(LifeRun lifeRun : lifeRuns){
			System.out.println(lifeRun.getDay() + "---->" + lifeRun.getWeek() + "---->" + lifeRun.getRunTime());
		}
	}
	
	
	@Test
	public void groupByYearAndWeek(){
		List<SumWeekRunBean> lifeRuns = this.lifeRunDao.groupByYearAndWeek("吴革", 2015);
		Assert.assertNotNull(lifeRuns);
		Assert.assertEquals(5, lifeRuns.size());
		for(SumWeekRunBean lifeRun : lifeRuns){
			System.out.println(lifeRun.getTotalTime() + "---->" + lifeRun.getTotalDistance());
		}
	}
	
	
	@Test
	public void groupByYearAndMonth(){
		List<SumMonthRunBean> lifeRuns = this.lifeRunDao.groupByYearAndMonth("吴革", 2015);
		Assert.assertNotNull(lifeRuns);
		Assert.assertEquals(2, lifeRuns.size());
		for(SumMonthRunBean lifeRun : lifeRuns){
			System.out.println(lifeRun.getTotalTime() + "---->" + lifeRun.getTotalDistance());
		}
	}
}
