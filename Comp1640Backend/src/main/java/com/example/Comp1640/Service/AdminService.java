package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.AdminDto;
import com.example.Comp1640.DTO.TutorDto;
import com.example.Comp1640.Entity.Admin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    List<AdminDto> getAllAdminDto();
    String updateAdmin(Long id, AdminDto adminDto, MultipartFile file) throws IOException;


}
