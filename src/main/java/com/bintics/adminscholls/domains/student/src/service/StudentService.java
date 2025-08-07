package com.bintics.adminscholls.domains.student.src.service;

import com.bintics.adminscholls.domains.student.src.dto.StudentDTO;
import com.bintics.adminscholls.domains.student.src.model.Student;
import com.bintics.adminscholls.domains.student.src.repository.StudentRepository;
import com.bintics.adminscholls.domains.group.src.repository.GroupRepository;
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

    public Optional<StudentDTO> getStudentByPublicId(String publicId) {
        return studentRepository.findByPublicId(publicId)
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
                .emergencyContactName(studentDTO.getEmergencyContactName())
                .emergencyContactPhone(studentDTO.getEmergencyContactPhone())
                .emergencyContactRelationship(studentDTO.getEmergencyContactRelationship())
                .isActive(true)
                .build();

        // Nota: La asignación a grupos ahora se maneja mediante StudentGroupAssignment
        // No asignamos directamente el grupo aquí - usar StudentGroupAssignmentService para eso

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
                    existingStudent.setEmergencyContactName(studentDTO.getEmergencyContactName());
                    existingStudent.setEmergencyContactPhone(studentDTO.getEmergencyContactPhone());
                    existingStudent.setEmergencyContactRelationship(studentDTO.getEmergencyContactRelationship());

                    // Nota: La gestión de grupos ahora se maneja mediante StudentGroupAssignment
                    // Usar StudentGroupAssignmentService para cambios de grupo

                    Student savedStudent = studentRepository.save(existingStudent);
                    return new StudentDTO(savedStudent);
                });
    }

    public Optional<StudentDTO> updateStudentByPublicId(String publicId, StudentDTO studentDTO) {
        return studentRepository.findByPublicId(publicId)
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
                    existingStudent.setEmergencyContactName(studentDTO.getEmergencyContactName());
                    existingStudent.setEmergencyContactPhone(studentDTO.getEmergencyContactPhone());
                    existingStudent.setEmergencyContactRelationship(studentDTO.getEmergencyContactRelationship());

                    // Nota: La gestión de grupos ahora se maneja mediante StudentGroupAssignment
                    // Usar StudentGroupAssignmentService para cambios de grupo

                    Student savedStudent = studentRepository.save(existingStudent);
                    return new StudentDTO(savedStudent);
                });
    }

    // DEPRECATED: Estos métodos están obsoletos - usar StudentGroupAssignmentService
    @Deprecated
    public boolean assignToGroup(Long studentId, Long groupId) {
        // Este método está obsoleto. Usar StudentGroupAssignmentService.createAssignment()
        throw new UnsupportedOperationException("Usar StudentGroupAssignmentService.createAssignment() en su lugar");
    }

    @Deprecated
    public boolean assignToGroupByPublicId(String publicId, Long groupId) {
        // Este método está obsoleto. Usar StudentGroupAssignmentService.createAssignment()
        throw new UnsupportedOperationException("Usar StudentGroupAssignmentService.createAssignment() en su lugar");
    }

    public void deactivateStudent(Long id) {
        studentRepository.findById(id)
                .ifPresent(student -> {
                    student.setIsActive(false);
                    studentRepository.save(student);
                });
    }

    public void deactivateStudentByPublicId(String publicId) {
        studentRepository.findByPublicId(publicId)
                .ifPresent(student -> {
                    student.setIsActive(false);
                    studentRepository.save(student);
                });
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public void deleteStudentByPublicId(String publicId) {
        studentRepository.findByPublicId(publicId)
                .ifPresent(student -> studentRepository.delete(student));
    }

    @Deprecated
    public List<StudentDTO> getStudentsByGroup(Long groupId) {
        // Este método está obsoleto. Usar StudentGroupAssignmentService.getAssignmentsByGroupCode()
        throw new UnsupportedOperationException("Usar StudentGroupAssignmentService.getAssignmentsByGroupCode() en su lugar");
    }

    public Long getActiveStudentsCount() {
        return studentRepository.countActiveStudents();
    }
}
