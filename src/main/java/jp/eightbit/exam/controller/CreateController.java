package jp.eightbit.exam.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.eightbit.exam.entity.Authority;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.AuthService;
import jp.eightbit.exam.service.HistoryService;
import jp.eightbit.exam.service.LoginUserService;
import jp.eightbit.exam.service.MyUt;
import jp.eightbit.exam.service.TaskService;
import jp.eightbit.exam.service.TemplateService;

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
	@Autowired
	TemplateService templateService;
	
	@GetMapping("/create")
	public String showCreate(@ModelAttribute("tmptask")Task task, Model model) {
		//現在ログインしてるユーザー
		User loginuser = loginUserService.getLoginUser();

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
		
		return "create";
	}
	
	@PostMapping("/create")
	public String toCreate(@Validated @ModelAttribute("task")Task task, BindingResult br, Model model, @RequestParam("template")boolean tmplate){
		
		if (br.hasErrors()) {
			int loginAuthId = loginUserService.getLoginUserAuthId();

			List<Authority> list = authService.getUnderByIdWith(loginAuthId);

			model.addAttribute("list", list);
			return "create";
		}
		
		//テンプレートの保存
		if (tmplate) {
			
		}
		
		int id = taskService.add(task);
		historyService.add(taskService.getById(id));
		return "redirect:/task";
	}
}
