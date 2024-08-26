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

import jp.eightbit.exam.entity.Authority;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.AuthService;
import jp.eightbit.exam.service.HistoryService;
import jp.eightbit.exam.service.LoginUserService;
import jp.eightbit.exam.service.TaskService;
import jp.eightbit.exam.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	AuthService authService;
	@Autowired
	LoginUserService logUserServ;
	@Autowired
	HistoryService historyService;
	@Autowired
	TaskService taskService;
	
	@GetMapping("/user")
	public String showUserIndex(Model model) {	
		User loginuser = logUserServ.getLoginUser();
		
		List<User> list = null;
		if (loginuser.getAuthId() == 2) {//rootの場合
			list = userService.getAll();
		}else {
			list = userService.getByParentId(loginuser.getParentId());
		}
		
		Map<Integer, Authority> map = new HashMap<>();
		
		list.forEach(el -> {
			map.put(el.getId(),authService.getById(el.getAuthId()));
		});
		
		model.addAttribute("list", list);
		model.addAttribute("map", map);
		
		return "user";
	}
	
	@GetMapping("/user/{id}")
	public String showDetail(@PathVariable("id")int id, Model model) {
		getSingleUserAttribute(model, id);
		
		return "userDetail";
	}
	
	@GetMapping("/user/delete/{id}")
	public String showDelete(@PathVariable("id")int id, Model model) {
		getSingleUserAttribute(model, id);
		
		return "userDelete";
	}
	
	private void getSingleUserAttribute(Model model, int userid) {
		User user = userService.getById(userid);
		String auth = authService.getById(user.getAuthId()).getName();
		int doing = historyService.getActiveByDoneUserId(userid).size();
		int dbling = historyService.getActiveByDblUserId(userid).size();
		int done = historyService.getDoneByDoneUserId(userid).size();
		int dbled = historyService.getDoneByDblUserId(userid).size();

		model.addAttribute("user", user);
		model.addAttribute("auth", auth);
		model.addAttribute("doing", doing);
		model.addAttribute("dbling", dbling);
		model.addAttribute("done", done);
		model.addAttribute("dbled", dbled);
	}
	
	@PostMapping("/user/delete/{id}")
	public String toDelete(@PathVariable("id")int id) {
		userService.deleteById(id);
		taskService.updateToVoid(id);
		historyService.updateToVoid(id);
		
		return "redirect:/user";
	}
}
