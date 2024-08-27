package jp.eightbit.exam.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class Template {
	private int id;
	
	@Size(min=1, message="1文字以上にしてください")
	@Size(max=50, message="50文字以下にしてください")
	private String title;
	
	@Size(min=1, message="1文字以上にしてください")
	@Size(max=500, message="500文字以下にしてください")
	private String description;
	
	private int createrId;
	
	private int parentId;
	
	@Min(value=1, message="権限の範囲を選択してください")
	private int authRangeId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCreaterId() {
		return createrId;
	}
	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}
	public int getAuthRangeId() {
		return authRangeId;
	}
	public void setAuthRangeId(int authRangeId) {
		this.authRangeId = authRangeId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
}
