package com.napoleon.life.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.abel533.echarts.Option;
import com.napoleon.life.core.entity.LifeRun;
import com.napoleon.life.core.entity.LifeUser;
import com.napoleon.life.core.service.LifeRunService;
import com.napoleon.life.core.util.Constants;
import com.napoleon.life.web.interceptor.AuthAccess;

@Controller
public class LifeRunController {

	@Autowired
	private LifeRunService lifeRunService;
	
	@AuthAccess
	@RequestMapping(value = "/month-run", method = RequestMethod.GET)
    public String findByYearAndMonth(HttpServletRequest request, Integer year, Integer month, Model model) {
		year = 2015;
		month = 12;
		LifeUser lifeUser = (LifeUser)request.getSession().getAttribute(Constants.USER_SESSION_NAME);
        List<LifeRun> runs = this.lifeRunService.findByYearAndMonth(lifeUser.getEmail(), year, month);
        model.addAttribute("runs", runs);
        return "run/month-run-list";
    }
	
	@AuthAccess
	@RequestMapping(value = "/run-index", method = RequestMethod.GET)
    public String lifeRunIndex() {
        return "run/run-index";
    }
	
	
	@AuthAccess
	@RequestMapping(value = "/findYearRunDetail", method = RequestMethod.GET)
	@ResponseBody
    public Option findYearRunDetail(HttpServletRequest request, Integer year) {
		LifeUser lifeUser = (LifeUser)request.getSession().getAttribute(Constants.USER_SESSION_NAME);
        return this.lifeRunService.findByYear(lifeUser.getEmail(), year);
        
    }
	
	@AuthAccess
	@RequestMapping(value = "/groupByYearAndWeek", method = RequestMethod.GET)
	@ResponseBody
    public Option groupByYearAndWeek(HttpServletRequest request, Integer year) {
		LifeUser lifeUser = (LifeUser)request.getSession().getAttribute(Constants.USER_SESSION_NAME);
        return this.lifeRunService.groupByYearAndWeek(lifeUser.getEmail(), year);
        
    }
}
