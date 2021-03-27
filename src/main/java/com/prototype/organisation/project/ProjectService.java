package com.prototype.organisation.project;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepo projectRepo;
	
	public Collection<Project> getAllProjects(){
		Collection<Project> projects = new ArrayList<>();
		projectRepo.findAll().forEach(projects::add);
		return projects;
	}

	public Project getProject(long id) {
		// TODO Auto-generated method stub
		return projectRepo.findById(id).get();
	}

	public HttpStatus createProject(Project project) {
		if(projectRepo.findByName(project.getName())!=null) return HttpStatus.IM_USED;
		if(projectRepo.save(project)!=null) return HttpStatus.CREATED;
		return HttpStatus.EXPECTATION_FAILED;
	}

	public HttpStatus updateProject(Project project) {
		if(projectRepo.existsById(project.getId())) {
			if(projectRepo.save(project)!=null) return HttpStatus.CREATED;
			return HttpStatus.EXPECTATION_FAILED;
		}
		return HttpStatus.NOT_FOUND;
	}

	public HttpStatus deleteProject(long id) {
		if(projectRepo.existsById(id)) {
			projectRepo.deleteById(id);
			return HttpStatus.OK;
		}
		return HttpStatus.NOT_FOUND;
	}

	public Project getProjectByName(String name) {
		return projectRepo.findByName(name);
	}
	
	
}