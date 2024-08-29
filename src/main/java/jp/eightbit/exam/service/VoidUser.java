package jp.eightbit.exam.service;

import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.mapper.UserMapper;

public class VoidUser {
	private User voiduser;
	
	public VoidUser() {
		UserService us = new UserService();
		LoginUserService lus = new LoginUserService();
		
		User root = us.getRootByParentId(lus.getLoginUser().getParentId());
		this.voiduser = us.getByName("void_" + root.getUsername());
	}
	
	public int getId() {
		return this.voiduser.getId();
	}
	
	//使う予定なし、念のため
	public String getUserName() {
		return this.voiduser.getUsername();
	}
}
