package jp.eightbit.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.eightbit.exam.entity.Template;
import jp.eightbit.exam.mapper.TemplateMapper;

@Service
public class TemplateService {
	@Autowired
	TemplateMapper templateMapper;
	
	@Transactional
	public List<Template> getByParentAndAuth(int parent, int auth){
		return templateMapper.getByParentAndAuth(parent, auth);
	}
	
	@Transactional
	public int add(Template template) {
		return templateMapper.add(template);
	}
	
	@Transactional
	public Template getById(int id) {
		return templateMapper.getById(id);
	}
}
