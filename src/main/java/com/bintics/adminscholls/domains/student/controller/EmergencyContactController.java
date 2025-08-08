package com.bintics.adminscholls.domains.student.controller;

import com.bintics.adminscholls.domains.student.dto.EmergencyContactDTO;
import com.bintics.adminscholls.domains.student.service.EmergencyContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emergency-contacts")
@RequiredArgsConstructor
public class EmergencyContactController {

    private final EmergencyContactService emergencyContactService;

    /**
     * Busca contactos de emergencia por texto combinando firstName + lastName, phone o email
     * @param search Texto a buscar (nombre completo, teléfono o email)
     * @param pageable Configuración de paginación (page, size, sort)
     * @return Página de contactos de emergencia que coinciden con la búsqueda
     */
    @GetMapping("/search")
    public ResponseEntity<Page<EmergencyContactDTO>> searchEmergencyContacts(
            @RequestParam(required = false, defaultValue = "") String search,
            @PageableDefault(size = 20, sort = {"firstName", "lastName"}) Pageable pageable) {

        Page<EmergencyContactDTO> contacts = emergencyContactService.searchEmergencyContacts(search, pageable);
        return ResponseEntity.ok(contacts);
    }

    /**
     * Obtiene todos los contactos de emergencia activos sin paginación
     * @return Lista de todos los contactos activos
     */
    @GetMapping
    public ResponseEntity<List<EmergencyContactDTO>> getAllEmergencyContacts() {
        List<EmergencyContactDTO> contacts = emergencyContactService.getAllActiveEmergencyContacts();
        return ResponseEntity.ok(contacts);
    }

    /**
     * Obtiene un contacto de emergencia específico por su publicId
     * @param publicId ID público del contacto
     * @return Contacto de emergencia si existe
     */
    @GetMapping("/{publicId}")
    public ResponseEntity<EmergencyContactDTO> getEmergencyContactByPublicId(@PathVariable String publicId) {
        return emergencyContactService.getEmergencyContactByPublicId(publicId)
                .map(contact -> ResponseEntity.ok(contact))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Búsqueda rápida sin paginación (útil para autocompletado)
     * @param search Texto a buscar
     * @return Lista de contactos que coinciden (máximo configurado en el servicio)
     */
    @GetMapping("/quick-search")
    public ResponseEntity<List<EmergencyContactDTO>> quickSearchEmergencyContacts(
            @RequestParam(required = false, defaultValue = "") String search) {

        List<EmergencyContactDTO> contacts = emergencyContactService.searchEmergencyContactsList(search);
        return ResponseEntity.ok(contacts);
    }
}
