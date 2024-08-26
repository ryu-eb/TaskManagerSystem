package jp.eightbit.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.eightbit.exam.entity.Status;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.HistoryService;
import jp.eightbit.exam.service.LoginUserService;
import jp.eightbit.exam.service.StatusService;
import jp.eightbit.exam.service.TaskService;

@Controller
public class ToggleController {
	@Autowired
	HistoryService historyService;
	@Autowired
	TaskService taskService;
	@Autowired
	StatusService statusService;
	@Autowired
	LoginUserService loginUserService;
	
	@PostMapping("/task/toggle/{id}")
	public String toggleTask(@PathVariable("id")int id){
		User user = loginUserService.getLoginUser();
		
		Task task = taskService.getById(id);
		List<Status> status = statusService.getAll();
		historyService.toggleTask(user,task,status,task.isDblCheck());
		
		return "redirect:/task";
	}
}
