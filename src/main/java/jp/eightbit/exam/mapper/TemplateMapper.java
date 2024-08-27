package jp.eightbit.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.eightbit.exam.entity.Template;
import jp.eightbit.exam.entity.User;

@Mapper
public interface TemplateMapper {
	
	List<Template> getRelateByUser(User user);
	
	int add(Template template);
	
	Template getById(int id);
	
	int updateToVoid(int id);
	
	int deleteById(int id);
}
