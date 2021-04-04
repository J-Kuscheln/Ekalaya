package com.prototype.organisation.member;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prototype.organisation.project.Project;

@CrossOrigin(origins = {"http://192.168.178.31:4200", "http://localhost:4200"})
@RestController
@RequestMapping(path = "/members")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	//get all members
	@GetMapping
	public List<Member> getMembers() {
		return service.getMembers();
	}
	
	//get particular member by id
	@GetMapping("/{id}")
	public Member getMember(@PathVariable String id) {
		try {
			UUID uuid = UUID.fromString(id);
			Member member = service.getMember(uuid);
			
			return member;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//save new member
	@PostMapping
	public HttpStatus saveMember(@RequestBody Member member, @RequestHeader("pass") String pass) {
		try {
				System.out.println("member Password: " + pass);
				System.out.println("member name: " + member.getFirstName());
				member.setPassword(pass);
				return service.saveMember(member);
			}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			return HttpStatus.BAD_REQUEST;
		}
	}
	
	//update member
	@PutMapping("/{id}")
	public HttpStatus updateMember(@RequestBody Member memberBody, @PathVariable String id, @RequestHeader("newP") String newPass, 
			HttpServletResponse response) {
		try {
			HttpStatus status;
			UUID uuid = UUID.fromString(id);

			
			Member memberOld = service.getMember(uuid);
			Member otherMember = service.getMemberByEmail(memberBody.getEmail());
			if(otherMember!=null && !otherMember.getEmail().equals(memberOld.getEmail())) {
				status = HttpStatus.IM_USED;
				response.setStatus(status.value());
				return status;
			}
			memberBody.setId(uuid);
			memberBody.setJoinDate(memberOld.getJoinDate());
			memberBody.setModifiedDate(memberOld.getModifiedDate());
			memberBody.setLeadedProjects(memberOld.getLeadedProjects());
			memberBody.setMemberProjects(memberOld.getMemberProjects());
			if(!newPass.equals("0")) memberBody.setPassword(newPass);
			else memberBody.setPassword(memberOld.getPassword());
			
			status = service.updateMember(memberBody);
			//status = HttpStatus.OK;
			response.setStatus(status.value());
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			HttpStatus status = HttpStatus.BAD_REQUEST;
			response.setStatus(status.value());
			return status;
		}
	}
	
	//delete new member
	@DeleteMapping("/{id}")
	public HttpStatus deleteMember(@PathVariable String id) {
		try {
			UUID uuid = UUID.fromString(id);
			return service.deleteMember(uuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return HttpStatus.BAD_REQUEST;
		}
	}
	
	//get member leaded projects
	@GetMapping("/{id}/projects/lead")
	public Collection<Project> getLeadedProjects(@PathVariable String id){
		try{
			UUID uuid = UUID.fromString(id);
			return service.getMember(uuid).getLeadedProjects();
		}
		catch(Exception e){
			return null;
		}
		
	}
	
	//get member projects (as member)
	@GetMapping("/{id}/projects/member")
	public Collection<Project> getMemberProjects(@PathVariable String id){
		try {
			UUID uuid = UUID.fromString(id);
			return service.getMember(uuid).getMemberProjects();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//get member projects (all kinds)
	@GetMapping("/{id}/projects")
	public Collection<Project> getProjects(@PathVariable String id){
		try {
			UUID uuid = UUID.fromString(id);
			Collection<Project> projects = new ArrayList<>();
			projects = service.getMember(uuid).getMemberProjects();
			service.getMember(uuid).getLeadedProjects().forEach(projects::add);
			return projects;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
