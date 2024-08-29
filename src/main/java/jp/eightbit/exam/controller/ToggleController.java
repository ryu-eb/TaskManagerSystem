package jp.eightbit.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@PostMapping("/task/toggle/inactive/{id}")
	public String tglInActive(@PathVariable("id")int id, RedirectAttributes ra){
		return toggleTask(id, ra);
	}
	@PostMapping("/task/toggle/active/{id}")
	public String tglActive(@PathVariable("id")int id, RedirectAttributes ra){
		return toggleTask(id, ra);
	}
	@PostMapping("/task/toggle/standby/{id}")
	public String tglStandBy(@PathVariable("id")int id, RedirectAttributes ra){
		return toggleTask(id, ra);
	}
	@PostMapping("/task/toggle/check/{id}")
	public String tglCheck(@PathVariable("id")int id, RedirectAttributes ra){
		return toggleTask(id, ra);
	}
	
	private String toggleTask(int id, RedirectAttributes ra) {
		User user = loginUserService.getUser();
		
		Task task = taskService.getById(id);
		List<Status> status = statusService.getAll();
		String err = historyService.toggleTask(user,task,status,task.isDblCheck());
		
		if (err != null) {
			ra.addFlashAttribute("errTask", task);
			ra.addFlashAttribute("err", err);
		}
		
		return "redirect:/task";
	}
}
