package com.bintics.adminscholls.controller;

import com.bintics.adminscholls.dto.BreakfastPackageDTO;
import com.bintics.adminscholls.service.BreakfastPackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/breakfast-packages")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class BreakfastPackageController {
    
    private final BreakfastPackageService breakfastPackageService;
    
    @GetMapping
    public ResponseEntity<List<BreakfastPackageDTO>> getAllPackages(
            @RequestParam(defaultValue = "false") boolean activeOnly) {
        List<BreakfastPackageDTO> packages = activeOnly ? 
                breakfastPackageService.getActivePackages() : 
                breakfastPackageService.getAllPackages();
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BreakfastPackageDTO> getPackageById(@PathVariable Long id) {
        return breakfastPackageService.getPackageById(id)
                .map(packageDTO -> ResponseEntity.ok(packageDTO))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<BreakfastPackageDTO> createPackage(@Valid @RequestBody BreakfastPackageDTO packageDTO) {
        try {
            BreakfastPackageDTO createdPackage = breakfastPackageService.createPackage(packageDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPackage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BreakfastPackageDTO> updatePackage(
            @PathVariable Long id, 
            @Valid @RequestBody BreakfastPackageDTO packageDTO) {
        return breakfastPackageService.updatePackage(id, packageDTO)
                .map(packageDto -> ResponseEntity.ok(packageDto))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivatePackage(@PathVariable Long id) {
        breakfastPackageService.deactivatePackage(id);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        breakfastPackageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }
}
