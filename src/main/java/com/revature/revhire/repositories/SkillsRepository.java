package com.revature.revhire.repositories;

import com.revature.revhire.models.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long> {
    Skills findBySkillName(String skill);
}

