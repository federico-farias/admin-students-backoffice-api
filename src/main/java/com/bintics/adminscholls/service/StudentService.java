package com.bintics.adminscholls.service;

import com.bintics.adminscholls.dto.StudentDTO;
import com.bintics.adminscholls.model.Student;
import com.bintics.adminscholls.model.Group;
import com.bintics.adminscholls.repository.StudentRepository;
import com.bintics.adminscholls.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentDTO::new)
                .toList();
    }

    public List<StudentDTO> getActiveStudents() {
        return studentRepository.findByIsActiveTrue()
                .stream()
                .map(StudentDTO::new)
                .toList();
    }

    public Page<StudentDTO> searchStudents(String search, Pageable pageable) {
        return studentRepository.findActiveStudentsBySearch(search, pageable)
                .map(StudentDTO::new);
    }

    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(StudentDTO::new);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = Student.builder()
                .firstName(studentDTO.getFirstName())
                .lastName(studentDTO.getLastName())
                .email(studentDTO.getEmail())
                .phone(studentDTO.getPhone())
                .dateOfBirth(studentDTO.getDateOfBirth())
                .grade(studentDTO.getGrade())
                .section(studentDTO.getSection())
                .parentName(studentDTO.getParentName())
                .parentPhone(studentDTO.getParentPhone())
                .parentEmail(studentDTO.getParentEmail())
                .address(studentDTO.getAddress())
                .enrollmentDate(studentDTO.getEnrollmentDate())
                .emergencyContactName(studentDTO.getEmergencyContactName())
                .emergencyContactPhone(studentDTO.getEmergencyContactPhone())
                .emergencyContactRelationship(studentDTO.getEmergencyContactRelationship())
                .isActive(true)
                .build();

        // Asignar grupo si se especifica
        if (studentDTO.getGroupId() != null) {
            Optional<Group> group = groupRepository.findById(studentDTO.getGroupId());
            if (group.isPresent() && group.get().hasAvailableSpaces()) {
                student.setGroup(group.get());
            }
        }

        Student savedStudent = studentRepository.save(student);
        return new StudentDTO(savedStudent);
    }

    public Optional<StudentDTO> updateStudent(Long id, StudentDTO studentDTO) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setFirstName(studentDTO.getFirstName());
                    existingStudent.setLastName(studentDTO.getLastName());
                    existingStudent.setEmail(studentDTO.getEmail());
                    existingStudent.setPhone(studentDTO.getPhone());
                    existingStudent.setDateOfBirth(studentDTO.getDateOfBirth());
                    existingStudent.setGrade(studentDTO.getGrade());
                    existingStudent.setSection(studentDTO.getSection());
                    existingStudent.setParentName(studentDTO.getParentName());
                    existingStudent.setParentPhone(studentDTO.getParentPhone());
                    existingStudent.setParentEmail(studentDTO.getParentEmail());
                    existingStudent.setAddress(studentDTO.getAddress());
                    existingStudent.setEnrollmentDate(studentDTO.getEnrollmentDate());
                    existingStudent.setEmergencyContactName(studentDTO.getEmergencyContactName());
                    existingStudent.setEmergencyContactPhone(studentDTO.getEmergencyContactPhone());
                    existingStudent.setEmergencyContactRelationship(studentDTO.getEmergencyContactRelationship());

                    // Actualizar grupo si ha cambiado
                    if (studentDTO.getGroupId() != null &&
                        !studentDTO.getGroupId().equals(existingStudent.getGroup() != null ?
                        existingStudent.getGroup().getId() : null)) {
                        Optional<Group> newGroup = groupRepository.findById(studentDTO.getGroupId());
                        if (newGroup.isPresent() && newGroup.get().hasAvailableSpaces()) {
                            existingStudent.setGroup(newGroup.get());
                        }
                    }

                    Student savedStudent = studentRepository.save(existingStudent);
                    return new StudentDTO(savedStudent);
                });
    }

    public boolean assignToGroup(Long studentId, Long groupId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Group> groupOpt = groupRepository.findById(groupId);

        if (studentOpt.isPresent() && groupOpt.isPresent()) {
            Group group = groupOpt.get();
            if (group.hasAvailableSpaces()) {
                Student student = studentOpt.get();
                student.setGroup(group);
                studentRepository.save(student);
                return true;
            }
        }
        return false;
    }

    public void deactivateStudent(Long id) {
        studentRepository.findById(id)
                .ifPresent(student -> {
                    student.setIsActive(false);
                    studentRepository.save(student);
                });
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> getStudentsByGroup(Long groupId) {
        return studentRepository.findByGroupId(groupId)
                .stream()
                .map(StudentDTO::new)
                .toList();
    }

    public Long getActiveStudentsCount() {
        return studentRepository.countActiveStudents();
    }
}
