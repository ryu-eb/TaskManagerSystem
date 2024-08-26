package jp.eightbit.exam.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class loginController {
	
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
