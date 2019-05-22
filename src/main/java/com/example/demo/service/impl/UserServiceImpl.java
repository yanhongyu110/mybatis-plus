package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.config.datasource.ContextConst;
import com.example.demo.config.datasource.TargetDateSouce;
import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zuo
 * @since 2019-05-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@TargetDateSouce(ContextConst.DataSourceType.CLUSTER)
	public boolean insert(User user) {
		/**
		 * mybatis plus 提供两种crud 方式
		 * 1 直接使用实体类crud
		 */
		user.insert();
		/**
		 * 2 使用service dao crud
		 */
//		userMapper.insert(user);
		return true;
	}
	
	@TargetDateSouce(ContextConst.DataSourceType.MASTER)
    public Page<User> selectPage(Page<User> page, Wrapper<User> wrapper) {
		//Wrapper 提供sql拼接的各种方法 in like between order by 等等
		page.setRecords(userMapper.selectPage(page, wrapper));
        return page;
	}
}
