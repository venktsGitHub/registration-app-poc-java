package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.entity.VerificationToken;


@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(Object object);

}
