package com.prototype.organisation.task;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.prototype.organisation.member.Member;
import com.prototype.organisation.project.Project;

public interface TaskRepo extends CrudRepository<Task, Integer> {
	
	public List<Task> findAllByProject(Project project);
	public List<Task> findAllByMember(Member member);
	public List<Task> findAllByName(String name);
}
