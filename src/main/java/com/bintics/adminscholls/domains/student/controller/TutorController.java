package com.bintics.adminscholls.domains.student.controller;

import com.bintics.adminscholls.domains.student.dto.CreateTutorDTO;
import com.bintics.adminscholls.domains.student.dto.TutorDTO;
import com.bintics.adminscholls.domains.student.service.TutorService;
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
@RequestMapping("/api/tutors")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    /**
     * Crea un nuevo tutor y opcionalmente lo relaciona con un estudiante
     * @param createTutorDTO Datos del tutor a crear
     * @return TutorDTO del tutor creado
     */
    @PostMapping
    public ResponseEntity<TutorDTO> createTutor(@Valid @RequestBody CreateTutorDTO createTutorDTO) {
        TutorDTO createdTutor = tutorService.createTutor(createTutorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTutor);
    }

    /**
     * Busca tutores por texto combinando firstName + lastName, phone o email
     * @param search Texto a buscar (nombre completo, teléfono o email)
     * @param pageable Configuración de paginación (page, size, sort)
     * @return Página de tutores que coinciden con la búsqueda
     */
    @GetMapping("/search")
    public ResponseEntity<Page<TutorDTO>> searchTutors(
            @RequestParam(required = false, defaultValue = "") String search,
            @PageableDefault(size = 20, sort = {"firstName", "lastName"}) Pageable pageable) {

        Page<TutorDTO> tutors = tutorService.searchTutors(search, pageable);
        return ResponseEntity.ok(tutors);
    }

    /**
     * Obtiene todos los tutores activos sin paginación
     * @return Lista de todos los tutores activos
     */
    @GetMapping
    public ResponseEntity<List<TutorDTO>> getAllTutors() {
        List<TutorDTO> tutors = tutorService.getAllActiveTutors();
        return ResponseEntity.ok(tutors);
    }

    /**
     * Obtiene un tutor específico por su publicId
     * @param publicId ID público del tutor
     * @return Tutor si existe
     */
    @GetMapping("/{publicId}")
    public ResponseEntity<TutorDTO> getTutorByPublicId(@PathVariable String publicId) {
        return tutorService.getTutorByPublicId(publicId)
                .map(tutor -> ResponseEntity.ok(tutor))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Búsqueda rápida sin paginación (útil para autocompletado)
     * @param search Texto a buscar
     * @return Lista de tutores que coinciden (máximo configurado en el servicio)
     */
    @GetMapping("/quick-search")
    public ResponseEntity<List<TutorDTO>> quickSearchTutors(
            @RequestParam(required = false, defaultValue = "") String search) {

        List<TutorDTO> tutors = tutorService.searchTutorsList(search);
        return ResponseEntity.ok(tutors);
    }
}
