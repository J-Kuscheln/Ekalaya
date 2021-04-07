package com.prototype.organisation.project;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prototype.organisation.member.Member;
import com.prototype.organisation.member.MemberService;
import com.prototype.organisation.task.Task;
import com.prototype.organisation.task.TaskService;

@CrossOrigin(origins = {"http://192.168.178.31:4200","http://localhost:4200"})
@RestController
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	private ProjectService service;
	@Autowired
	private TaskService taskService;
	@Autowired
	private MemberService memberService;
	
	@GetMapping
	public Collection<Project> getAllProjects(){
		return service.getAllProjects();
	}
	
	@GetMapping("/{id}")
	public Project getProject(@PathVariable long id) {
		return service.getProject(id);
	}
	
	@GetMapping("/name/{name}")
	public Project getProjectByName(@PathVariable String name) {
		return service.getProjectByName(name);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<HttpStatus> createProject(@RequestBody Project project) {
		return new ResponseEntity<HttpStatus>(service.createProject(project));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public HttpStatus updateProject(@PathVariable long id, @RequestBody Project project, HttpServletResponse response) {
		System.out.println("New Project Name: " + project.getName());
		System.out.println("New Project Desc: " + project.getDescription());
		
		HttpStatus status;
		
		Project sameProject = service.getProjectByName(project.getName());
		
		if(sameProject!=null && sameProject.getId()!=id) {
			status = HttpStatus.IM_USED;
			response.setStatus(status.value());
			return status;
		}
		
		Project oldProject = service.getProject(id);
		
		oldProject.setDescription(project.getDescription());
		oldProject.setName(project.getName());
		
		return service.updateProject(oldProject);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public HttpStatus deleteProject(@PathVariable long id) {
		Project project = service.getProject(id);
		
		//remove all tasks related to the project
		for(Task task : project.getTasks()) {
			taskService.removeTask(task.getId());
		}
		
		//remove member & Leader relation with the project
		for(Member member : project.getProjectMembers()) {
			member.removeMemberProjects(project);
			memberService.updateMember(member);
		}
		
		for(Member member : project.getProjectLeaders()) {
			member.removeLeadedProjects(project);
			memberService.updateMember(member);
		}
		
		return service.deleteProject(id);
	}
}
