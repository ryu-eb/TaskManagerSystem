package jp.eightbit.exam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.Template;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.AuthService;
import jp.eightbit.exam.service.LoginUserService;
import jp.eightbit.exam.service.TemplateService;

@Controller
public class TemplateController {
	@Autowired
	TemplateService templateService;
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	AuthService authService;
	
	@GetMapping("/template")
	public String showList(Model model, @ModelAttribute("isForm")boolean isform) {
		User user = loginUserService.getLoginUser();
		
		List<Template> list = templateService.getByParentAndAuth(user.getParentId(),user.getAuthId());
		
		Map<Integer, String> map = new HashMap<>();
		
		list.forEach(el -> {
			map.put(el.getId(), authService.getById(el.getAuthRangeId()).getName());
		});
		
		if (isform) {
			model.addAttribute("isForm",true);
		}else {
			model.addAttribute("isForm",false);
		}
		model.addAttribute("list", list);
		model.addAttribute("map", map);
		return "template";
	}
	
	@GetMapping("/template/{id}")
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
		User user = loginUserService.getLoginUser();
		
		template.setCreaterId(user.getId());
		template.setParentId(user.getParentId());
		
		model.addAttribute("template", template);
		
		return "templateCreate";
	}
	
	@PostMapping("/template/create")
	public String addTemplate(@Validated @ModelAttribute("template")Template template, Model model) {
		
		return "redirect:/template?isForm=false";
	}
	
	@GetMapping("/template/delete/{id}")
	public String showDelete(@PathVariable("id")int id, Model model) {
		
		
		return "templateDelete";
	}
	
	@PostMapping("/template/delete/{id}")
	public String toDelete(@PathVariable("id")int id, Model model) {
		
		
		return "redirect:/template?isForm=false";
	}
}
