package jp.eightbit.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.eightbit.exam.entity.Template;
import jp.eightbit.exam.entity.User;

@Mapper
public interface TemplateMapper {
	
	List<Template> getRelateByUser(@Param("user")User user, @Param("voidid")int voidid);
	
	int add(Template template);
	
	Template getById(int id);
	
	int updateToVoid(@Param("id")int id, @Param("voider")User voider);
	
	int deleteById(int id);
}
