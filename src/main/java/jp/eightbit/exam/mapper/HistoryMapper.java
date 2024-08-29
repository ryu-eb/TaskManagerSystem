package jp.eightbit.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.eightbit.exam.entity.History;

@Mapper
public interface HistoryMapper {
	
	History getById(int id);
	
	List<History> getNotDoneTaskHist();	
	
	List<History> getDoneTaskHist(int voidid);
	
	History getByTaskId(int id);

	List<History> getActiveByDoneUserId(int id);
	
	List<History> getDoneByDoneUserId(int id);

	List<History> getActiveByDblUserId(int id);
	
	List<History> getDoneByDblUserId(int id);
	
	int add(History hist);

	int deleteById(int id);
	
	int deleteByTaskId(int id);
	
	int updateDoneToVoid(@Param("id")int id, @Param("voidid")int voidid);
	
	int updateDblToVoid(@Param("id")int id, @Param("voidid")int voidid);
	
	int save(History hist);
}
