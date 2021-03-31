package com.prototype.organisation.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepo repo;
	
	public HttpStatus addTask(Task task) {
		List<Task> tasks = repo.findAllByName(task.getName());
		for(Task t : tasks) {
			if(t.getProject().getId() == task.getProject().getId()) return HttpStatus.IM_USED;
		}
		try{
			repo.save(task);
			return HttpStatus.OK;
		}catch (Exception e) {
			return HttpStatus.EXPECTATION_FAILED;
		}
	}
	
	public Task getTask(int id) {
		try {
			if(!repo.existsById(id)) return null;
			return repo.findById(id).get();
		}catch (Exception e) {
			return null;
		}
	}
	

	public HttpStatus removeTask(int id) {
		try {
			if(!repo.existsById(id)) return HttpStatus.NOT_FOUND;
			repo.deleteById(id);
			return HttpStatus.OK;
		}catch (Exception e) {
			return HttpStatus.EXPECTATION_FAILED;
		}
	}

	public HttpStatus updateTask(Task task) {
		try {
			if(!repo.existsById(task.getId())) return HttpStatus.NOT_FOUND;
			repo.save(task);
			return HttpStatus.OK;
		}catch (Exception e) {
			return HttpStatus.EXPECTATION_FAILED;
		}
	}
}
