package com.revature.revhire.SkillTest;

import com.revature.revhire.exceptions.NotFoundException;
import com.revature.revhire.models.Skills;
import com.revature.revhire.repositories.SkillsRepository;
import com.revature.revhire.services.SkillsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class SkillTest {
    @Mock
    private SkillsRepository skillsRepository;

    @InjectMocks
    private SkillsService skillService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSkill() {
        Skills skill = new Skills();
        skill.setSkillName("Java");

        when(skillsRepository.save(skill)).thenReturn(skill);

        Skills createdSkill = skillService.createSkill(skill);

        assertEquals(skill, createdSkill);
    }

    @Test
    void testGetSkillByName_Success() {
        String skillName = "Java";
        Skills skill = new Skills();
        skill.setSkillName(skillName);

        when(skillsRepository.findBySkillName(skillName)).thenReturn(skill);

        Skills retrievedSkill = skillService.getSkillByName(skillName);

        assertEquals(skill, retrievedSkill);
    }

    @Test
    void testGetSkillByName_NotFound() {
        String skillName = "Python";

        when(skillsRepository.findBySkillName(skillName)).thenReturn(null);

        NotFoundException thrownException = assertThrows(
                NotFoundException.class,
                () -> skillService.getSkillByName(skillName),
                "Expected getSkillByName() to throw NotFoundException"
        );

        assertEquals("No skill with name: " + skillName, thrownException.getMessage());
    }
}
