package jp.eightbit.exam.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.eightbit.exam.entity.Authority;
import jp.eightbit.exam.mapper.AuthMapper;

@Service
public class AuthService {
	@Autowired
	AuthMapper authMapper;
	
	@Transactional
	public Authority getById(int id) {
		return authMapper.getById(id);
	}
	
	//使用してない
	@Transactional
	public List<Authority> getUnderByIdNotWith(int id) {
		return authMapper.getUnderByIdNotWith(id);
	}

	@Transactional
	public List<Authority> getUnderByIdWith(int id) {
		if (id == 5) {
			List<Authority> list = new ArrayList<>();
			list.add(authMapper.getById(1));
			return list;
		}
		return authMapper.getUnderByIdWith(id);
	}
	
	@Transactional
	public List<Authority> getSmallerById(int id){
		return authMapper.getSmallerById(id);
	}
}
