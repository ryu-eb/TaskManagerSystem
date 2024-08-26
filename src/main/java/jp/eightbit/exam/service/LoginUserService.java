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
import jp.eightbit.exam.mapper.UserMapper;

@Service
public class LoginUserService implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.getByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new LoginUserDetails(user);
	}
	
	@Transactional
	public int save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userMapper.add(user);
		return user.getId();
	}
	
	@Transactional
	public User getLoginUser() {
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
	
	@Transactional
	public int getLoginUserId() {
		return this.getLoginUser().getId();
	}
	
	@Transactional
	public int getLoginUserAuthId() {
		return this.getLoginUser().getAuthId();
	}

}
