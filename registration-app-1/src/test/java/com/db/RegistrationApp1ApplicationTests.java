package com.db;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.db.entity.Student;
import com.db.repo.StudentRepo;

@SpringBootTest
class RegistrationApp1ApplicationTests {

	@Autowired
	private StudentRepo sRepo;
	
	
	@Test
	void addStudentDetails() {
		
		Student st = Student.builder()
						.fName("Kamli")
						.lName("Mogga")
						.dob(Date.valueOf("1998-05-11"))
						.address("trichy")
						.build();
		
		sRepo.save(st);
	}

	
	
}
