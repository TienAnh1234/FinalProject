package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.CommentDto;
import com.example.Comp1640.DTO.DistrictDto;
import com.example.Comp1640.Entity.Comment;
import com.example.Comp1640.Entity.District;
import com.example.Comp1640.Repository.CommentRepository;
import com.example.Comp1640.Repository.DistrictRepository;
import com.example.Comp1640.Service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public List<DistrictDto> getAllDistrict() {
        List<District> districts =  districtRepository.findAll();
        List<DistrictDto> districtDtos = new ArrayList<>();

        if (districts.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < districts.size(); i++){
                DistrictDto districtDto = new DistrictDto(districts.get(i).getId(),districts.get(i).getName());
                districtDtos.add(districtDto);
            }
        }
        return districtDtos;
    }
}
