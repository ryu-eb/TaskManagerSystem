package jp.eightbit.exam.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class User {
	public static final int VOID_ID = 9;
	
	private int id;
	
	@NotEmpty(message="ユーザー名を入力してください")
	@Size(max=255, message="255文字以下にしてください")
	private String username;
	
	@NotEmpty(message="パスワードを入力してください")
	private String password;
	
	@Min(value=1, message="親ユーザーを選んでください")
	private int parentId;
	
	@Min(value=1, message="権限を選んでください")
	private int authId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getAuthId() {
		return authId;
	}
	public void setAuthId(int authId) {
		this.authId = authId;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", parentId=" + parentId + ", authId="
				+ authId + "]";
	}
}
