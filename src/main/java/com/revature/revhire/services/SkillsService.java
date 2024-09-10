package com.revature.revhire.services;

import com.revature.revhire.exceptions.NotFoundException;
import com.revature.revhire.models.Skills;
import com.revature.revhire.repositories.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillsService {
    @Autowired
    SkillsRepository skillsRepository;

    public Skills createSkill(Skills skill){
        Skills existingSkill = skillsRepository.findBySkillName(skill.getSkillName());
        if (existingSkill != null) {
            return existingSkill;
        }
        return skillsRepository.save(skill);
    }

    public Skills getSkillByName(String skill){
        Skills dbSkill=skillsRepository.findBySkillName(skill);
        if(dbSkill==null){
            throw new NotFoundException("No skill with name: "+skill);
        }
        return dbSkill;
    }

    public List<Skills> getAllSkills(){
        return skillsRepository.findAll();
    }

}

