package jp.eightbit.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.mapper.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Transactional
	public User getById(int id) {
		return userMapper.getById(id);
	}
	
	@Transactional
	public User getByName(String nm) {
		return userMapper.getByName(nm);
	}

	@Transactional
	public List<User> getByParent(User voider){
		return userMapper.getByParent(voider);
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
	public List<User> getAll(int parent){
		return userMapper.getAll(parent);
	}
	
	@Transactional
	public List<User> getRootAdmin(){
		return userMapper.getRootAdmin();
	}
	
	@Transactional
	public int updateParent(int id, int parent) {
		return userMapper.updateToMyParent(id, parent);
	}
	
	@Transactional
	public int registRoot(User user) {
		//rootユーザー登録処理
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userMapper.add(user);
		user.setParentId(user.getId());
		userMapper.updateParent(user);
		
		//voidユーザー登録処理
		User voiduser = new User();
		voiduser.setUsername(VoidUtil.genName(user.getUsername()));
		voiduser.setPassword(user.getPassword());
		voiduser.setAuthId(5);
		voiduser.setParentId(user.getId());
		userMapper.add(voiduser);
		
		return 0;
	}
	
	@Transactional
	public User getRootByParentId(int id) {
		return userMapper.getRootByParentId(id);
	}
}
