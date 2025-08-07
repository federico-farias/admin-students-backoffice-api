package com.bintics.adminscholls.domains.student.controller;

import com.bintics.adminscholls.domains.student.dto.StudentDTO;
import com.bintics.adminscholls.domains.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getActiveStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StudentDTO>> searchStudents(
            @RequestParam(required = false, defaultValue = "") String search,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<StudentDTO> students = studentService.searchStudents(search, pageable);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<StudentDTO> getStudentByPublicId(@PathVariable String publicId) {
        return studentService.getStudentByPublicId(publicId)
                .map(student -> ResponseEntity.ok(student))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/{publicId}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable String publicId,
            @Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(publicId, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String publicId) {
        studentService.deleteStudent(publicId);
        return ResponseEntity.noContent().build();
    }
}
