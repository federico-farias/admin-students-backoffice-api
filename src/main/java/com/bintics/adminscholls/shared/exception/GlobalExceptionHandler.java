package com.bintics.adminscholls.shared.exception;

import com.bintics.adminscholls.domains.student.exception.EmergencyContactsNotFoundException;
import com.bintics.adminscholls.domains.student.exception.StudentAlreadyExistsException;
import com.bintics.adminscholls.domains.student.exception.TutorsNotFoundException;
import com.bintics.adminscholls.domains.student.exception.NoTutorsProvidedException;
import com.bintics.adminscholls.domains.student.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleStudentAlreadyExists(StudentAlreadyExistsException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Estudiante ya existe")
                .message(ex.getMessage())
                .path("/api/students")
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EmergencyContactsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmergencyContactsNotFound(EmergencyContactsNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Contactos de emergencia no encontrados")
                .message(ex.getMessage())
                .path("/api/students")
                .details(Map.of("notFoundContactIds", ex.getNotFoundContactIds()))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(TutorsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTutorsNotFound(TutorsNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Tutores no encontrados")
                .message(ex.getMessage())
                .path("/api/students")
                .details(Map.of("notFoundTutorIds", ex.getNotFoundTutorIds()))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoTutorsProvidedException.class)
    public ResponseEntity<ErrorResponse> handleNoTutorsProvided(NoTutorsProvidedException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Tutores requeridos")
                .message(ex.getMessage())
                .path("/api/students")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            assert errorMessage != null;
            errors.put(fieldName, List.of(errorMessage));
        });

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Errores de validación")
                .message("Los datos proporcionados no son válidos")
                .details(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        // Manejar errores específicos de tutores
        if (ex.getMessage().contains("No se encontró el estudiante con publicId:")) {
            ErrorResponse error = ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error("Estudiante no encontrado")
                    .message(ex.getMessage())
                    .path("/api/tutors")
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Error interno del servidor")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFound(StudentNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Estudiante no encontrado")
                .message(ex.getMessage())
                .path("/api/students")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
