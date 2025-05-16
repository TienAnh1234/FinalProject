package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.GradeDto;
import com.example.Comp1640.DTO.IndividualPaymentDto;

import java.util.List;

public interface IndividualPaymentService {

    List<IndividualPaymentDto> getAllIndividualPaymentDto();

    void deleteIndividualPaymentDto(Long id);


}
