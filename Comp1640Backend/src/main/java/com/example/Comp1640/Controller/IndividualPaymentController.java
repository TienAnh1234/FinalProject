package com.example.Comp1640.Controller;

import com.example.Comp1640.DTO.GradeDto;
import com.example.Comp1640.DTO.IndividualPaymentDto;
import com.example.Comp1640.Service.GradeService;
import com.example.Comp1640.Service.IndividualPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/payment")
public class IndividualPaymentController {

    @Autowired
    private IndividualPaymentService individualPaymentService;

    @GetMapping()
    public ResponseEntity<List<IndividualPaymentDto>> getAllIndividualPaymentDto() {
        return ResponseEntity.ok(individualPaymentService.getAllIndividualPaymentDto());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIndividualPayment(@PathVariable Long id) {
        individualPaymentService.deleteIndividualPaymentDto(id);
    }

}
