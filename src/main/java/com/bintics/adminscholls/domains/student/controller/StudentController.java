package com.bintics.adminscholls.domains.student.controller;

import com.bintics.adminscholls.domains.student.dto.StudentDTO;
import com.bintics.adminscholls.domains.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents(
            @RequestParam(defaultValue = "false") boolean activeOnly) {
        List<StudentDTO> students = activeOnly ?
                studentService.getActiveStudents() :
                studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StudentDTO>> searchStudents(
            @RequestParam String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDTO> students = studentService.searchStudents(search, pageable);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable String id) {
        return studentService.getStudentByPublicId(id)
                .map(student -> ResponseEntity.ok(student))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<StudentDTO>> getStudentsByGroup(@PathVariable Long groupId) {
        List<StudentDTO> students = studentService.getStudentsByGroup(groupId);
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO createdStudent = studentService.createStudent(studentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable String id,
            @Valid @RequestBody StudentDTO studentDTO) {
        return studentService.updateStudentByPublicId(id, studentDTO)
                .map(student -> ResponseEntity.ok(student))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/assign-group")
    public ResponseEntity<Void> assignToGroup(
            @PathVariable String id,
            @RequestParam Long groupId) {
        boolean assigned = studentService.assignToGroupByPublicId(id, groupId);
        return assigned ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateStudent(@PathVariable String id) {
        studentService.deactivateStudentByPublicId(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudentByPublicId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getActiveStudentsCount() {
        Long count = studentService.getActiveStudentsCount();
        return ResponseEntity.ok(count);
    }
}
