package com.bintics.adminscholls.domains.student.service;

import com.bintics.adminscholls.domains.student.dto.EmergencyContactDTO;
import com.bintics.adminscholls.domains.student.repository.EmergencyContactRepository;
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
public class EmergencyContactService {

    private final EmergencyContactRepository emergencyContactRepository;

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
}
