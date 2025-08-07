package com.bintics.adminscholls.domains.assignment.src.controller;

import com.bintics.adminscholls.domains.assignment.src.dto.StudentGroupAssignmentDTO;
import com.bintics.adminscholls.domains.assignment.src.dto.StudentTransferGroupDTO;
import com.bintics.adminscholls.domains.assignment.src.service.StudentGroupAssignmentService;
import com.bintics.adminscholls.shared.model.AssignmentStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student-group-assignments")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class StudentGroupAssignmentController {

    private final StudentGroupAssignmentService assignmentService;

    @GetMapping
    public ResponseEntity<Page<StudentGroupAssignmentDTO>> getAllAssignments(
            @RequestParam(required = false) AssignmentStatus status,
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assignmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<StudentGroupAssignmentDTO> assignments = assignmentService.findAssignments(
                status, academicYear, groupCode, isActive, searchText, pageable);

        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentGroupAssignmentDTO> getAssignmentById(@PathVariable String id) {
        return assignmentService.getAssignmentByPublicId(id)
                .map(assignment -> ResponseEntity.ok(assignment))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentPublicId}")
    public ResponseEntity<List<StudentGroupAssignmentDTO>> getAssignmentsByStudent(@PathVariable String studentPublicId) {
        List<StudentGroupAssignmentDTO> assignments = assignmentService.getAssignmentsByStudentPublicId(studentPublicId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/student/{studentPublicId}/active")
    public ResponseEntity<StudentGroupAssignmentDTO> getActiveAssignmentByStudent(@PathVariable String studentPublicId) {
        return assignmentService.getActiveAssignmentByStudent(studentPublicId)
                .map(assignment -> ResponseEntity.ok(assignment))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/group/{groupCode}")
    public ResponseEntity<List<StudentGroupAssignmentDTO>> getAssignmentsByGroup(@PathVariable String groupCode) {
        List<StudentGroupAssignmentDTO> assignments = assignmentService.getAssignmentsByGroupCode(groupCode);
        return ResponseEntity.ok(assignments);
    }

    @PostMapping
    public ResponseEntity<?> createAssignment(@Valid @RequestBody StudentGroupAssignmentDTO assignmentDTO) {
        try {
            StudentGroupAssignmentDTO createdAssignment = assignmentService.createAssignment(assignmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al crear asignación", "message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssignment(
            @PathVariable String id,
            @Valid @RequestBody StudentGroupAssignmentDTO assignmentDTO) {
        try {
            return assignmentService.updateAssignmentByPublicId(id, assignmentDTO)
                    .map(assignment -> ResponseEntity.ok(assignment))
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al actualizar asignación", "message", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/transfer")
    public ResponseEntity<?> transferStudent(
            @PathVariable String id,
            @RequestBody StudentTransferGroupDTO transferGroupDTO) {
        try {
            boolean transferred = assignmentService.transferStudent(transferGroupDTO);
            return transferred ?
                    ResponseEntity.ok(Map.of("message", "Estudiante transferido exitosamente")) :
                    ResponseEntity.badRequest().body(Map.of("error", "No se pudo transferir el estudiante"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al transferir estudiante", "message", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/finish")
    public ResponseEntity<?> finishAssignment(@PathVariable String id) {
        boolean finished = assignmentService.finishAssignment(id);
        return finished ?
                ResponseEntity.ok(Map.of("message", "Asignación finalizada exitosamente")) :
                ResponseEntity.badRequest().body(Map.of("error", "No se pudo finalizar la asignación"));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAssignment(@PathVariable String id) {
        boolean cancelled = assignmentService.cancelAssignment(id);
        return cancelled ?
                ResponseEntity.ok(Map.of("message", "Asignación cancelada exitosamente")) :
                ResponseEntity.badRequest().body(Map.of("error", "No se pudo cancelar la asignación"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable String id) {
        assignmentService.deleteAssignmentByPublicId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats/group/{groupCode}/count")
    public ResponseEntity<Long> getActiveStudentsInGroup(@PathVariable String groupCode) {
        Long count = assignmentService.getActiveStudentsInGroup(groupCode);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/count-by-status")
    public ResponseEntity<Long> getAssignmentsCountByStatus(@RequestParam AssignmentStatus status) {
        Long count = assignmentService.getAssignmentsCountByStatus(status);
        return ResponseEntity.ok(count);
    }
}
