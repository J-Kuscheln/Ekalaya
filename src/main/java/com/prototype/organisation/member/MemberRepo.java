package com.prototype.organisation.member;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface MemberRepo extends CrudRepository<Member, UUID> {
	public Member findByFirstName(String firstName);
	public Member findByLastName(String lastName);
	public Member findByEmail(String email);
}
