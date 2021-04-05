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

@CrossOrigin(origins = {"http://192.168.178.31:4200","http://localhost:4200"})
@RestController
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	private ProjectService service;
	
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
		return service.deleteProject(id);
	}
}
