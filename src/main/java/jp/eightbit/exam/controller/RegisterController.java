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
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.AuthService;
import jp.eightbit.exam.service.LoginUserService;
import jp.eightbit.exam.service.MyUt;
import jp.eightbit.exam.service.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	UserService userService;
	@Autowired
	AuthService authService;
	
	@GetMapping("/register")
	public String showRegister(Model model) {
		//現在ログインしてるユーザー
		User loginuser = loginUserService.getLoginUser();
		
		//modelに渡す用のユーザー
		User user = new User();
		user.setParentId(loginuser.getParentId());
		
		//ログインしているユーザー以下の権限のリスト
		List<Authority> list = authService.getUnderByIdNotWith(loginuser.getAuthId());
		
		model.addAttribute("user", user);
		model.addAttribute("list", list);
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@Validated @ModelAttribute("user")User user, BindingResult br, Model model) {
		MyUt.print(user.toString());
		
		//バリデーションチェック
		//入力した情報を持つユーザーがいるかチェック
		User exist = userService.getByUserName(user.getUsername());
		
		if (br.hasErrors() || exist != null) {
			if (exist != null) model.addAttribute("message", "既にそのユーザー名は使われています");
			
			int loginAuthId = loginUserService.getLoginUserAuthId();
			List<Authority> list = authService.getUnderByIdNotWith(loginAuthId);
			
			model.addAttribute("list", list);
			return "register";
		}
		
		//上記ユーザーがいなかったら
		loginUserService.save(user);
		return "redirect:/user";
	}
}
