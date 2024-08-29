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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.eightbit.exam.entity.Authority;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.Template;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.AuthService;
import jp.eightbit.exam.service.LoginUserService;
import jp.eightbit.exam.service.TemplateService;
import jp.eightbit.exam.service.UserService;

@Controller
public class TemplateController {
	@Autowired
	TemplateService templateService;
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	AuthService authService;
	@Autowired
	UserService userService;
	
	@GetMapping("/template")
	public String showList(Model model, @ModelAttribute("isForm")boolean isform) {
		User user = loginUserService.getUser();
		
		List<Template> list = templateService.getRelateByUser(user, loginUserService.getVoidId());
		
		Map<Integer, String> auth = new HashMap<>();
		Map<Integer, String> users = new HashMap<>();
		
		list.forEach(el -> {
			int id = el.getId();
			auth.put(id, authService.getById(el.getAuthRangeId()).getName());
			users.put(id, userService.getById(el.getCreaterId()).getUsername());
		});
		
		if (isform) {
			model.addAttribute("isForm",true);
		}else {
			model.addAttribute("isForm",false);
		}
		model.addAttribute("list", list);
		model.addAttribute("auth", auth);
		model.addAttribute("user", users);
		return "template";
	}
	
	@GetMapping("/template/use/{id}")
	public String selectTemplate(@PathVariable("id")int id, RedirectAttributes ra) {
		Template temp = templateService.getById(id);
		
		Task task =  new Task();
		task.setTitle(temp.getTitle());
		task.setDescription(temp.getDescription());
		
		ra.addFlashAttribute("tmptask", task);
		return "redirect:/task/create";
	}
	
	@GetMapping("/template/create")
	public String showCreate(Model model) {
		Template template = new Template();
		User user = loginUserService.getUser();
		
		template.setCreaterId(user.getId());
		template.setParentId(user.getParentId());
		
		List<Authority> list = authService.getUnderByIdWith(user.getAuthId());
		
		model.addAttribute("template", template);
		model.addAttribute("list", list);
		return "templateCreate";
	}
	
	@PostMapping("/template/create")
	public String toCreate(@Validated @ModelAttribute("template")Template template, BindingResult br, Model model) {
		if (br.hasErrors()) {
			User user = loginUserService.getUser();
			List<Authority> list = authService.getUnderByIdWith(user.getAuthId());
			
			model.addAttribute("template", template);
			model.addAttribute("list", list);
			return "templateCreate";
		}
		
		templateService.add(template);
		return "redirect:/template?isForm=false";
	}
	
	@GetMapping("/template/delete/{id}")
	public String showDelete(@PathVariable("id")int id, Model model) {
		Template template = templateService.getById(id);
		
		String auth = authService.getById(template.getAuthRangeId()).getName();
		String user = userService.getById(template.getCreaterId()).getUsername();
		
		model.addAttribute("template", template);
		model.addAttribute("auth", auth);
		model.addAttribute("user", user);
		
		return "templateDelete";
	}
	
	@PostMapping("/template/delete/{id}")
	public String toDelete(@PathVariable("id")int id) {
		templateService.deleteById(id);
		
		return "redirect:/template?isForm=false";
	}
}
