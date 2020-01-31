package com.ioproject.pocketmoney.controller;

import com.ioproject.pocketmoney.entitiesDTO.AdministrationUnitDTO;
import com.ioproject.pocketmoney.entitiesDTO.EducationLevelDTO;
import com.ioproject.pocketmoney.service.ServiceEducation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EducationLevelController {

    @Autowired
    private ServiceEducation serviceEducation;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getEducationLevels")
    public ResponseEntity<List<EducationLevelDTO>> getAllEducationLevels(){
        Type listType = new TypeToken<List<EducationLevelDTO>>(){}.getType();
        return ResponseEntity.ok().body(modelMapper.map(serviceEducation.getAll(), listType));
    }
}
