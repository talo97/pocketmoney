package com.ioproject.pocketmoney.controller;

import com.ioproject.pocketmoney.entitiesDTO.AdministrationUnitDTO;
import com.ioproject.pocketmoney.service.ServiceAdministrationUnit;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdministrationUnitController {

    private final ServiceAdministrationUnit serviceAdministrationUnit;

    private final ModelMapper modelMapper;

    public AdministrationUnitController(ServiceAdministrationUnit serviceAdministrationUnit, ModelMapper modelMapper) {
        this.serviceAdministrationUnit = serviceAdministrationUnit;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAdministrationUnits")
    public ResponseEntity<List<AdministrationUnitDTO>> getAllAdministrationUnits() {
        Type listType = new TypeToken<List<AdministrationUnitDTO>>(){}.getType();
        return ResponseEntity.ok().body(modelMapper.map(this.serviceAdministrationUnit.getAll(), listType));
    }
}
