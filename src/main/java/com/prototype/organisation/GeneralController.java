package com.prototype.organisation;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prototype.organisation.member.Member;
import com.prototype.organisation.member.MemberService;
import com.prototype.organisation.project.Project;
import com.prototype.organisation.project.ProjectService;

@CrossOrigin(origins = {"http://192.168.178.31:4200","http://localhost:4200"})
@Controller
public class GeneralController {
	@Autowired
	ProjectService projectService;
	@Autowired
	MemberService memberService;
	
	
	@RequestMapping("/")
	public String home(Model model) {
		List<Member> members = memberService.getMembers();
		Collection<Project> projects = projectService.getAllProjects();
		if (members.size()>0) model.addAttribute("members", members);
		if (projects.size()>0) model.addAttribute("projects", projects);
		return "index";
	}
	
	
	@RequestMapping(method = RequestMethod.POST,value = "/relate")
	//@RequestParam(name="memberId") String memberId, @RequestParam(name="projectId") long projectId, @RequestParam(name="toDo") String toDo
	public String relate(@RequestBody RelateObjects body) {
		try{
			Member member = memberService.getMember(body.getMemberId());
			Project project = projectService.getProject(body.getProjectId());
			
			switch(body.getToDo()) {
				case("asLeader"):
					for(Member leader : project.getProjectLeaders()) {
						if (leader.getId().equals(body.getMemberId())) {
							System.out.println(HttpStatus.IM_USED);
							return "redirect:/projects";
						}
					}
					member.addLeadedProjects(project);
					project.addProjectLeader(member);
					break;
					
				case("asMember"):
					for(Member leader : project.getProjectMembers()) {
						if (leader.getId().equals(body.getMemberId())) {
							System.out.println(HttpStatus.IM_USED);
							return "redirect:/";
						}
					}
					member.addMemberProjects(project);
					project.addProjectMember(member);
					break;
					
				case("removeAsLeader"):
					for(Member leader : project.getProjectLeaders()) {
						if (leader.getId().equals(body.getMemberId())) {
							member.removeLeadedProjects(project);
							project.removeProjectLeader(member);
							break;
						}
					}
					break;
				case("removeAsMember"):
					System.out.println("searching for match");
					for(Member memb : project.getProjectMembers()) {
						if (memb.getId().equals(body.getMemberId())) {
							member.removeMemberProjects(project);
							project.removeProjectMember(member);
							break;
						}
					}
					
			}
			System.out.println(memberService.updateMember(member));
			System.out.println(projectService.updateProject(project));
			return "redirect:/";
		}
		catch(Exception e) {
			System.out.println(HttpStatus.BAD_REQUEST);
			return "redirect:/";
		}
	}
	
}
