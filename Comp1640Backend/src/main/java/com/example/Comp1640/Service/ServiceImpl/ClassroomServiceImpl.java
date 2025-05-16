package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.AddFriendRequestDto;
import com.example.Comp1640.DTO.ClassroomDto;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Entity.*;
import com.example.Comp1640.Repository.*;
import com.example.Comp1640.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;


    @Autowired
    private AddFriendRequestRepository addFriendRequestRepository;

    @Override
    public List<ClassroomDto> getAllClassroomDto() {
        List<Classroom> classrooms = classroomRepository.findAll();
        List<ClassroomDto> classroomDtos = new ArrayList<>();



        Map<Long, List<Classroom>> groupedClassroomMap = classrooms.stream()
                            .filter(c -> c.getTutor() != null) // Lọc các bản ghi có tutor
                            .collect(Collectors.groupingBy(c -> c.getTutor().getId()));




        groupedClassroomMap.forEach((tutorId, classroomsOfTutor) -> {
            List<Long> StudentsId = new ArrayList<>();
            for (Classroom classroom : classroomsOfTutor) {
                StudentsId.add(classroom.getStudent().getId());
            }
            classroomDtos.add(new ClassroomDto(tutorId,StudentsId));
        });

        return classroomDtos;
    }

    @Override
    public String saveClassroom(ClassroomDto classroomDto) {
        Tutor tutor = tutorRepository.findById(classroomDto.getTutorId()).get();

        for(int i = 0 ; i < classroomDto.getStudentsId().size() ; i++){
            Student student = studentRepository.findById(classroomDto.getStudentsId().get(i)).get();

            mailService.sendMail(
                    student.getUser().getEmail(),
                    "Thông báo phân lớp",
                    "hello, bạn đã đc phân lớp với teacher " + tutor.getUser().getUsername()
            );

            mailService.sendMail(
                    tutor.getUser().getEmail(),
                    "Thông báo phân lớp",
                    "hello, bạn đã đc phân lớp với student " + student.getUser().getUsername()
            );

            classroomRepository.save(new Classroom(tutor, student));
        }

        return "Saved successfully";
    }

    @Override
    public String updateClassroom(ClassroomDto classroomDtoInput) {
        List<ClassroomDto> allClassroomDtos = this.getAllClassroomDto();
        List<ClassroomDto> filteredListClassroomDtos = allClassroomDtos.stream()
                .filter(classroomDto -> classroomDto.getTutorId() == classroomDtoInput.getTutorId())
                .collect(Collectors.toList());


        for (int i = 0; i < filteredListClassroomDtos.get(0).getStudentsId().size(); i++) {
//            System.out.println("Student " + (i + 1) + ": " + filteredListClassroomDtos.get(0).getStudentsId().get(i));
            classroomRepository.deleteByStudentIdAndTutorId(filteredListClassroomDtos.get(0).getStudentsId().get(i),filteredListClassroomDtos.get(0).getTutorId());
            scheduleRepository.deleteByTutorIdAndStudentId(filteredListClassroomDtos.get(0).getTutorId(),filteredListClassroomDtos.get(0).getStudentsId().get(i));
        }

        saveClassroom(classroomDtoInput);

        return "Updated successfully";
    }


    @Override
    public void deleteClassroom(Long tutorId) {
        List<Schedule> mySchedules = scheduleRepository.findByTutorId(tutorId);
        System.out.println(mySchedules.size());
        classroomRepository.deleteByTutorId(tutorId);
        scheduleRepository.deleteByTutorId(tutorId);
    }

    @Override
    public void deleteClassroomByStudentIdAndTutorId(Long tutorId,Long studentId) {
        classroomRepository.deleteByStudentIdAndTutorId(studentId, tutorId);
        scheduleRepository.deleteByTutorIdAndStudentId(tutorId, studentId);
    }

    @Override
    public List<AddFriendRequest> getAllAddFriendRequest() {
        List<AddFriendRequest> listAddFriendRequests = addFriendRequestRepository.findAll();
        return listAddFriendRequests;
    }


    @Override
    public String saveClassroomMain(AddFriendRequestDto addFriendRequestDto) {
        User user1 = userRepository.findByUsername(addFriendRequestDto.getSender()).get();
        User user2 = userRepository.findByUsername(addFriendRequestDto.getRecipient()).get();
        Tutor tutor;
        Student student;

        if(tutorRepository.findIdByUserId(user1.getId()) != null){
            tutor = tutorRepository.findById(tutorRepository.findIdByUserId(user1.getId())).get();
        }else{
            tutor = tutorRepository.findById(tutorRepository.findIdByUserId(user2.getId())).get();
        }

        if(studentRepository.findIdByUserId(user1.getId()) != null){
            student = studentRepository.findById(studentRepository.findIdByUserId(user1.getId())).get();
        }else{
            student = studentRepository.findById(studentRepository.findIdByUserId(user2.getId())).get();
        }


        classroomRepository.save(new Classroom(tutor, student));
        return "Saved successfully";
    }


}
