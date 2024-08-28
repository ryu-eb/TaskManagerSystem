package jp.eightbit.exam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.eightbit.exam.entity.Authority;
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
	@Autowired
	TemplateService templateService;
	@Autowired
	LoginUserService loginUserService;
	
	@GetMapping("/user")
	public String showUserIndex(Model model) {	
		User loginuser = logUserServ.getUser();
		
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
	
	@GetMapping("/user/register")
	public String showRegister(Model model) {
		//現在ログインしてるユーザー
		User loginuser = loginUserService.getUser();
		
		//modelに渡す用のユーザー
		User user = new User();
		user.setParentId(loginuser.getParentId());
		model.addAttribute("user", user);
		
		setRegistInfo(loginuser, model);
		
		return "userRegister";
	}
	@PostMapping("/user/register")
	public String registerUser(@Validated @ModelAttribute("user")User user, BindingResult br, Model model) {
		User loginuser = loginUserService.getUser();
		
		//バリデーションチェック
		//入力した情報を持つユーザーがいるかチェック
		User exist = userService.getByUserName(user.getUsername());
		
		if (br.hasErrors() || exist != null) {
			if (exist != null) model.addAttribute("message", "既にそのユーザー名は使われています");
			setRegistInfo(loginuser, model);
			return "userRegister";
		}
		
		//上記ユーザーがいなかったら
		int savedid = loginUserService.save(user);
		return "redirect:/user";
	}
	private void setRegistInfo(User user, Model model) {
		//ログインしているユーザーより下の権限のリスト
		List<Authority> list = authService.getSmallerById(user.getAuthId());
		model.addAttribute("list", list);
		
		//root用の親ユーザー選択リスト
		if (user.getAuthId() == 2) {
			List<User> parents = userService.getRootAdmin();
			model.addAttribute("parents", parents);
			
			Map<Integer, String> map = new HashMap<>();
			parents.forEach(el -> map.put(el.getId(), authService.getById(el.getAuthId()).getName()));
			model.addAttribute("map", map);
		}
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
		templateService.updateToVoid(id);
		
		return "redirect:/user";
	}
}
