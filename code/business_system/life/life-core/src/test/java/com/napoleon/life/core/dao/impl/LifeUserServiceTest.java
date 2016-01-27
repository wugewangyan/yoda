package com.napoleon.life.core.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.napoleon.life.core.entity.LifeUser;
import com.napoleon.life.core.service.LifeEmailService;
import com.napoleon.life.core.service.LifeUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-*.xml")
public class LifeUserServiceTest {
	
	@Autowired
	private LifeUserService lifeUserService;
	
	@Autowired
	private LifeEmailService lifeEmailService;

	
	
	@Test
	public void userRegister(){
		LifeUser user = new LifeUser();
		user.setAddress("湖北洪湖");
		user.setEmail("2732650059@qq.com");
		user.setPassword("wuge@199006193853");
		user.setPhone("13581878205");
		user.setSex("male");
		user.setUserName("吴革");
		
		this.lifeUserService.userRegister(user);
	}
	
}
