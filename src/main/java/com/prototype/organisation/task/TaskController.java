package com.prototype.organisation.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prototype.organisation.member.Member;
import com.prototype.organisation.member.MemberService;
import com.prototype.organisation.project.Project;
import com.prototype.organisation.project.ProjectService;


@CrossOrigin
@RestController
@RequestMapping("/tasks")
public class TaskController {
	@Autowired
	private TaskService service;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Task> getAllTask(){ 
		return service.getAllTask();
	}
	
	//

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public Task getTask(@PathVariable int id, @RequestHeader("Id") String memberId, HttpServletRequest request) {
		Task task= service.getTask(id);
		boolean isLoggedIn = request.getSession().getAttribute("USER_ID")!=null;
		System.out.println("is logged in: " + isLoggedIn);
		//should add if logged in *later
		if((isMemberRegistered(task,memberId)||isLeader(task, memberId)) && isLoggedIn) {
			System.out.println("returning task");
			return task;
		}

		return null;
	}
	
	/*
	 * RequestBody Task task, @RequestHeader("PID") String projectId, 
	 * @RequestHeader("UID") String userId, HttpServletRequest request
	 */
			
	
	@RequestMapping(method = RequestMethod.POST)
	public HttpStatus createTask(@RequestBody Task task, @RequestHeader("PID") String projectId, 
			 @RequestHeader("UID") String userId, HttpServletRequest request){ 
		
		System.out.println("task name: " + task.getName());
		System.out.println("projectId: " + projectId);
		System.out.println("userId: " + userId);
		System.out.println("duedate: " + task.getDueDate());
		
		//Authorisation
		
		if(request.getSession().getAttribute("USER_ID")!=null) {
			try {
			Project project = projectService.getProject(Long.valueOf(projectId));
			
			//check if the task name already exist in this project
			Collection<Task> tasks = project.getTasks();
			for(Task t : tasks) {
				if(t.getName().equals(task.getName())) return HttpStatus.IM_USED;
			}
			
			//relate task with project
			task.setProject(project);
			
			//relate task with user
			if(userId!="") {
				String[] userIds = userId.split("&");
				for(String Id : userIds) {
					Member member = memberService.getMember(UUID.fromString(Id));
					task.addMembers(member);
				}
			}
			
			
			HttpStatus status = service.addTask(task);
			return status;
		}catch(Exception e) {
			e.printStackTrace();
			return HttpStatus.EXPECTATION_FAILED;
		}
		}
		return HttpStatus.UNAUTHORIZED;
	}
	

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public HttpStatus removeTask(@PathVariable int id, @RequestHeader("id") String memberId
			, HttpServletRequest request) {
		Task task= service.getTask(id);
		boolean isLoggedIn = request.getSession().getAttribute("USER_ID")!=null;
		if(!isLeader(task, memberId) && isLoggedIn) return HttpStatus.UNAUTHORIZED;
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
	@RequestMapping(method = RequestMethod.PUT, path="/{id}")
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
	
	private boolean isLeader(Task task, String memberId) {
		Project project = task.getProject();
		for(Member leader: project.getProjectLeaders()) {
			if(leader.getId().equals(UUID.fromString(memberId))) return true;
		}
		return false;
	}
}
