package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/major")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping()
    public ResponseEntity<List<MajorDto>> getAllMajorDto() {
        return ResponseEntity.ok(majorService.getAllMajorDto());
    }

    @PostMapping("/save_major")
    public ResponseEntity<MajorDto> saveNew(@RequestBody(required = false) MajorDto majorDto) {
        return ResponseEntity.ok(majorService.saveMajor(majorDto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMajor(@PathVariable Long id) {
        majorService.deleteMajor(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MajorDto> update(@PathVariable Long id,@RequestBody(required = false) MajorDto majorDto ) {return ResponseEntity.ok(majorService.updateMajor(id, majorDto));
    }



}
