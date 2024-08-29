package jp.eightbit.exam.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.eightbit.exam.entity.Authority;
import jp.eightbit.exam.entity.History;
import jp.eightbit.exam.entity.Status;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.AuthService;
import jp.eightbit.exam.service.HistoryService;
import jp.eightbit.exam.service.LoginUserService;
import jp.eightbit.exam.service.MyUt;
import jp.eightbit.exam.service.StatusService;
import jp.eightbit.exam.service.TaskService;
import jp.eightbit.exam.service.TemplateService;
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
	LoginUserService loginUserService;
	@Autowired
	TemplateService templateService;
	@Autowired
	AuthService authService;
	
	
	//@AuthenticationPrincipal User user
	//System.out.println("now its " + loguser.getUsername() + " logged in.");
	
	@GetMapping("/task")
	public String showIndex(Model model, @ModelAttribute("err")String err, @ModelAttribute("errTask")Task errtask) {
		
		//debug
		//loginUserService.getLoginUser().getAuthoritiesId().forEach(el -> MyUt.print("id :" + el));
		
		//ログインユーザー情報の取得
		User user = loginUserService.getUser();
		
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
		
		//精査失敗時
		if (errtask != null) {
			model.addAttribute("err", err);
			model.addAttribute("errTask", errtask);
		}
		
		return "task";
	}
	
	@GetMapping("/task/{taskid}")
	public String showDetail(Model model, @PathVariable("taskid")Integer taskid) {
		addSingleTaskAttribute(model,taskid);
		
		return "taskDetail";
	}
	
	@GetMapping("/task/create")
	public String showCreate(@ModelAttribute("tmptask")Task task, Model model) {
		//現在ログインしてるユーザー
		User loginuser = loginUserService.getUser();

		//modelに渡す用のタスク
		task.setCreaterId(loginuser.getId());
		model.addAttribute("task", task);
		
		if (Objects.nonNull(task.getTitle())) {
			model.addAttribute("isTemplate", true);
		}else {
			model.addAttribute("isTemplate", false);
		}
		

		//ログインしているユーザー以下の権限のリスト
		List<Authority> list = authService.getUnderByIdWith(loginuser.getAuthId());
		model.addAttribute("list", list);
		
		return "taskCreate";
	}
	
	@PostMapping("/task/create")
	public String toCreate(@Validated @ModelAttribute("task")Task task, BindingResult br, Model model, @RequestParam("template")boolean tmplate, @ModelAttribute("isTemplate")String istemplate){
		User user = loginUserService.getUser();
		
		if (br.hasErrors()) {
			List<Authority> list = authService.getUnderByIdWith(user.getAuthId());
			model.addAttribute("isTemplate", istemplate);
			model.addAttribute("list", list);
			return "taskCreate";
		}
		
		//テンプレートの保存
		if (tmplate) templateService.addByTask(task,user.getParentId());
		
		int id = taskService.add(task);
		historyService.add(taskService.getById(id));
		return "redirect:/task";
	}
	
	@GetMapping("/task/delete/{taskid}")
	public String showDelete(Model model, @PathVariable("taskid")Integer taskid) {
		addSingleTaskAttribute(model,taskid);
		
		return "taskDelete";
	}
	
	private void addSingleTaskAttribute(Model model, int taskid) {
		Task task = taskService.getById(taskid);
		model.addAttribute("task", task);
		
		String creater = userService.getById(task.getCreaterId()).getUsername();
		model.addAttribute("creater", creater);
		
		List<History> hist = historyService.getNotDoneTaskHist();
		Status status = statusService.getStatusByTaskIdHistList(taskid, hist);
		model.addAttribute("status", status);
		
		String doing = null;
		String dbling = null;
		switch (historyService.getByTaskId(taskid).getStatusId()) {
			case 4:
				dbling = userService.getById(historyService.getByTaskId(taskid).getDblUserId()).getUsername();
				model.addAttribute("dbling", dbling);
			case 3:
			case 2:
				doing = userService.getById(historyService.getByTaskId(taskid).getDoneUserId()).getUsername();
				model.addAttribute("doing", doing);
				break;
			default:
				break;
		}
	}
	
	@PostMapping("/task/delete/{taskid}")
	public String doDelete(@PathVariable("taskid")Integer taskid) {
		int tk = taskService.deleteById(taskid);
		int his = historyService.deleteByTaskId(taskid);
		MyUt.print("tk=" + tk + ", his=" + his);
		
		return "redirect:/task";
	}
}
