package com.bintics.adminscholls.domains.student.service;

import com.bintics.adminscholls.domains.student.dto.CreateTutorDTO;
import com.bintics.adminscholls.domains.student.dto.TutorDTO;
import com.bintics.adminscholls.domains.student.exception.StudentNotFoundException;
import com.bintics.adminscholls.domains.student.model.Tutor;
import com.bintics.adminscholls.domains.student.model.StudentTutor;
import com.bintics.adminscholls.domains.student.repository.TutorRepository;
import com.bintics.adminscholls.domains.student.repository.StudentRepository;
import com.bintics.adminscholls.domains.student.repository.StudentTutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutorService {

    private final TutorRepository tutorRepository;
    private final StudentRepository studentRepository;
    private final StudentTutorRepository studentTutorRepository;

    /**
     * Busca tutores por texto en nombre completo, teléfono o email
     * @param searchText Texto a buscar (puede ser nombre, teléfono o email)
     * @param pageable Configuración de paginación
     * @return Página de tutores que coinciden con la búsqueda
     */
    public Page<TutorDTO> searchTutors(String searchText, Pageable pageable) {
        if (searchText == null || searchText.trim().isEmpty()) {
            // Si no hay texto de búsqueda, devolver todos los tutores activos
            return tutorRepository.findAll(pageable)
                    .map(TutorDTO::new);
        }

        return tutorRepository.findBySearchTerm(searchText.trim(), pageable)
                .map(TutorDTO::new);
    }

    /**
     * Obtiene todos los tutores activos
     * @return Lista de todos los tutores activos
     */
    public List<TutorDTO> getAllActiveTutors() {
        return tutorRepository.findByIsActiveTrue()
                .stream()
                .map(TutorDTO::new)
                .toList();
    }

    /**
     * Obtiene un tutor por su publicId
     * @param publicId ID público del tutor
     * @return Optional con el tutor si existe
     */
    public Optional<TutorDTO> getTutorByPublicId(String publicId) {
        return tutorRepository.findByPublicId(publicId)
                .map(TutorDTO::new);
    }

    /**
     * Busca tutores sin paginación (útil para validaciones rápidas)
     * @param searchText Texto a buscar
     * @return Lista de tutores que coinciden
     */
    public List<TutorDTO> searchTutorsList(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return getAllActiveTutors();
        }

        return tutorRepository.findBySearchTermList(searchText.trim())
                .stream()
                .map(TutorDTO::new)
                .toList();
    }

    /**
     * Crea un nuevo tutor y opcionalmente lo relaciona con un estudiante
     * @param createTutorDTO Datos del tutor a crear
     * @return TutorDTO del tutor creado
     * @throws RuntimeException si el estudiante no existe cuando se proporciona studentPublicId
     */
    @Transactional
    public TutorDTO createTutor(CreateTutorDTO createTutorDTO) {
        // 1. Validar que el estudiante existe si se proporciona studentPublicId
        if (createTutorDTO.getStudentPublicId() != null && !createTutorDTO.getStudentPublicId().trim().isEmpty()) {
            boolean studentExists = studentRepository.findByPublicId(createTutorDTO.getStudentPublicId().trim()).isPresent();
            if (!studentExists) {
                throw new StudentNotFoundException("No se encontró el estudiante con publicId: " + createTutorDTO.getStudentPublicId());
            }
        }

        // 2. Crear el tutor
        Tutor tutor = new Tutor(
                createTutorDTO.getFirstName(),
                createTutorDTO.getLastName(),
                createTutorDTO.getEmail(),
                createTutorDTO.getPhone(),
                createTutorDTO.getAddress(),
                createTutorDTO.getRelationship(),
                createTutorDTO.getOccupation()
        );

        Tutor savedTutor = tutorRepository.save(tutor);

        // 3. Si se proporcionó studentPublicId, crear la relación
        if (createTutorDTO.getStudentPublicId() != null && !createTutorDTO.getStudentPublicId().trim().isEmpty()) {
            StudentTutor studentTutor = new StudentTutor(
                    createTutorDTO.getStudentPublicId().trim(),
                    savedTutor.getPublicId(),
                    false // No es tutor primario por defecto
            );
            studentTutorRepository.save(studentTutor);
        }

        return new TutorDTO(savedTutor);
    }
}
