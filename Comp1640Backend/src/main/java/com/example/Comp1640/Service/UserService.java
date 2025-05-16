package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.DefinedUser;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.SearchStudentOrTutor;
import com.example.Comp1640.DTO.UserDto;
import com.example.Comp1640.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(Long id);

    Long findIdByUser(String username);

    List<UserDto> getAllUserDto();

    UserDto saveUser(DefinedUser definedUser, MultipartFile file) throws IOException;

    void deleteUser(Long id);

    UserDto updateUser(Long id, UserDto userDto);

    List<UserDto> findTutor(SearchStudentOrTutor searchStudentOrTutor);

    List<UserDto> findStudent(SearchStudentOrTutor searchStudentOrTutor);

}
