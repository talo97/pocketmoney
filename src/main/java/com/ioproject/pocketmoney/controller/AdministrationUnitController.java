package com.ioproject.pocketmoney.controller;

import com.ioproject.pocketmoney.entitiesDTO.AdministrationUnitDTO;
import com.ioproject.pocketmoney.service.ServiceAdministrationUnit;
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
public class AdministrationUnitController {

    @Autowired
    private ServiceAdministrationUnit serviceAdministrationUnit;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getAdministrationUnits")
    public ResponseEntity<List<AdministrationUnitDTO>> getAllAdministrationUnits() {
        Type listType = new TypeToken<List<AdministrationUnitDTO>>(){}.getType();
        return ResponseEntity.ok().body(modelMapper.map(this.serviceAdministrationUnit.getAll(), listType));
    }
}
