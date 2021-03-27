package com.prototype.organisation;

import java.util.UUID;

public class RelateObjects {
	private UUID memberId;
	private int projectId;
	private String toDo;
	
	public RelateObjects() {
		
	}

	public UUID getMemberId() {
		return memberId;
	}

	public void setMemberId(UUID memberId) {
		this.memberId = memberId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getToDo() {
		return toDo;
	}

	public void setToDo(String toDo) {
		this.toDo = toDo;
	}
	
	
}
