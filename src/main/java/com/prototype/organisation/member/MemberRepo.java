package com.prototype.organisation.member;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface MemberRepo extends CrudRepository<Member, UUID> {
	public Member findByFirstName(String firstName);
	public Member findByLastName(String lastName);
	public Member findByEmail(String email);
}
