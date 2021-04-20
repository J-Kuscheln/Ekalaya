package com.prototype.organisation.task;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prototype.organisation.project.Project;

@Transactional
@Repository
public interface TaskRepo extends CrudRepository<Task, Integer> {
	
	public List<Task> findAllByProject(Project project);
	public List<Task> findAllByName(String name);
}
