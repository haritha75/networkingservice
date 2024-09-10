package com.revature.revhire.SkillTest;

import com.revature.revhire.Controllers.SkillsController;
import com.revature.revhire.models.Skills;
import com.revature.revhire.services.SkillsService;
import com.revature.revhire.utilities.BaseResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SkillControllerTest {
    @InjectMocks
    private SkillsController skillController;

    @Mock
    private SkillsService skillService;

    private Skills skill;

    @Before
    public void setup() {
        skill = new Skills();
        skill.setSkillName("Java");
    }

    @Test
    public void testSetSkill() {
        when(skillService.createSkill(skill)).thenReturn(skill);

        ResponseEntity<BaseResponse<Skills>> response = skillController.setSkill(skill);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Skill added", response.getBody().getMessages());
        assertEquals(skill, response.getBody().getData());
    }

    @Test
    public void testFetchBySkill() {
        when(skillService.getSkillByName("Java")).thenReturn(skill);

        ResponseEntity<BaseResponse<Skills>> response = skillController.fetchBySkill("Java");

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("fetched skill", response.getBody().getMessages());
        assertEquals(skill, response.getBody().getData());
    }
}
