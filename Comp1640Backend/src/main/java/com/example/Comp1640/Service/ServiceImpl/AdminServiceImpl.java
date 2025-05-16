package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.AdminDto;
import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.Entity.Admin;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.AdminRepository;
import com.example.Comp1640.Repository.ClassroomRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Service.AdminService;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ImageUploadService imageUploadService;


    @Override
    public List<AdminDto> getAllAdminDto() {
        List<Admin> listAdmins = adminRepository.findAll();
        List<AdminDto> listAdminDtos = new ArrayList<>();

        if (!listAdmins.isEmpty()) {
            for(int i = 0; i < listAdmins.size(); i++){
                AdminDto adminDto = new AdminDto(listAdmins.get(i).getId(),listAdmins.get(i).getName(),listAdmins.get(i).getBirthday(),listAdmins.get(i).getImageFile(),listAdmins.get(i).getUser().getUsername());
                listAdminDtos.add(adminDto);
            }
            return listAdminDtos;
        } else {
            return null;
        }
    }


    @Override
    public String updateAdmin(Long id, AdminDto adminDto, MultipartFile file) throws IOException {
        Admin existingAdmin = adminRepository.findById(id).orElse(null);

        Long userId = userService.findIdByUser(adminDto.getUsername());

        User user = userService.findUserById(userId).get();



        if(file != null) {
            String imageUrl = imageUploadService.uploadImage(file);
            adminDto.setImageFile(imageUrl);
        }

        existingAdmin.setName(adminDto.getName());
        existingAdmin.setBirthday(adminDto.getBirthday());
        existingAdmin.setImageFile(adminDto.getImageFile());
        existingAdmin.setUser(user);
        adminRepository.save(existingAdmin);

        return "Update successfully";
    }



}
