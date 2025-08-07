package com.bintics.adminscholls.domains.breakfast.src.service;

import com.bintics.adminscholls.domains.breakfast.src.dto.BreakfastPackageDTO;
import com.bintics.adminscholls.domains.breakfast.src.model.BreakfastPackage;
import com.bintics.adminscholls.domains.breakfast.src.repository.BreakfastPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BreakfastPackageService {

    private final BreakfastPackageRepository breakfastPackageRepository;

    public List<BreakfastPackageDTO> getAllPackages() {
        return breakfastPackageRepository.findAll()
                .stream()
                .map(BreakfastPackageDTO::new)
                .toList();
    }

    public List<BreakfastPackageDTO> getActivePackages() {
        return breakfastPackageRepository.findByIsActiveTrueOrderByNameAsc()
                .stream()
                .map(BreakfastPackageDTO::new)
                .toList();
    }

    public Optional<BreakfastPackageDTO> getPackageById(Long id) {
        return breakfastPackageRepository.findById(id)
                .map(BreakfastPackageDTO::new);
    }

    public BreakfastPackageDTO createPackage(BreakfastPackageDTO packageDTO) {
        BreakfastPackage breakfastPackage = BreakfastPackage.builder()
                .name(packageDTO.getName())
                .description(packageDTO.getDescription())
                .pricePerDay(packageDTO.getPricePerDay())
                .pricePerWeek(packageDTO.getPricePerWeek())
                .pricePerMonth(packageDTO.getPricePerMonth())
                .isActive(true)
                .build();

        BreakfastPackage savedPackage = breakfastPackageRepository.save(breakfastPackage);
        return new BreakfastPackageDTO(savedPackage);
    }

    public Optional<BreakfastPackageDTO> updatePackage(Long id, BreakfastPackageDTO packageDTO) {
        return breakfastPackageRepository.findById(id)
                .map(existingPackage -> {
                    existingPackage.setName(packageDTO.getName());
                    existingPackage.setDescription(packageDTO.getDescription());
                    existingPackage.setPricePerDay(packageDTO.getPricePerDay());
                    existingPackage.setPricePerWeek(packageDTO.getPricePerWeek());
                    existingPackage.setPricePerMonth(packageDTO.getPricePerMonth());

                    BreakfastPackage savedPackage = breakfastPackageRepository.save(existingPackage);
                    return new BreakfastPackageDTO(savedPackage);
                });
    }

    public void deactivatePackage(Long id) {
        breakfastPackageRepository.findById(id)
                .ifPresent(breakfastPackage -> {
                    breakfastPackage.setIsActive(false);
                    breakfastPackageRepository.save(breakfastPackage);
                });
    }

    public void deletePackage(Long id) {
        breakfastPackageRepository.deleteById(id);
    }
}
