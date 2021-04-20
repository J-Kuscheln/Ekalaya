package com.prototype.organisation.member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	@Autowired
	private MemberRepo memberRepo;
	
	//get all members
	public List<Member> getMembers() {
		List<Member> members = new ArrayList<>();
		memberRepo.findAll().forEach(members::add);
		initializeAllLazyCollection(members);
		return members;
	}
	
	//get particular member by id
	public Member getMember(UUID id) {
		return memberRepo.findById(id).get();
	}
	
	//save new member
	public HttpStatus saveMember(Member member) {
		if(memberRepo.findByEmail(member.getEmail())!=null) return HttpStatus.IM_USED;
		if(memberRepo.save(member)!=null) return HttpStatus.CREATED;
		return HttpStatus.EXPECTATION_FAILED;
	}
	
	//update member
	public HttpStatus updateMember(Member member) {
		if(memberRepo.existsById(member.getId())) {
			if(memberRepo.save(member)!=null) return HttpStatus.OK;
			return HttpStatus.EXPECTATION_FAILED;
		}
		return HttpStatus.NOT_FOUND;
	}
	
	//delete member
	public HttpStatus deleteMember(UUID id) {
		memberRepo.deleteById(id);
		return HttpStatus.OK;
	}

	public Member getMemberByEmail(String email) {
		return memberRepo.findByEmail(email);
	}
	
	private void initializeAllLazyCollection(List<Member> members) {
		members.stream().forEach(member->{
			member.getLeadedProjects().size();
			member.getMemberProjects().size();
			member.getFinishedProjects().size();
			member.getTasks().size();
		});
	}
}
