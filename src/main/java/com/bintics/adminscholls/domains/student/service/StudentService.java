package com.bintics.adminscholls.domains.student.service;

import com.bintics.adminscholls.domains.student.repository.EmergencyContactRepository;
import com.bintics.adminscholls.domains.student.repository.TutorRepository;
import com.bintics.adminscholls.domains.student.repository.StudentTutorRepository;
import com.bintics.adminscholls.domains.student.dto.StudentDTO;
import com.bintics.adminscholls.domains.student.exception.EmergencyContactsNotFoundException;
import com.bintics.adminscholls.domains.student.exception.StudentAlreadyExistsException;
import com.bintics.adminscholls.domains.student.exception.TutorsNotFoundException;
import com.bintics.adminscholls.domains.student.exception.NoTutorsProvidedException;
import com.bintics.adminscholls.domains.student.model.Student;
import com.bintics.adminscholls.domains.student.model.StudentEmergencyContact;
import com.bintics.adminscholls.domains.student.model.StudentTutor;
import com.bintics.adminscholls.domains.student.repository.StudentEmergencyContactRepository;
import com.bintics.adminscholls.domains.student.repository.StudentRepository;
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
    private final EmergencyContactRepository emergencyContactRepository;
    private final StudentEmergencyContactRepository studentEmergencyContactRepository;
    private final TutorRepository tutorRepository;
    private final StudentTutorRepository studentTutorRepository;

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
        // 1. Validar si el estudiante ya existe
        validateStudentDoesNotExist(studentDTO);

        // 2. Validar que todos los contactos de emergencia existan
        validateEmergencyContactsExist(studentDTO.getEmergencyContactIds());

        // 3. Validar que todos los tutores existan
        validateTutorsExist(studentDTO.getTutorIds());

        // 4. Crear el estudiante
        Student student = new Student(
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getEmail(),
                studentDTO.getPhone(),
                studentDTO.getDateOfBirth(),
                studentDTO.getAddress()
        );

        Student savedStudent = studentRepository.save(student);

        // 5. Asociar los contactos de emergencia
        associateEmergencyContacts(savedStudent.getPublicId(), studentDTO.getEmergencyContactIds());

        // 6. Asociar los tutores
        associateTutors(savedStudent.getPublicId(), studentDTO.getTutorIds());

        return new StudentDTO(savedStudent);
    }

    private void validateStudentDoesNotExist(StudentDTO studentDTO) {
        // Validar por email si está presente
        if (studentDTO.getEmail() != null && !studentDTO.getEmail().trim().isEmpty()) {
            if (studentRepository.existsByEmailAndIsActiveTrue(studentDTO.getEmail())) {
                throw new StudentAlreadyExistsException(
                        "Ya existe un estudiante activo con el email: " + studentDTO.getEmail());
            }
        }

        // Validar por combinación de nombre, apellido y fecha de nacimiento
        if (studentRepository.existsByFirstNameAndLastNameAndDateOfBirth(
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getDateOfBirth())) {
            throw new StudentAlreadyExistsException(
                    String.format("Ya existe un estudiante activo con el nombre '%s %s' y fecha de nacimiento %s",
                            studentDTO.getFirstName(),
                            studentDTO.getLastName(),
                            studentDTO.getDateOfBirth()));
        }
    }

    private void validateEmergencyContactsExist(List<String> emergencyContactIds) {
        if (emergencyContactIds == null || emergencyContactIds.isEmpty()) {
            throw new EmergencyContactsNotFoundException(List.of());
        }

        List<String> existingContactIds = emergencyContactRepository.findExistingPublicIds(emergencyContactIds);

        List<String> notFoundContactIds = emergencyContactIds.stream()
                .filter(id -> !existingContactIds.contains(id))
                .toList();

        if (!notFoundContactIds.isEmpty()) {
            throw new EmergencyContactsNotFoundException(notFoundContactIds);
        }
    }

    private void associateEmergencyContacts(String studentPublicId, List<String> emergencyContactIds) {
        for (String contactId : emergencyContactIds) {
            StudentEmergencyContact association = new StudentEmergencyContact(studentPublicId, contactId);
            studentEmergencyContactRepository.save(association);
        }
    }

    private void validateTutorsExist(List<String> tutorIds) {
        if (tutorIds == null || tutorIds.isEmpty()) {
            throw new NoTutorsProvidedException();
        }

        List<String> existingTutorIds = tutorRepository.findExistingPublicIds(tutorIds);

        List<String> notFoundTutorIds = tutorIds.stream()
                .filter(id -> !existingTutorIds.contains(id))
                .toList();

        if (!notFoundTutorIds.isEmpty()) {
            throw new TutorsNotFoundException(notFoundTutorIds);
        }
    }

    private void associateTutors(String studentPublicId, List<String> tutorIds) {
        for (String tutorId : tutorIds) {
            StudentTutor association = new StudentTutor(studentPublicId, tutorId, false);
            studentTutorRepository.save(association);
        }
    }

    public StudentDTO updateStudent(String publicId, StudentDTO studentDTO) {
        Student student = studentRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Actualizar campos básicos
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setPhone(studentDTO.getPhone());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setAddress(studentDTO.getAddress());

        Student updatedStudent = studentRepository.save(student);
        return new StudentDTO(updatedStudent);
    }

    public void deleteStudent(String publicId) {
        Student student = studentRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        student.setIsActive(false);
        studentRepository.save(student);
    }
}
