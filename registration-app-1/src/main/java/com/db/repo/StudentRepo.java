package com.db.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long>{
	
	public List<Student> findByLName(String lName);

}
