package com.prototype.organisation.project;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface ProjectRepo extends CrudRepository<Project,Long> {
	public Project findByName(String name);
	public Collection<Project> findAllByStatus(String status);
}
