package com.bintics.adminscholls.domains.student.service;

import com.bintics.adminscholls.domains.student.dto.CreateEmergencyContactDTO;
import com.bintics.adminscholls.domains.student.dto.EmergencyContactDTO;
import com.bintics.adminscholls.domains.student.model.EmergencyContact;
import com.bintics.adminscholls.domains.student.model.Student;
import com.bintics.adminscholls.domains.student.model.StudentEmergencyContact;
import com.bintics.adminscholls.domains.student.repository.EmergencyContactRepository;
import com.bintics.adminscholls.domains.student.repository.StudentEmergencyContactRepository;
import com.bintics.adminscholls.domains.student.repository.StudentRepository;
import com.bintics.adminscholls.domains.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmergencyContactService {

    private final EmergencyContactRepository emergencyContactRepository;
    private final StudentRepository studentRepository;
    private final StudentEmergencyContactRepository studentEmergencyContactRepository;

    /**
     * Busca contactos de emergencia por texto en nombre completo, teléfono o email
     * @param searchText Texto a buscar (puede ser nombre, teléfono o email)
     * @param pageable Configuración de paginación
     * @return Página de contactos de emergencia que coinciden con la búsqueda
     */
    public Page<EmergencyContactDTO> searchEmergencyContacts(String searchText, Pageable pageable) {
        if (searchText == null || searchText.trim().isEmpty()) {
            // Si no hay texto de búsqueda, devolver todos los contactos activos
            return emergencyContactRepository.findAll(pageable)
                    .map(EmergencyContactDTO::new);
        }

        return emergencyContactRepository.findBySearchTerm(searchText.trim(), pageable)
                .map(EmergencyContactDTO::new);
    }

    /**
     * Obtiene todos los contactos de emergencia activos
     * @return Lista de todos los contactos activos
     */
    public List<EmergencyContactDTO> getAllActiveEmergencyContacts() {
        return emergencyContactRepository.findByIsActiveTrue()
                .stream()
                .map(EmergencyContactDTO::new)
                .toList();
    }

    /**
     * Obtiene un contacto de emergencia por su publicId
     * @param publicId ID público del contacto
     * @return Optional con el contacto si existe
     */
    public Optional<EmergencyContactDTO> getEmergencyContactByPublicId(String publicId) {
        return emergencyContactRepository.findByPublicId(publicId)
                .map(EmergencyContactDTO::new);
    }

    /**
     * Busca contactos sin paginación (útil para validaciones rápidas)
     * @param searchText Texto a buscar
     * @return Lista de contactos que coinciden
     */
    public List<EmergencyContactDTO> searchEmergencyContactsList(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return getAllActiveEmergencyContacts();
        }

        return emergencyContactRepository.findBySearchTermList(searchText.trim())
                .stream()
                .map(EmergencyContactDTO::new)
                .toList();
    }

    /**
     * Crea un contacto de emergencia y lo asocia a un estudiante si se recibe el publicId del estudiante
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public EmergencyContactDTO createEmergencyContact(CreateEmergencyContactDTO dto) {
        EmergencyContact emergencyContact = new EmergencyContact(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getRelationship() != null ? dto.getRelationship() : "Otro", // Default relationship
                dto.getPhone(),
                dto.getEmail(),
                dto.getAddress() != null ? dto.getAddress() : null // Puede ser nulo
        );

        this.emergencyContactRepository.save(emergencyContact);

        if (dto.getStudentPublicId() != null && !dto.getStudentPublicId().isBlank()) {
            var student = this.studentRepository.findByPublicId(dto.getStudentPublicId()).orElseThrow(() -> new StudentNotFoundException("No se encontró el estudiante con publicId: " + dto.getStudentPublicId()));
            StudentEmergencyContact studentEmergencyContact = new StudentEmergencyContact(student.getPublicId(), emergencyContact.getPublicId());
            this.studentEmergencyContactRepository.save(studentEmergencyContact);
        }

        return new EmergencyContactDTO(emergencyContact);
    }
}
