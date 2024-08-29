package jp.eightbit.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.mapper.AuthMapper;
import jp.eightbit.exam.mapper.UserMapper;

@Service
public class LoginUserService implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AuthMapper authMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.getByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		String auth = authMapper.getById(user.getAuthId()).getName();
		
		return new LoginUserDetails(user, auth);
	}
	
	@Transactional
	public int save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userMapper.add(user);
		return user.getId();
	}
	
	@Transactional
	public User getUser() {
		String username = null;
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof LoginUserDetails) {
			username = ((LoginUserDetails) principal).getUsername();
		}
		if (username != null) {
			return userMapper.getByName(username);
		}
		
		return null;
	}
	
	public int getId() {
		return this.getUser().getId();
	}
	
	public int getAuthId() {
		return this.getUser().getAuthId();
	}
	
	@Transactional
	public User getVoid() {
		User root = userMapper.getRootByParentId(getUser().getParentId());
		return userMapper.getByName(VoidUtil.genName(root.getUsername()));
	}
	
	public int getVoidId() {
		return getVoid().getId();
	}

}
