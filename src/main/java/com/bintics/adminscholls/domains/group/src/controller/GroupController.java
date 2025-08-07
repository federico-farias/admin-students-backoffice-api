package com.bintics.adminscholls.domains.group.src.controller;

import com.bintics.adminscholls.domains.group.src.dto.GroupDTO;
import com.bintics.adminscholls.domains.group.src.service.GroupService;
import com.bintics.adminscholls.shared.model.AcademicLevel;
import com.bintics.adminscholls.shared.exception.DuplicateGroupException;
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
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<Page<GroupDTO>> getAllGroups(
            @RequestParam(defaultValue = "false") boolean activeOnly,
            @RequestParam(defaultValue = "false") boolean availableOnly,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) AcademicLevel academicLevel,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "false") boolean unpaginated) {

        // Configurar ordenamiento y paginación
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        // Para unpaginated, usar un tamaño muy grande
        int pageSize = unpaginated ? Integer.MAX_VALUE : size;
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        // Determinar el filtro de estado activo
        Boolean activeFilter = isActive != null ? isActive : (activeOnly ? true : null);

        // Usar el método unificado para todos los casos
        Page<GroupDTO> groups = groupService.findGroups(
                academicLevel, grade, name, academicYear, activeFilter, availableOnly, searchText, pageable);

        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id)
                .map(group -> ResponseEntity.ok(group))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        try {
            GroupDTO createdGroup = groupService.createGroup(groupDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
        } catch (DuplicateGroupException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Grupo duplicado", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al crear grupo", "message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(
            @PathVariable Long id,
            @Valid @RequestBody GroupDTO groupDTO) {
        try {
            return groupService.updateGroup(id, groupDTO)
                    .map(group -> ResponseEntity.ok(group))
                    .orElse(ResponseEntity.notFound().build());
        } catch (DuplicateGroupException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Grupo duplicado", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al actualizar grupo", "message", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateGroup(@PathVariable Long id) {
        groupService.deactivateGroup(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats/full-count")
    public ResponseEntity<Long> getFullGroupsCount() {
        Long count = groupService.getFullGroupsCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/average-occupancy")
    public ResponseEntity<Double> getAverageOccupancy() {
        Double occupancy = groupService.getAverageOccupancy();
        return ResponseEntity.ok(occupancy != null ? occupancy : 0.0);
    }
}
