package com.example.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
	public String insertUser() {
		User user = new User();
		user.setUserName("ycat");
		user.setUserAddress("重庆");
		user.setUserAge(28);
		userService.insert(user);
		return "suuess";
	}
}

