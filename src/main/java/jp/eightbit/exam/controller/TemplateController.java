package jp.eightbit.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.Template;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.LoginUserService;
import jp.eightbit.exam.service.TemplateService;

@Controller
public class TemplateController {
	@Autowired
	TemplateService templateService;
	@Autowired
	LoginUserService loginUserService;
	
	@GetMapping("/template")
	public String showList(Model model) {
		User user = loginUserService.getLoginUser();
		
		List<Template> list = templateService.getByParentAndAuth(user.getParentId(),user.getAuthId());
		
		model.addAttribute("list", list);
		return "template";
	}
	
	@GetMapping("/template/{id}")
	public String selectTemplate(@PathVariable("id")int id, RedirectAttributes ra) {
		Template temp = templateService.getById(id);
		
		Task task =  new Task();
		task.setTitle(temp.getTitle());
		task.setDescription(temp.getDescription());
		
		ra.addFlashAttribute("tmptask", task);
		return "redirect:/create";
	}
}
