package com.bintics.adminscholls.domains.assignment.service;

import com.bintics.adminscholls.domains.assignment.dto.StudentGroupAssignmentDTO;
import com.bintics.adminscholls.domains.assignment.dto.StudentTransferGroupDTO;
import com.bintics.adminscholls.domains.assignment.model.StudentGroupAssignment;
import com.bintics.adminscholls.domains.assignment.repository.StudentGroupAssignmentRepository;
import com.bintics.adminscholls.shared.model.AcademicLevel;
import com.bintics.adminscholls.shared.model.AssignmentStatus;
import com.bintics.adminscholls.domains.student.model.Student;
import com.bintics.adminscholls.domains.group.model.Group;
import com.bintics.adminscholls.domains.student.repository.StudentRepository;
import com.bintics.adminscholls.domains.group.repository.GroupRepository;
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
public class StudentGroupAssignmentService {

    private final StudentGroupAssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    // Método unificado para búsqueda con filtros y paginación
    public Page<StudentGroupAssignmentDTO> findAssignments(AssignmentStatus status,
                                                          String academicYear,
                                                          String groupCode,
                                                          Boolean isActive,
                                                          String searchText,
                                                          Pageable pageable) {
        return assignmentRepository.findAssignmentsWithFilters(
                status, academicYear, groupCode, isActive, searchText, pageable)
                .map(StudentGroupAssignmentDTO::new);
    }

    public Optional<StudentGroupAssignmentDTO> getAssignmentByPublicId(String publicId) {
        return assignmentRepository.findByPublicId(publicId)
                .map(StudentGroupAssignmentDTO::new);
    }

    public List<StudentGroupAssignmentDTO> getAssignmentsByStudentPublicId(String studentPublicId) {
        return assignmentRepository.findByStudentPublicId(studentPublicId)
                .stream()
                .map(StudentGroupAssignmentDTO::new)
                .toList();
    }

    public List<StudentGroupAssignmentDTO> getAssignmentsByGroupCode(String groupCode) {
        return assignmentRepository.findByGroupCode(groupCode)
                .stream()
                .map(StudentGroupAssignmentDTO::new)
                .toList();
    }

    public Optional<StudentGroupAssignmentDTO> getActiveAssignmentByStudent(String studentPublicId) {
        return assignmentRepository.findActiveAssignmentByStudent(studentPublicId)
                .map(StudentGroupAssignmentDTO::new);
    }

    public StudentGroupAssignmentDTO createAssignment(StudentGroupAssignmentDTO assignmentDTO) {
        // Buscar estudiante por publicId
        Optional<Student> studentOpt = studentRepository.findByPublicId(assignmentDTO.getStudentPublicId());
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Estudiante no encontrado");
        }

        // Buscar grupo por código
        Optional<Group> groupOpt = groupRepository.findByGroupCode(assignmentDTO.getAcademicYear(), assignmentDTO.getAcademicLevel(), assignmentDTO.getGrade(), assignmentDTO.getGroupName());
        if (groupOpt.isEmpty()) {
            throw new RuntimeException("Grupo no encontrado");
        }

        Group group = groupOpt.get();

        // Verificar que el grupo tenga espacios disponibles
        Long currentStudents = assignmentRepository.countActiveStudentsInGroup(group.getGroupCode());
        if (currentStudents >= group.getMaxStudents()) {
            throw new RuntimeException("El grupo no tiene espacios disponibles");
        }

        // Verificar que el estudiante no tenga una asignación activa
        Optional<StudentGroupAssignment> existingActive =
            assignmentRepository.findActiveAssignmentByStudent(assignmentDTO.getStudentPublicId());
        if (existingActive.isPresent()) {
            throw new RuntimeException("El estudiante ya tiene una asignación activa. " +
                "Debe finalizar la asignación actual antes de crear una nueva.");
        }

        // Crear la asignación
        StudentGroupAssignment assignment = StudentGroupAssignment.builder()
                .student(studentOpt.get())
                .group(group)
                .assignmentDate(assignmentDTO.getAssignmentDate() != null ?
                    assignmentDTO.getAssignmentDate() : LocalDate.now())
                .academicYear(assignmentDTO.getAcademicYear())
                .status(AssignmentStatus.ACTIVA)
                .notes(assignmentDTO.getNotes())
                .isActive(true)
                .build();

        StudentGroupAssignment savedAssignment = assignmentRepository.save(assignment);
        return new StudentGroupAssignmentDTO(savedAssignment);
    }

    public Optional<StudentGroupAssignmentDTO> updateAssignmentByPublicId(String publicId,
                                                                         StudentGroupAssignmentDTO assignmentDTO) {
        return assignmentRepository.findByPublicId(publicId)
                .map(existingAssignment -> {
                    // Solo actualizar campos editables
                    existingAssignment.setAssignmentDate(assignmentDTO.getAssignmentDate());
                    existingAssignment.setAcademicYear(assignmentDTO.getAcademicYear());
                    existingAssignment.setNotes(assignmentDTO.getNotes());

                    StudentGroupAssignment savedAssignment = assignmentRepository.save(existingAssignment);
                    return new StudentGroupAssignmentDTO(savedAssignment);
                });
    }

    public boolean transferStudent(StudentTransferGroupDTO studentTransferGroupDTO) {
        // Buscar la asignación activa del estudiante
        Optional<StudentGroupAssignment> currentAssignmentOpt =
            assignmentRepository.findActiveAssignmentByStudent(studentTransferGroupDTO.getStudentPublicId());

        if (currentAssignmentOpt.isEmpty()) {
            throw new RuntimeException("No se encontró una asignación activa para el estudiante");
        }

        StudentGroupAssignment currentAssignment = currentAssignmentOpt.get();

        // Convertir string a enum
        AcademicLevel targetAcademicLevel;
        try {
            targetAcademicLevel = AcademicLevel.valueOf(studentTransferGroupDTO.getAcademicLevel().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Nivel académico inválido: " + studentTransferGroupDTO.getAcademicLevel());
        }

        // Buscar el grupo destino
        Optional<Group> newGroupOpt = groupRepository.findByAcademicYearAndLevelAndGradeAndName(
            studentTransferGroupDTO.getAcademicYear(),
            targetAcademicLevel,
            studentTransferGroupDTO.getGrade(),
            studentTransferGroupDTO.getGroupName()
        );

        if (newGroupOpt.isEmpty()) {
            throw new RuntimeException("No se encontró el grupo destino especificado");
        }

        Group newGroup = newGroupOpt.get();

        // Verificar que no sea el mismo grupo
        if (currentAssignment.getGroup().getId().equals(newGroup.getId())) {
            throw new RuntimeException("El estudiante ya está asignado a este grupo");
        }

        // Verificar espacios disponibles en el nuevo grupo
        Long currentStudentsInNewGroup = assignmentRepository.countActiveStudentsInGroup(newGroup.getGroupCode());
        if (currentStudentsInNewGroup >= newGroup.getMaxStudents()) {
            throw new RuntimeException("El grupo destino no tiene espacios disponibles");
        }

        // Finalizar asignación actual
        currentAssignment.setStatus(AssignmentStatus.TRANSFERIDA);
        assignmentRepository.save(currentAssignment);

        // Crear nueva asignación
        StudentGroupAssignment newAssignment = StudentGroupAssignment.builder()
                .student(currentAssignment.getStudent())
                .group(newGroup)
                .assignmentDate(LocalDate.now())
                .academicYear(studentTransferGroupDTO.getAcademicYear())
                .status(AssignmentStatus.ACTIVA)
                .notes("Transferido desde: " + currentAssignment.getGroup().getGroupCode())
                .isActive(true)
                .build();

        assignmentRepository.save(newAssignment);
        return true;
    }

    public boolean finishAssignment(String publicId) {
        return assignmentRepository.findByPublicId(publicId)
                .map(assignment -> {
                    if (assignment.getStatus() == AssignmentStatus.ACTIVA) {
                        assignment.setStatus(AssignmentStatus.FINALIZADA);
                        assignmentRepository.save(assignment);
                        return true;
                    }
                    return false;
                }).orElse(false);
    }

    public boolean cancelAssignment(String publicId) {
        return assignmentRepository.findByPublicId(publicId)
                .map(assignment -> {
                    assignment.setStatus(AssignmentStatus.CANCELADA);
                    assignment.setIsActive(false);
                    assignmentRepository.save(assignment);
                    return true;
                }).orElse(false);
    }

    public void deleteAssignmentByPublicId(String publicId) {
        assignmentRepository.findByPublicId(publicId)
                .ifPresent(assignment -> assignmentRepository.delete(assignment));
    }

    // Métodos para estadísticas
    public Long getActiveStudentsInGroup(String groupCode) {
        return assignmentRepository.countActiveStudentsInGroup(groupCode);
    }

    public Long getAssignmentsCountByStatus(AssignmentStatus status) {
        return assignmentRepository.countByStatus(status);
    }
}
