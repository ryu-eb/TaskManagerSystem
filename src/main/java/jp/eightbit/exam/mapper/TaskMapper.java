package jp.eightbit.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.eightbit.exam.entity.Task;

@Mapper
public interface TaskMapper {
	
	List<Task> getAllTask();
	
	List<Task> getRelateTask(@Param("family")List<Integer> familiy, @Param("inactives")List<Integer> inactives);
	
	Task getById(int id);
	
	int add(Task task);
	
	int deleteById(int id);
	
	int updateToVoid(@Param("id")int id, @Param("voidid")int voidid);
	
	List<Task> getAllByCreaterId(int id);
}
