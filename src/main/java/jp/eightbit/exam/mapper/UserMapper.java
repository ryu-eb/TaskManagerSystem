package jp.eightbit.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.eightbit.exam.entity.User;

@Mapper
public interface UserMapper {
	
	User getByName(String name);
	
	User getById(int id);
	
	List<User> getByParentId(int id);
	
	int add(User user);
	
	int deleteById(int id);
	
	int updateToMyParent(int id, int parent);
	
	List<User> getAll();
	
	List<User> getRootAdmin();
}
