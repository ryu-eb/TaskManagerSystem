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
	
	@PostMapping("/task/toggle/{id}")
	public String toggleTask(@PathVariable("id")int id, RedirectAttributes ra){
		User user = loginUserService.getUser();
		
		Task task = taskService.getById(id);
		List<Status> status = statusService.getAll();
		int result = historyService.toggleTask(user,task,status,task.isDblCheck());
		
		String err = null;
		switch (result) {
		case 0:
			err = "SQLのエラー";
			break;
		case -1:
			err = "を精査中へ変更できませんでした。作業者は精査出来ません。";
			break;
		case -2:
			err = "を精査待ちへ変更できませんでした。作業者のみ作業中から精査待ちに変更できます。";
			break;
		case -3:
			err = "を完了へ変更できませんでした。作業者のみ作業中から完了に変更できます。";
			break;
		case -4:
			err = "を完了へ変更できませんでした。精査者のみ精査中から完了に変更できます。";
			break;
		}
		if (err != null) {
			ra.addFlashAttribute("errTask", task);
			ra.addFlashAttribute("err", err);
		}
		
		return "redirect:/task";
	}
}
