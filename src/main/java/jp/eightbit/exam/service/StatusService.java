package jp.eightbit.exam.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.eightbit.exam.entity.History;
import jp.eightbit.exam.entity.Status;
import jp.eightbit.exam.mapper.StatusMapper;

@Service
public class StatusService {
	@Autowired
	StatusMapper statusMapper;
	
	@Transactional
	public Map<Integer, Status> getMapByTaskList(List<History> hist){		
		Map<Integer, Status> result = new HashMap<>();
		
		for (History hst : hist) {
			result.put(hst.getTaskId(), statusMapper.getById(hst.getStatusId()));
		}
		
		return result;
	}
	
	@Transactional
	public Status getStatusByTaskIdHistList(int taskid, List<History> hist) {
		
		for (History hst : hist) {
			if (hst.getTaskId() == taskid) return statusMapper.getById(hst.getStatusId());
		}
		
		return null;
	}
	
	@Transactional
	public List<Status> getAll(){
		return statusMapper.getAll();
	}
}
