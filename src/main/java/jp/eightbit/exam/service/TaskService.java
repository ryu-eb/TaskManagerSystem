package jp.eightbit.exam.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.eightbit.exam.entity.History;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.mapper.TaskMapper;

@Service
public class TaskService {
	@Autowired
	TaskMapper taskMapper;
	
	@Transactional
	public List<Task> getAllRelateTask(User user, List<User> fam, List<History> hist, User voider){
		
		if (fam.size() == 0 || hist.size() == 0) return null;
		
		//idを抽出したリストの取得
		List<Integer> family = fam.stream().map(el -> el.getId()).distinct().collect(Collectors.toList());
		List<Integer> inactives = hist.stream().map(el -> el.getTaskId()).distinct().collect(Collectors.toList());
		
		//ユーザーidとタスクidを使って、同じparentIdをもつユーザーが作成したタスクを取得
		List<Task> relatetasks = taskMapper.getRelateTask(family, inactives);
		
		//上記リストで権限が該当するもののみを取得
		List<Task> authlist = new ArrayList<>();
		for (Task tk : relatetasks) {
			if (user.getAuthId() == 2) {// ログインユーザーがrootの場合
				authlist.add(tk);
			}else if (tk.getCreaterId() == user.getId()) {// タスク作成者がログインユーザーである場合
				authlist.add(tk);
			}else if(tk.getCreaterId() != voider.getId()) {//タスク作成者がvoidではない場合
				if (tk.getAuthRangeId() >= user.getAuthId()) {//　タスク権限idがログインユーザー権限idより大きい(下位の権限)場合
					authlist.add(tk);
				}
			}
		}
		
		return authlist.stream().sorted(Comparator.comparing(Task::getDue)).collect(Collectors.toList());
	}
	
	@Transactional
	public Task getById(int id) {
		return taskMapper.getById(id);
	}
	
	@Transactional
	public int add(Task task) {
		taskMapper.add(task);
		return task.getId();
	}
	
	@Transactional
	public int deleteById(int id) {
		return taskMapper.deleteById(id);
	}
	
	@Transactional
	public int updateToVoid(int id, int voidid){
		return taskMapper.updateToVoid(id, voidid);
	}
	
	@Transactional
	public List<Task> getAllByCreaterId(int id){
		return taskMapper.getAllByCreaterId(id);
	}
	
	@Transactional
	public List<Integer> getAllIdByCreaterId(int id){
		List<Integer> list = taskMapper.getAllByCreaterId(id).stream().map(el->el.getId()).collect(Collectors.toList());
		
		return list;
	}
	
	
}
