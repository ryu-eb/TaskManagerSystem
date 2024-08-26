package jp.eightbit.exam.entity;

public class History {
	private int id;
	private String done;
	private int doneUserId;
	private String dbl;
	private int dblUserId;
	private int taskId;
	private int statusId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDone() {
		return done;
	}
	public void setDone(String done) {
		this.done = done;
	}
	public int getDoneUserId() {
		return doneUserId;
	}
	public void setDoneUserId(int doneUserId) {
		this.doneUserId = doneUserId;
	}
	public String getDbl() {
		return dbl;
	}
	public void setDbl(String dbl) {
		this.dbl = dbl;
	}
	public int getDblUserId() {
		return dblUserId;
	}
	public void setDblUserId(int dblUserId) {
		this.dblUserId = dblUserId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
}
