package com.prototype.organisation.milestone;

import javax.persistence.Embeddable;

@Embeddable
public class Milestone {
	private String name;
	private String description;
	private String status;
	
	public Milestone() {
	}
	
	public Milestone(String name, String description, String status) {
		super();
		this.name = name;
		this.description = description;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
