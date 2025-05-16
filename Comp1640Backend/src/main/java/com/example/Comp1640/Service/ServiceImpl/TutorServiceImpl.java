package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.DTO.TutorDto;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.ClassroomRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Service.MajorService;
import com.example.Comp1640.Service.TutorService;
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
public class TutorServiceImpl implements TutorService {

    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MajorService majorService;

    @Autowired
    private ImageUploadService imageUploadService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String UPLOAD_DIR = "uploads/";



    @Override
    public List<TutorDto> getAllTutorDto() {
        List<Tutor> listTutors = tutorRepository.findAll();
        List<TutorDto> listStudentDto = new ArrayList<>();
        if (!listTutors.isEmpty()) {
            for(int i = 0; i < listTutors.size(); i++){
                TutorDto tutorDto = new TutorDto(listTutors.get(i).getId(),listTutors.get(i).getName(),listTutors.get(i).getBirthday(),listTutors.get(i).getImageFile(),listTutors.get(i).getUser().getUsername(),listTutors.get(i).getStatus());
                listStudentDto.add(tutorDto);
            }
            return listStudentDto;
        } else {
            return null;
        }
    }

    @Override
    public String updateTutor(Long id, TutorDto tutorDto, MultipartFile file) throws IOException {
        Tutor existingTutor = tutorRepository.findById(id).orElse(null);

        Long userId = userService.findIdByUser(tutorDto.getUsername());

        User user = userService.findUserById(userId).get();



        if(file != null) {
            String imageUrl = imageUploadService.uploadImage(file);
            tutorDto.setImageFile(imageUrl);
        }

        existingTutor.setName(tutorDto.getName());
        existingTutor.setBirthday(tutorDto.getBirthday());
        existingTutor.setImageFile(tutorDto.getImageFile());
        existingTutor.setUser(user);
        tutorRepository.save(existingTutor);

        return "Update successfully";
    }



    public boolean deleteFile(String fileName) {
        try {
            Path filePath = Paths.get("uploads", fileName);
            Files.delete(filePath);
            return true; // Xóa thành công
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Xóa thất bại
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        // Tạo thư mục lưu file nếu chưa có
        Path uploadPath = Path.of(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Tạo tên file duy nhất
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Lưu file vào thư mục
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + fileName; // Trả về đường dẫn file
    }

}
