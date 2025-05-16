package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.DistrictDto;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Repository.DistrictRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.ClassroomService;
import com.example.Comp1640.Service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/district")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping()
    public ResponseEntity<List<DistrictDto>> getAllDistrictDto() {
        return ResponseEntity.ok(districtService.getAllDistrict());
    }



}
