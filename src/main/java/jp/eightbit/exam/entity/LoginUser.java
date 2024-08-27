package jp.eightbit.exam.entity;

import java.util.List;

public class LoginUser extends User{
	
	private List<Integer> authoritiesId;

	public List<Integer> getAuthoritiesId() {
		return authoritiesId;
	}

	public void setAuthoritiesId(List<Integer> authoritiesId) {
		this.authoritiesId = authoritiesId;
	}
	
	public void castUser(User user) {
		super.setId(user.getId());
		super.setUsername(user.getUsername());
		super.setPassword(user.getPassword());
		super.setParentId(user.getParentId());
		super.setAuthId(user.getAuthId());
	}
	
	public boolean isInIdList(int id) {
		return this.authoritiesId.contains(id);
	}
}
