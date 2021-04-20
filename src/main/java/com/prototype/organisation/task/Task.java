package com.prototype.organisation.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prototype.organisation.CustomMembersSerializer;
import com.prototype.organisation.CustomProjectSerializer;
import com.prototype.organisation.member.Member;
import com.prototype.organisation.project.Project;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Task {
	@Id
	@Column(nullable = false, unique = true, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Lob
	@Column(length=3000)
	private String description;
	private String status;
	@CreatedDate
	private Date createdDate = new Date();
	@LastModifiedDate
	private Date modifiedDate = new Date();
	@Column(nullable=true)
	private Date dueDate;
	@JsonSerialize(using = CustomProjectSerializer.class)
	@ManyToOne()
	private Project project; 
	@JsonSerialize(using = CustomMembersSerializer.class)
	@ManyToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	private Collection<Member> members = new ArrayList<>();
	
	
	public Task() {
	}


	public Task(int id, String name, String description, String status, Date createdDate, Date modifiedDate, Date dueDate, Project projects,
			Collection<Member> members) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.dueDate = dueDate;
		this.project = projects;
		this.members = members;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	

	public void setProject(Project project) {
		this.project = project;
	}
	public Project getProject() {
		return this.project;
	}


	public Collection<Member> getMembers() {
		return members;
	}


	public void setMembers(Collection<Member> members) {
		this.members = members;
	} 
	
	public void addMembers(Member member) {
		this.members.add(member);
	}

	public void removeMembers(Member member) {
		this.members.remove(member);
	}	
	
	
}
