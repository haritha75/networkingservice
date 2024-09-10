package com.revature.revhire.repositories;

import com.revature.revhire.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Object> findByEmail(String email);

    Optional<Object> findByUserName(String username);
}

