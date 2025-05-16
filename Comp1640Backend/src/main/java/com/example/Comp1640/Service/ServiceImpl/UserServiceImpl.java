package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.DefinedUser;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.SearchStudentOrTutor;
import com.example.Comp1640.DTO.UserDto;
import com.example.Comp1640.Entity.*;
import com.example.Comp1640.Repository.AdminRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String UPLOAD_DIR = "uploads/";


    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Long findIdByUser(String username) {
        return userRepository.findIdByUsername(username);
    }


    @Override
    public List<UserDto> getAllUserDto() {
        List<User> listUsers = userRepository.findAll();
        List<UserDto> listUserDto = new ArrayList<>();

        if (!listUsers.isEmpty()) {
            for(int i = 0; i < listUsers.size(); i++){
                UserDto userDto = new UserDto(listUsers.get(i).getId(),listUsers.get(i).getUsername(),listUsers.get(i).getEmail(),listUsers.get(i).getPassword(),listUsers.get(i).getRole());
                userDto.setDistrictId(listUsers.get(i).getDistrict().getId());
                listUserDto.add(userDto);
            }
            return listUserDto;
        } else {
            return null;
        }

    }

    @Override
    public UserDto saveUser(DefinedUser definedUser, MultipartFile file) throws IOException {

        User user = new User(definedUser.getUsername(),definedUser.getEmail(), definedUser.getPassword(), definedUser.getRole());
        user.setDistrict(definedUser.getDistrict());
        UserDto userDto = new UserDto(definedUser.getUsername(),definedUser.getEmail(), definedUser.getPassword(), definedUser.getRole());
        userDto.setDistrictId(definedUser.getDistrict().getId());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        if(Objects.equals(definedUser.getRole().toString(), "STUDENT")){
            Student student = new Student(definedUser.getName(), definedUser.getBirthday(), user);
            String imageUrl = "https://www.gravatar.com/avatar/?d=mp&s=100";
            if (file != null && !file.isEmpty()) {
                imageUrl = imageUploadService.uploadImage(file);
            }
            student.setImageFile(imageUrl);
            studentRepository.save(student);
        } else if (Objects.equals(definedUser.getRole().toString(), "TUTOR")) {
            Tutor tutor = new Tutor(definedUser.getName(), definedUser.getBirthday(), user, "WAITTING");
            String imageUrl = "https://www.gravatar.com/avatar/?d=mp&s=100";
            if (file != null && !file.isEmpty()) {
                imageUrl = imageUploadService.uploadImage(file);
            }
            tutor.setImageFile(imageUrl);
            tutorRepository.save(tutor);
        }else{
            Admin admin = new Admin(definedUser.getName(), definedUser.getBirthday(), definedUser.getImageFile(), user);
            String imageUrl = "https://www.gravatar.com/avatar/?d=mp&s=100";
            if (file != null && !file.isEmpty()) {
                imageUrl = imageUploadService.uploadImage(file);
            }
            admin.setImageFile(imageUrl);
            adminRepository.save(admin);
        }


        return userDto;

    }


    @Override
    public void deleteUser(Long id) {
        String role = userRepository.findRoleByUserId(id);

        if(Objects.equals(role, "STUDENT")){
            System.out.println(role);
            userRepository.deleteStudentByUserId(id);
        }

        if(Objects.equals(role, "TUTOR")){
            System.out.println(role);
            userRepository.deleteTutorByUserId(id);
        }

        if(Objects.equals(role, "ADMIN")){
            System.out.println(role);
            userRepository.deleteAdminByUserId(id);
        }

        try {
            userRepository.deleteUserById(id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Không tìm thấy user để xóa!");
        }


    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        try {
            User existingUser = userRepository.findById(id).orElse(null);
            existingUser.setUsername(userDto.getUsername());
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            existingUser.setEmail(userDto.getEmail());
            userRepository.save(existingUser);
            return userDto;

        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public List<UserDto> findTutor(SearchStudentOrTutor searchStudentOrTutor) {
        String username = searchStudentOrTutor.getUsername();
        Long districtId = searchStudentOrTutor.getDistrictId();
        Long majorId = searchStudentOrTutor.getMajorId();
        Long gradeId = searchStudentOrTutor.getGradeId();
        List<Tutor> searchResult = tutorRepository.findTutorsByDynamicCriteriaNative(username, districtId, gradeId, majorId);
        System.out.println(searchResult.size() + " hanoi");

        List<UserDto> userDtos = new ArrayList<>();
        for (Tutor tutor : searchResult) {
            System.out.println(tutor.getName());
            User user = tutor.getUser();
            userDtos.add(new UserDto(user));
        }

        return userDtos;

    }

    @Override
    public List<UserDto> findStudent(SearchStudentOrTutor searchStudentOrTutor) {
        String username = searchStudentOrTutor.getUsername();
        Long districtId = searchStudentOrTutor.getDistrictId();
        List<Student> searchResult = studentRepository.findStudentsByDynamicCriteriaNative(username, districtId);

        List<UserDto> userDtos = new ArrayList<>();
        for (Student student : searchResult) {
            User user = student.getUser();
            userDtos.add(new UserDto(user));
        }
        return userDtos;
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
