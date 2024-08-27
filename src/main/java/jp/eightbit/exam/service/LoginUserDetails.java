package jp.eightbit.exam.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.eightbit.exam.entity.Authority;
import jp.eightbit.exam.entity.User;

public class LoginUserDetails implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Collection<? extends GrantedAuthority> authorities;

	public LoginUserDetails(User user, List<Authority> auth) {
		this.user = user;
		
		List<SimpleGrantedAuthority> list = new ArrayList<>();
		
		auth.forEach(el -> {
			list.add(new SimpleGrantedAuthority(el.getName()));
		});
		
		this.authorities = list;
		
	}
	
	public User getUser() {
		return this.user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
