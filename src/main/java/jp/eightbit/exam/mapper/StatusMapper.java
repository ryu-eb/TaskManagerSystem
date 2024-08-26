package jp.eightbit.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.eightbit.exam.entity.Status;

@Mapper
public interface StatusMapper {
	
	Status getById(int id);
	
	List<Status> getAll();

}
