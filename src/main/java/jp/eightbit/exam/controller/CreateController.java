package jp.eightbit.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.eightbit.exam.entity.Authority;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.AuthService;
import jp.eightbit.exam.service.HistoryService;
import jp.eightbit.exam.service.LoginUserService;
import jp.eightbit.exam.service.TaskService;

@Controller
public class CreateController {
	@Autowired
	TaskService taskService;
	@Autowired
	AuthService authService;
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	HistoryService historyService;
	
	@GetMapping("/create")
	public String showCreate(Model model) {
		//現在ログインしてるユーザー
		User loginuser = loginUserService.getLoginUser();

		//modelに渡す用のタスク
		Task task = new Task();
		task.setCreaterId(loginuser.getId());
		

		//ログインしているユーザー以下の権限のリスト
		List<Authority> list = authService.getUnderByIdWith(loginuser.getAuthId());
		
		model.addAttribute("task", task);
		model.addAttribute("list", list);
		return "create";
	}
	
	@PostMapping("/create")
	public String toCreate(@Validated @ModelAttribute("task")Task task, BindingResult br, Model model){
		
		if (br.hasErrors()) {
			int loginAuthId = loginUserService.getLoginUserAuthId();

			List<Authority> list = authService.getUnderByIdWith(loginAuthId);

			model.addAttribute("list", list);
			return "create";
		}
		
		int id = taskService.add(task);
		historyService.add(taskService.getById(id));
		return "redirect:/task";
	}
}
