package jp.eightbit.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.eightbit.exam.entity.Authority;

@Mapper
public interface AuthMapper {
	
	public Authority getById(int id);
	
	public List<Authority> getUnderByIdNotWith(int id);
	
	public List<Authority> getUnderByIdWith(int id);
	
}
