package com.bintics.adminscholls.domains.enrollment.src.service;

import com.bintics.adminscholls.domains.enrollment.src.dto.EnrollmentDTO;
import com.bintics.adminscholls.domains.enrollment.src.model.Enrollment;
import com.bintics.adminscholls.domains.enrollment.src.repository.EnrollmentRepository;
import com.bintics.adminscholls.shared.model.EnrollmentStatus;
import com.bintics.adminscholls.domains.student.src.model.Student;
import com.bintics.adminscholls.domains.student.src.repository.StudentRepository;
import com.bintics.adminscholls.domains.group.src.model.Group;
import com.bintics.adminscholls.domains.group.src.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    // Método unificado para búsqueda con filtros y paginación
    public Page<EnrollmentDTO> findEnrollments(EnrollmentStatus status,
                                             String academicYear,
                                             Long groupId,
                                             Boolean isActive,
                                             String searchText,
                                             Pageable pageable) {
        return enrollmentRepository.findEnrollmentsWithFilters(
                status, academicYear, groupId, isActive, searchText, pageable)
                .map(EnrollmentDTO::new);
    }

    public Optional<EnrollmentDTO> getEnrollmentByPublicId(String publicId) {
        return enrollmentRepository.findByPublicId(publicId)
                .map(EnrollmentDTO::new);
    }

    public List<EnrollmentDTO> getEnrollmentsByStudentPublicId(String studentPublicId) {
        return enrollmentRepository.findByStudentPublicId(studentPublicId)
                .stream()
                .map(EnrollmentDTO::new)
                .toList();
    }

    public List<EnrollmentDTO> getEnrollmentsByGroupId(Long groupId) {
        return enrollmentRepository.findByGroupId(groupId)
                .stream()
                .map(EnrollmentDTO::new)
                .toList();
    }

    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        // Buscar estudiante por publicId
        Optional<Student> studentOpt = studentRepository.findByPublicId(enrollmentDTO.getStudentPublicId());
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Estudiante no encontrado");
        }

        // Buscar grupo por id
        Optional<Group> groupOpt = groupRepository.findById(enrollmentDTO.getGroupId());
        if (groupOpt.isEmpty()) {
            throw new RuntimeException("Grupo no encontrado");
        }

        Group group = groupOpt.get();

        // Crear la inscripción
        Enrollment enrollment = Enrollment.builder()
                .student(studentOpt.get())
                .group(group)
                .enrollmentDate(enrollmentDTO.getEnrollmentDate() != null ?
                    enrollmentDTO.getEnrollmentDate() : LocalDate.now())
                .academicYear(enrollmentDTO.getAcademicYear())
                .enrollmentFee(enrollmentDTO.getEnrollmentFee())
                .status(EnrollmentStatus.PENDIENTE)
                .notes(enrollmentDTO.getNotes())
                .isActive(true)
                .build();

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return new EnrollmentDTO(savedEnrollment);
    }

    public Optional<EnrollmentDTO> updateEnrollmentByPublicId(String publicId, EnrollmentDTO enrollmentDTO) {
        return enrollmentRepository.findByPublicId(publicId)
                .map(existingEnrollment -> {
                    // Actualizar campos editables
                    existingEnrollment.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
                    existingEnrollment.setAcademicYear(enrollmentDTO.getAcademicYear());
                    existingEnrollment.setEnrollmentFee(enrollmentDTO.getEnrollmentFee());
                    existingEnrollment.setNotes(enrollmentDTO.getNotes());

                    Enrollment savedEnrollment = enrollmentRepository.save(existingEnrollment);
                    return new EnrollmentDTO(savedEnrollment);
                });
    }

    public boolean confirmEnrollment(String publicId) {
        return enrollmentRepository.findByPublicId(publicId)
                .map(enrollment -> {
                    if (enrollment.getStatus() == EnrollmentStatus.PENDIENTE) {
                        enrollment.setStatus(EnrollmentStatus.CONFIRMADA);
                        enrollmentRepository.save(enrollment);
                        return true;
                    }
                    return false;
                }).orElse(false);
    }

    public boolean completeEnrollment(String publicId) {
        return enrollmentRepository.findByPublicId(publicId)
                .map(enrollment -> {
                    if (enrollment.getStatus() == EnrollmentStatus.CONFIRMADA) {
                        enrollment.setStatus(EnrollmentStatus.COMPLETADA);
                        enrollmentRepository.save(enrollment);
                        return true;
                    }
                    return false;
                }).orElse(false);
    }

    public boolean cancelEnrollment(String publicId) {
        return enrollmentRepository.findByPublicId(publicId)
                .map(enrollment -> {
                    enrollment.setStatus(EnrollmentStatus.CANCELADA);
                    enrollment.setIsActive(false);
                    enrollmentRepository.save(enrollment);
                    return true;
                }).orElse(false);
    }

    public void deleteEnrollmentByPublicId(String publicId) {
        enrollmentRepository.findByPublicId(publicId)
                .ifPresent(enrollment -> enrollmentRepository.delete(enrollment));
    }

    // Métodos para estadísticas
    public Long getEnrollmentsCountByStatus(EnrollmentStatus status) {
        return enrollmentRepository.countByStatus(status);
    }

    public Long getEnrollmentsCountByAcademicYear(String academicYear) {
        return enrollmentRepository.countByAcademicYear(academicYear);
    }
}
