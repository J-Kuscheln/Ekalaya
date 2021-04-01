package com.prototype.organisation.task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prototype.organisation.member.Member;

@RestController
@RequestMapping("/Tasks")
public class TaskController {
	
	@Autowired
	private TaskService service;
	
	@RequestMapping("/{id}")
	public Task getTask(@PathVariable int id, @RequestHeader String memberId, HttpServletRequest request) {
		Task task= service.getTask(id);
		boolean isLoggedIn = request.getSession().getAttribute("USER_ID")!=null;
		//should add if logged in *later
		if(isMemberRegistered(task,memberId) && isLoggedIn) return task;

		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public HttpStatus addTask(@RequestBody Task task, HttpServletRequest request) {
		if(request.getSession().getAttribute("USER_ID")!=null) return service.addTask(task);
		return HttpStatus.UNAUTHORIZED;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public HttpStatus removeTask(@PathVariable int id, @RequestHeader String memberId) {
		Task task= service.getTask(id);
		if(!isMemberRegistered(task,memberId)) return HttpStatus.UNAUTHORIZED;
		return service.removeTask(id);
	}
	
	@RequestMapping(path = "/Project/{projectId}")
	public List<Task> getTaskInProject(@PathVariable long projectId){
		try {
			List<Task> allTasks = service.getAllTask();
			List<Task> tasks = new ArrayList<>();
			allTasks.forEach(t->{
				if(t.getProject().getId()==projectId) tasks.add(t);
			});
			
			return tasks;
		}catch (Exception e) {
			System.out.println("ERROR: something wrong in getTaskInProject()");
			return null;
		}
	}
	
	@RequestMapping("/Member/{memberId}")
	public List<Task> getTaskInMember(@PathVariable String memberId){
		try {
			List<Task> allTasks = service.getAllTask();
			List<Task> tasks = new ArrayList<>();
			allTasks.forEach(t->{
				t.getMembers().forEach(m->{
					if(m.getId()==UUID.fromString(memberId)) tasks.add(t);
				});
			});
			
			return tasks;
		}catch (Exception e) {
			System.out.println("ERROR: something wrong in getTaskInMember()");
			return null;
		}
	}
	
	//update
	@RequestMapping(method = RequestMethod.PUT)
	public HttpStatus updateTask(@RequestBody Task task) {
		return service.updateTask(task);
	}
	
	private boolean isMemberRegistered(Task task, String memberId) {
		for(Member memberInTask : task.getMembers()) {
			if(UUID.fromString(memberId)==memberInTask.getId()) {
				return true;
			} 
		}
		return false;
	}
}
