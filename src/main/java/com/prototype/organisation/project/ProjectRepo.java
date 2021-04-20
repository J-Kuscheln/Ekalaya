package com.prototype.organisation.project;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;


public interface ProjectRepo extends CrudRepository<Project,Long> {
	public Project findByName(String name);
	public Collection<Project> findAllByStatus(String status);
	
}
