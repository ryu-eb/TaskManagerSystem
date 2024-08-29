package jp.eightbit.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.Template;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.mapper.TemplateMapper;

@Service
public class TemplateService {
	@Autowired
	TemplateMapper templateMapper;
	
	@Transactional
	public List<Template> getRelateByUser(User user, int voidid){
		return templateMapper.getRelateByUser(user, voidid);
	}
	
	@Transactional
	public int add(Template template) {
		return templateMapper.add(template);
	}
	
	@Transactional
	public int addByTask(Task task, int parent) {
		Template template = new Template();
		
		template.setTitle(task.getTitle());
		template.setDescription(task.getDescription());
		template.setCreaterId(task.getCreaterId());
		template.setParentId(parent);
		template.setAuthRangeId(task.getAuthRangeId());
		
		return templateMapper.add(template);
	}
	
	@Transactional
	public Template getById(int id) {
		return templateMapper.getById(id);
	}
	
	@Transactional
	public int updateToVoid(int id, User voider) {
		return templateMapper.updateToVoid(id, voider);
	}
	
	@Transactional
	public int deleteById(int id){
		return templateMapper.deleteById(id);
	}
}
