package com.revature.revhire.Controllers;

import com.revature.revhire.models.Skills;
import com.revature.revhire.services.SkillsService;
import com.revature.revhire.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillsController {

    @Autowired
    private SkillsService skillsService;

    @PostMapping
    public ResponseEntity<BaseResponse<Skills>> setSkill(@RequestBody Skills skill){
        BaseResponse<Skills> baseResponse=new BaseResponse<>("Skill added",skillsService.createSkill(skill), HttpStatus.CREATED.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Skills>> fetchBySkill(@RequestParam String skill){
        BaseResponse<Skills> baseResponse=new BaseResponse<>("fetched skill",skillsService.getSkillByName(skill), HttpStatus.FOUND.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.FOUND);
    }

    @GetMapping("/allSkills")
    public ResponseEntity<BaseResponse<List<Skills>>> getAllSkills(){
        BaseResponse<List<Skills>> baseResponse=new BaseResponse<>("All skills fetched",skillsService.getAllSkills(), HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}

