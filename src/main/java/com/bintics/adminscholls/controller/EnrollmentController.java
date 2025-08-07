package com.bintics.adminscholls.controller;

import com.bintics.adminscholls.dto.EnrollmentDTO;
import com.bintics.adminscholls.model.EnrollmentStatus;
import com.bintics.adminscholls.service.EnrollmentService;
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

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<Page<EnrollmentDTO>> getAllEnrollments(
            @RequestParam(required = false) EnrollmentStatus status,
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "enrollmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EnrollmentDTO> enrollments = enrollmentService.findEnrollments(
                status, academicYear, groupId, isActive, searchText, pageable);

        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable String id) {
        return enrollmentService.getEnrollmentByPublicId(id)
                .map(enrollment -> ResponseEntity.ok(enrollment))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentPublicId}")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStudent(@PathVariable String studentPublicId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByStudentPublicId(studentPublicId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByGroup(@PathVariable Long groupId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByGroupId(groupId);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        try {
            EnrollmentDTO createdEnrollment = enrollmentService.createEnrollment(enrollmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEnrollment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> updateEnrollment(
            @PathVariable String id,
            @Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        return enrollmentService.updateEnrollmentByPublicId(id, enrollmentDTO)
                .map(enrollment -> ResponseEntity.ok(enrollment))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmEnrollment(@PathVariable String id) {
        boolean confirmed = enrollmentService.confirmEnrollment(id);
        return confirmed ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeEnrollment(@PathVariable String id) {
        boolean completed = enrollmentService.completeEnrollment(id);
        return completed ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelEnrollment(@PathVariable String id) {
        boolean cancelled = enrollmentService.cancelEnrollment(id);
        return cancelled ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable String id) {
        enrollmentService.deleteEnrollmentByPublicId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats/count-by-status")
    public ResponseEntity<Long> getEnrollmentsCountByStatus(@RequestParam EnrollmentStatus status) {
        Long count = enrollmentService.getEnrollmentsCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/count-by-year")
    public ResponseEntity<Long> getEnrollmentsCountByYear(@RequestParam String academicYear) {
        Long count = enrollmentService.getEnrollmentsCountByAcademicYear(academicYear);
        return ResponseEntity.ok(count);
    }
}
