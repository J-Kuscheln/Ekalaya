package com.prototype.organisation.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prototype.organisation.CustomMembersSerializer;
import com.prototype.organisation.CustomTasksSerializer;
import com.prototype.organisation.member.Member;
import com.prototype.organisation.milestone.Milestone;
import com.prototype.organisation.task.Task;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private long id;
	private String name;
	private String status;
	@Lob
	@Column(length=3000)
	private String description;
	@CreatedDate
	@Column(updatable = false, nullable = false)
	private Date createdDate = new Date();
	@LastModifiedDate
	@Column(nullable = false)
	private Date modifiedDate = new Date();
	
	@JsonSerialize(using = CustomMembersSerializer.class)
	@ManyToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	private Collection<Member> projectLeaders = new ArrayList<>();
	
	@JsonSerialize(using = CustomMembersSerializer.class)
	@ManyToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	private Collection<Member> projectMembers = new ArrayList<>();
	@Embedded
	private Collection<Milestone> milestones = new ArrayList<>();
	
	@JsonSerialize(using = CustomTasksSerializer.class)
	@OneToMany(mappedBy = "project")
	@LazyCollection(LazyCollectionOption.TRUE)
	private Collection<Task> tasks = new ArrayList<>();
	
	public Project() {
	}
	
	public Project(long id, String name, String status, String description, Date createdDate, Date modifiedDate,
			Collection<Member> projectLeader, Collection<Member> projectMember, Collection<Milestone> milestones, Collection<Task> tasks) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.description = description;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.projectLeaders = projectLeader;
		this.projectMembers = projectMember;
		this.milestones = milestones;
		this.tasks = tasks;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Collection<Member> getProjectLeaders() {
		return projectLeaders;
	}
	public void setProjectLeader(Collection<Member> members) {
		this.projectLeaders = members;
	}
	public void addProjectLeader(Member projectLeader) {
		getProjectLeaders().add(projectLeader);
	}
	public void removeProjectLeader(Member projectLeader) {
		getProjectLeaders().remove(projectLeader);
	}
	public Collection<Member> getProjectMembers() {
		return projectMembers;
	}
	public void addProjectMember(Member projectMember) {
		getProjectMembers().add(projectMember);
	}
	public void removeProjectMember(Member projectMember) {
		getProjectMembers().remove(projectMember);
	}
	public void setProjectMember(Collection<Member> members) {
		this.projectMembers = members;
	}
	public Collection<Milestone> getMilestones() {
		return milestones;
	}
	public void addMilestone(Milestone milestone) {
		getMilestones().add(milestone);
	}
	public void setMilestone(Collection<Milestone> milestones) {
		this.milestones = milestones;
	}
	
	public Collection<Task> getTasks() {
		return this.tasks;
	}
	public void addTask(Task task) {
		this.tasks.add(task);
	}
	
	public void setTask(Collection<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void removeTask(Task task) {
		this.tasks.remove(task);
	}
	
}
