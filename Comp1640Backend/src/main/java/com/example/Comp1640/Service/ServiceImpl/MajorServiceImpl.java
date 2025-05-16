package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.MajorRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Optional<Major> findMajorById(Long id) {
        return majorRepository.findById(id);
    }

    @Override
    public Long findIdByMajor(String majorName) {
        return majorRepository.findIdByMajorname(majorName);
    }


    @Override
    public List<MajorDto> getAllMajorDto() {
        List<Major> listMajors = majorRepository.findAll();
        List<MajorDto> listMajorDto = new ArrayList<>();

        if (listMajors.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < listMajors.size(); i++){
                MajorDto majorDto = new MajorDto(listMajors.get(i).getId(),listMajors.get(i).getName(),listMajors.get(i).getDescription());
                listMajorDto.add(majorDto);
            }
        }
        return listMajorDto;
    }


    @Override
    public MajorDto saveMajor(MajorDto majorDto) {
        if(majorDto == null){
            return null;
        }else {
            Major major = new Major(majorDto.getName(), majorDto.getDescription());
            majorRepository.save(major);
            return majorDto;
        }
    }

    @Override
    public void deleteMajor(Long id) {
        majorRepository.deleteById(id);
    }

    @Override
    public MajorDto updateMajor(Long id, MajorDto majorDto) {
        try {
            Major existingMajor = majorRepository.findById(id).orElse(null);
            existingMajor.setName(majorDto.getName());
            existingMajor.setDescription(majorDto.getDescription());
            majorRepository.save(existingMajor);
            return majorDto;

        }
        catch(Exception e){
            return null;
        }
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

}
