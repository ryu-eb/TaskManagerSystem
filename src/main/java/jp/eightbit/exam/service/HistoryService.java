package jp.eightbit.exam.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.eightbit.exam.entity.History;
import jp.eightbit.exam.entity.Status;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.mapper.HistoryMapper;
import jp.eightbit.exam.mapper.UserMapper;

@Service
public class HistoryService {
	@Autowired
	HistoryMapper historyMapper;
	@Autowired
	UserMapper userMapper;
	
	@Transactional
	public History getById(int id) {
		return historyMapper.getById(id);
	}
	
	@Transactional
	public History getByTaskId(int id) {
		return historyMapper.getByTaskId(id);
	}
	
	@Transactional
	public List<History> getNotDoneTaskHist(){
		return historyMapper.getNotDoneTaskHist();
	}
	
	@Transactional
	public List<History> getDoneTaskHist(){
		return historyMapper.getDoneTaskHist();
	}

	@Transactional
	public List<History> getActiveByDoneUserId(int id){
		return historyMapper.getActiveByDoneUserId(id);
	}
	@Transactional
	public List<History> getDoneByDoneUserId(int id){
		return historyMapper.getDoneByDoneUserId(id);
	}

	@Transactional
	public List<History> getActiveByDblUserId(int id){
		return historyMapper.getActiveByDblUserId(id);
	}
	@Transactional
	public List<History> getDoneByDblUserId(int id){
		return historyMapper.getDoneByDblUserId(id);
	}
	
	@Transactional
	public int add(Task task){
		History hist = new History();
		hist.setTaskId(task.getId());
		hist.setStatusId(1);
		
		historyMapper.add(hist);
		return hist.getId();
	}

	@Transactional
	public int deleteById(int id) {
		return historyMapper.deleteById(id);
	}
	
	@Transactional
	public int deleteByTaskId(int id) {
		return historyMapper.deleteByTaskId(id);
	}
	
	@Transactional
	public int updateToVoid(int id){
		int done = historyMapper.updateDoneToVoid(id, User.VOID_ID);
		int dbl = historyMapper.updateDblToVoid(id, User.VOID_ID);
		return done + dbl;
	}
	
	@Transactional
	public String toggleTask(User user, Task task, List<Status> list, boolean isDbl) {
		History hist = historyMapper.getByTaskId(task.getId());
		int nowstat = hist.getStatusId();
		int userid = user.getId();
		boolean flg = false;
		
		for (Status st : list) {
			int index = st.getId();
			if (flg) {
				switch(index) {
				case 2://実行中へ
					MyUt.print("実行中へ変更");
					hist.setStatusId(2);
					hist.setDoneUserId(userid);
					break;
				case 4://精査中へ
					MyUt.print("精査中へ変更");
					User another = userMapper.getById(hist.getDoneUserId());
					if (another.getAuthId() < user.getAuthId()) return "を精査中へ変更出来ませんでした。自分以下の権限を持つユーザーの精査のみ可能です。";
					if (hist.getDoneUserId() == user.getId()) return "を精査中へ変更出来ませんでした。作業者以外のみ精査可能です。";
					hist.setStatusId(4);
					hist.setDblUserId(userid);
					break;
				case 3://精査待ちへ
					MyUt.print("精査待ちへ変更");
					hist.setDone(MyUt.dateToString(new Date()));
					if (isDbl) {
						if (hist.getDoneUserId() != user.getId()) return "を精査待ちへ変更出来ませんでした。作業者のみ作業中から精査待ちに変更可能です。";
						hist.setStatusId(3);
						break;
					}
					if (hist.getDoneUserId() != user.getId()) return "を完了へ変更出来ませんでした。作業者のみ作業中から完了に変更可能です。";
				case 5://完了へ
					MyUt.print("完了へ変更");
					if (hist.getDblUserId() != user.getId() && hist.getDblUserId() != 0) return "を完了へ変更出来ませんでした。精査者のみ精査中から完了に変更可能です。";
					hist.setStatusId(5);
					if (isDbl) hist.setDbl(MyUt.dateToString(new Date()));
					break;
				}
				break;
			}
			//statIdが同じだったらその次の値へ
			if (nowstat == index) {
				flg = true;
			}
		}
		
		int save = historyMapper.save(hist);
		
		switch (save) {
			case 1:
				return null;
			default:
				return "を変更できませんでした。SQLのエラー";
		}
	}
}
