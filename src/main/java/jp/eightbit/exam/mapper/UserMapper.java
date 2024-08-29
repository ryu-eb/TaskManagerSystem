package jp.eightbit.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.eightbit.exam.entity.User;

@Mapper
public interface UserMapper {
	
	User getByName(String name);
	
	User getById(int id);
	
	List<User> getByParent(User voider);
	
	int add(User user);
	
	int deleteById(int id);
	
	int updateToMyParent(int id, int parent);
	
	List<User> getAll(int parent);
	
	List<User> getRootAdmin();//
	
	int updateParent(User user);
	
	User getRootByParentId(int parent);
}
