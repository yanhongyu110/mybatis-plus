package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zuo
 * @since 2019-05-21
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	@ResponseBody
	public String insertUser() {
		User user = new User();
		user.setUserName("ycat");
		user.setUserAddress("重庆");
		user.setUserAge(28);
		userService.insert(user);
		return "suuess";
	}
	@RequestMapping(value = "/queryUser", method = RequestMethod.POST)
	@ResponseBody
	public Page<User> queryUser() {
		Page<User> page = new Page<User>(1, 5);
		Wrapper<User> wrapper = new EntityWrapper<User>();
		return userService.selectPage(page, wrapper);
	}
}

