package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.DTO.UserDto;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.ClassroomRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.MajorService;
import com.example.Comp1640.Service.StudentService;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ImageUploadService imageUploadService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String UPLOAD_DIR = "uploads/";




    @Override
    public List<StudentDto> getAllStudentDto() {
        List<Student> listStudents = studentRepository.findAll();
        List<StudentDto> listStudentDto = new ArrayList<>();

        if (!listStudents.isEmpty()) {
            for(int i = 0; i < listStudents.size(); i++){
                StudentDto studentDto = new StudentDto(listStudents.get(i).getId(),listStudents.get(i).getName(),listStudents.get(i).getBirthday(),listStudents.get(i).getImageFile(),listStudents.get(i).getUser().getUsername());
                listStudentDto.add(studentDto);
            }
            return listStudentDto;
        } else {
            return null;
        }
    }


    @Override
    public String updateStudent(Long id, StudentDto studentDto, MultipartFile file) throws IOException{
        Student existingStudent = studentRepository.findById(id).orElse(null);

        Long userId = userService.findIdByUser(studentDto.getUsername());

        User user = userService.findUserById(userId).get();




        if(file != null) {
            String imageUrl = imageUploadService.uploadImage(file);

            studentDto.setImageFile(imageUrl);
        }



        existingStudent.setName(studentDto.getName());
        existingStudent.setBirthday(studentDto.getBirthday());
        existingStudent.setImageFile(studentDto.getImageFile());
        existingStudent.setUser(user);
        studentRepository.save(existingStudent);

        return "Update successfully";
    }


//    public boolean deleteFile(String fileName) {
//        try {
//            Path filePath = Paths.get("uploads", fileName);
//            Files.delete(filePath);
//            return true; // Xóa thành công
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false; // Xóa thất bại
//        }
//    }
//
//    private String saveFile(MultipartFile file) throws IOException {
//        // Tạo thư mục lưu file nếu chưa có
//        Path uploadPath = Path.of(UPLOAD_DIR);
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        // Tạo tên file duy nhất
//        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//
//        // Lưu file vào thư mục
//        Path filePath = uploadPath.resolve(fileName);
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        return "/uploads/" + fileName; // Trả về đường dẫn file
//    }



}
