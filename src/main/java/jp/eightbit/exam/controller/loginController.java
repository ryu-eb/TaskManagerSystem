package jp.eightbit.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.UserService;

@Controller
public class loginController {
	
	@Autowired
	UserService userService;
	
	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	
	@GetMapping("/login")
	public String justIndex() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String toLogout(Authentication auth, HttpServletRequest request, HttpServletResponse response) {
		logoutHandler.logout(request, response, auth);
		return "redirect:login";
	}
	
	@GetMapping("/root/register")
	public String showRootRegist(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		
		return "rootRegister";
	}
	@PostMapping("/root/register")
	public String registRoot(@Validated @ModelAttribute("user")User user, BindingResult br, Model model) {
		
		User exist = userService.getByUserName(user.getUsername());
		
		if (br.hasErrors() || exist != null) {
			if (exist != null) model.addAttribute("message", "既にそのユーザー名は使われています");
			return "rootRegister";
		}
		
		userService.registRoot(user);
		return "redirect:login";
	}
	
	//メモ　loginからボタン押すとpostで/loginにアクセス。postの場合多分内部で何かして認証して、configで定義した認証成功し場合のurl(/)に飛ぶから、そこからここにくる。
	@GetMapping("/")
	public String redirectToIndex() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			return "redirect:/task";
		}
		return "redirect:/login";
	}
}
