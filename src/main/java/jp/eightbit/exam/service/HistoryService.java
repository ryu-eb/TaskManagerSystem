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

@Service
public class HistoryService {
	@Autowired
	HistoryMapper historyMapper;
	
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
	public int toggleTask(User user, Task task, List<Status> list, boolean isDbl) {
		History hist = historyMapper.getByTaskId(task.getId());
		int nowstat = hist.getStatusId();
		int userid = user.getId();
		boolean flg = false;
		
		for (Status st : list) {
			int index = st.getId();
			if (flg) {
				switch(index) {
				case 2://実行中へ
					hist.setStatusId(2);
					hist.setDoneUserId(userid);
					break;
				case 4://精査中へ
					hist.setStatusId(4);
					hist.setDbl(MyUt.dateToString(new Date()));
					break;
				case 3://精査待ちへ
					if (isDbl) {
						hist.setStatusId(3);
						hist.setDblUserId(userid);
						break;
					}
				case 5://完了へ
					hist.setStatusId(5);
					hist.setDone(MyUt.dateToString(new Date()));
					break;
				}
				break;
			}
			//statIdが同じだったらその次の値へ
			if (nowstat == index) flg = true;
		}
		
		return historyMapper.save(hist);
	}
}
