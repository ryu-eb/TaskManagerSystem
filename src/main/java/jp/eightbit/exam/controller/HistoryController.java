package jp.eightbit.exam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.eightbit.exam.entity.History;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.HistoryService;
import jp.eightbit.exam.service.LoginUserService;
import jp.eightbit.exam.service.MyUt;
import jp.eightbit.exam.service.TaskService;
import jp.eightbit.exam.service.UserService;

@Controller
public class HistoryController {
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	UserService userService;
	@Autowired
	HistoryService historyService;
	@Autowired
	TaskService taskService;
	
	@GetMapping("history")
	public String showHistory(Model model) {
		//ログインユーザー情報の取得
		User user = loginUserService.getUser();
		
		//ログインユーザーと同じparentIdをもつユーザーの取得、rootの場合全ユーザーの取得
		List<User> fam = null;
		if (user.getAuthId() == 2) {
			fam = userService.getAll();/**ここ治す*/
		}else {
			fam = userService.getByParentId(user.getParentId());/**ここ治す*/
		}
		
		//現時点で完了(id=5)のhistory
		List<History> hist = historyService.getDoneTaskHist();/**ここ治す*/
		
		List<Task> tsklist = taskService.getAllRelateTask(user, fam, hist);/**ここ治す?*/
		
		Map<Integer, Integer> histMap = new HashMap<>();
		tsklist.forEach(el -> {
			histMap.put(el.getId(), historyService.getByTaskId(el.getId()).getId());
		});
		
		model.addAttribute("tasks", tsklist);
		model.addAttribute("map", histMap);
		return "history";
	}
	
	@GetMapping("/history/{id}")
	public String showHistDetail(@PathVariable("id")int id, Model model) {
		addSingleHistoryById(id, model);
		return "historyDetail";
	}
	
	@GetMapping("/history/delete/{id}")
	public String showDeleteConfirm(@PathVariable("id")int id, Model model) {
		addSingleHistoryById(id, model);
		return "historyDelete";
	}
	
	private void addSingleHistoryById(int id, Model model) {
		History hist = historyService.getById(id);
		
		Task task = taskService.getById(hist.getTaskId());
		
		String creater = userService.getById(task.getCreaterId()).getUsername();
		
		String done = userService.getById(hist.getDoneUserId()).getUsername();
		MyUt.print("doneUser :" + done);
		
		model.addAttribute("history", hist);
		model.addAttribute("task", task);
		model.addAttribute("creater", creater);		
		model.addAttribute("doneUser", done);
		
		int dblid = hist.getDblUserId();
		if (dblid != 0) model.addAttribute("dblUser", userService.getById(dblid));
	}
	
	@PostMapping("history/delete/{id}")
	public String toDeleteHistroy(@PathVariable("id")int id) {
		History hist = historyService.getById(id);
		
		int tk = taskService.deleteById(hist.getTaskId());
		int his = historyService.deleteById(id);
		MyUt.print("tk=" + tk + ", his=" + his);
		
		return "redirect:/history";
	}
}
