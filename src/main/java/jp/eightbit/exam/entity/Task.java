package jp.eightbit.exam.entity;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Task {
	private int id;
	
	@Size(min=1, message="1文字以上にしてください")
	@Size(max=50, message="500文字以上にしてください")
	private String title;
	
	@Size(min=1, message="1文字以上にしてください")
	@Size(max=500, message="500文字にしてください")
	private String description;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotEmpty(message="日付を選択してください")
	private String due;
	
	@NotNull(message="精査有無を選択してください")
	private boolean dblCheck;
	
	private int createrId;
	
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
	public String getDue() {
		return due;
	}
	public void setDue(String due) {
		this.due = due;
	}
	public boolean isDblCheck() {
		return dblCheck;
	}
	public void setDblCheck(boolean dblCheck) {
		this.dblCheck = dblCheck;
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
}
