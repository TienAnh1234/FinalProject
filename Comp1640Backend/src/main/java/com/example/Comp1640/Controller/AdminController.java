package com.example.Comp1640.Controller;

import com.example.Comp1640.DTO.AdminDto;
import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.Service.AdminService;
import com.example.Comp1640.Service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping()
    public ResponseEntity<List<AdminDto>> getAllStudentDto() {
        List<AdminDto> AdminDtos = adminService.getAllAdminDto();
        if(!(AdminDtos == null)) {
            return ResponseEntity.ok(AdminDtos);
        }else{
            return null;
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @ModelAttribute AdminDto adminDto, @RequestParam(value = "file",required = false) MultipartFile file ) throws IOException {
        return ResponseEntity.ok(adminService.updateAdmin(id, adminDto,file));
    }



}
