package jp.eightbit.exam.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.eightbit.exam.entity.History;
import jp.eightbit.exam.entity.Status;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.HistoryService;
import jp.eightbit.exam.service.LoginUserService;
import jp.eightbit.exam.service.MyUt;
import jp.eightbit.exam.service.StatusService;
import jp.eightbit.exam.service.TaskService;
import jp.eightbit.exam.service.UserService;

@Controller
public class TaskController {
	@Autowired
	TaskService taskService;
	@Autowired
	StatusService statusService;
	@Autowired
	HistoryService historyService;
	@Autowired
	UserService userService;
	@Autowired
	LoginUserService logUserServ;
	
	//@AuthenticationPrincipal User user
	//System.out.println("now its " + loguser.getUsername() + " logged in.");
	
	@GetMapping("/task")
	public String showIndex(Model model) {
		//ログインユーザー情報の取得
		User user = logUserServ.getLoginUser();
		
		//ログインユーザーと同じparentIdをもつユーザーの取得、rootの場合全ユーザーの取得
		List<User> fam = null;
		if (user.getAuthId() == 2) {//rootの場合
			fam = userService.getAll();
		}else {
			fam = userService.getByParentId(user.getParentId());
		}
		
		//現時点で完了以外(id<>5)のhistory
		List<History> hist = historyService.getNotDoneTaskHist();
		
		List<Task> tsklist = taskService.getAllRelateTask(user, fam, hist);
		model.addAttribute("tasks", tsklist);
		
		//ステータスリストの取得
		Map<Integer, Status> statlist = statusService.getMapByTaskList(hist);
		model.addAttribute("map", statlist);
		
		return "task";
	}
	
	@GetMapping("/task/{taskid}")
	public String showDetail(Model model, @PathVariable("taskid")Integer taskid) {
		addSingleTaskAttribute(model,taskid);
		
		return "taskDetail";
	}
	
	@GetMapping("/task/delete/{taskid}")
	public String showDelete(Model model, @PathVariable("taskid")Integer taskid) {
		addSingleTaskAttribute(model,taskid);
		
		return "taskDelete";
	}
	
	private void addSingleTaskAttribute(Model model, int taskid) {
		Task task = taskService.getById(taskid);
		String creater = userService.getById(task.getCreaterId()).getUsername();
		
		List<History> hist = historyService.getNotDoneTaskHist();
		Status stat = statusService.getStatusByTaskIdHistList(taskid, hist);

		model.addAttribute("task", task);
		model.addAttribute("creater", creater);
		model.addAttribute("stat", stat);
	}
	
	@PostMapping("/task/delete/{taskid}")
	public String doDelete(@PathVariable("taskid")Integer taskid) {
		int tk = taskService.deleteById(taskid);
		int his = historyService.deleteByTaskId(taskid);
		MyUt.print("tk=" + tk + ", his=" + his);
		
		return "redirect:/task";
	}
}
