package com.bintics.adminscholls.controller;

import com.bintics.adminscholls.dto.GroupDTO;
import com.bintics.adminscholls.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups(
            @RequestParam(defaultValue = "false") boolean activeOnly,
            @RequestParam(defaultValue = "false") boolean availableOnly) {
        List<GroupDTO> groups;

        if (availableOnly) {
            groups = groupService.getAvailableGroups();
        } else if (activeOnly) {
            groups = groupService.getActiveGroups();
        } else {
            groups = groupService.getAllGroups();
        }

        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id)
                .map(group -> ResponseEntity.ok(group))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        try {
            GroupDTO createdGroup = groupService.createGroup(groupDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(
            @PathVariable Long id,
            @Valid @RequestBody GroupDTO groupDTO) {
        return groupService.updateGroup(id, groupDTO)
                .map(group -> ResponseEntity.ok(group))
                .orElse(ResponseEntity.notFound().build());
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
