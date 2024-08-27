package jp.eightbit.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.eightbit.exam.entity.Template;

@Mapper
public interface TemplateMapper {
	
	List<Template> getByParentAndAuth(@Param("parent")int parent, @Param("auth")int auth);
	
	int add(Template template);
	
	Template getById(int id);
	
	int updateToVoid(int id);
}
