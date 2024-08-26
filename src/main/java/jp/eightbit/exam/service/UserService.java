package jp.eightbit.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.mapper.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Transactional
	public int loginable(String name, String password) {
		User user = userMapper.getByName(name);
		
		if (user == null) return 0;
		else if (user.getPassword().equals(password)) return user.getId();
		else return -1;
	}
	
	@Transactional
	public User getById(int id) {
		return userMapper.getById(id);
	}

	@Transactional
	public List<User> getByParentId(int id){
		return userMapper.getByParentId(id);
	}
	
	@Transactional
	public User getByUserName(String userName) {
		return userMapper.getByName(userName);
	}
	
	@Transactional
	public int deleteById(int id) {
		return userMapper.deleteById(id);
	}
	
	@Transactional
	public List<User> getAll(){
		return userMapper.getAll();
	}
}
