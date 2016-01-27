package com.napoleon.life.core.util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.napoleon.life.common.util.NetUtil;
import com.napoleon.life.common.util.SerialNoGenerator;
import com.napoleon.life.core.dao.LifeSerialDao;
import com.napoleon.life.core.entity.LifeSerial;

@Component
public class LifeSerialNoService {
	private static final Logger logger = LoggerFactory
			.getLogger(LifeSerialNoService.class);

	@Autowired
	private LifeSerialDao lifeSerialDao;

	private SerialNoGenerator guid;

	@PostConstruct
	public void init() {
		String ipAddr = NetUtil.getLocalHost();
		logger.info("the local ip : {}", ipAddr);
		Long workerId = this.lifeSerialDao.getLifeSerialByIp(ipAddr);
		if (workerId == null) {
			LifeSerial lifeSerial = new LifeSerial();
			lifeSerial.setMachineAddr(ipAddr);
			this.lifeSerialDao.add(lifeSerial);
			workerId = lifeSerial.getId();
		}

		logger.info("The worker id : {}", workerId);
		guid = new SerialNoGenerator(workerId);
	}

	@PreDestroy
	public void destory() {
		logger.info("The lifeSerialNoService has bean destory");
	}

	public String getSerialNo(String command) {
		return command + guid.nextNo();
	}

}
