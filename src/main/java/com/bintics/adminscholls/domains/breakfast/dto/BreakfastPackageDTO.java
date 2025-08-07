package com.bintics.adminscholls.domains.breakfast.dto;

import com.bintics.adminscholls.domains.breakfast.model.BreakfastPackage;
import com.bintics.adminscholls.shared.model.PeriodType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BreakfastPackageDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull(message = "El precio por día es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio por día debe ser mayor a 0")
    private BigDecimal pricePerDay;

    @NotNull(message = "El precio por semana es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio por semana debe ser mayor a 0")
    private BigDecimal pricePerWeek;

    @NotNull(message = "El precio por mes es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio por mes debe ser mayor a 0")
    private BigDecimal pricePerMonth;

    private Boolean isActive;
    private Integer paymentsCount;

    // Constructor para convertir desde entidad
    public BreakfastPackageDTO(BreakfastPackage breakfastPackage) {
        this.id = breakfastPackage.getId();
        this.name = breakfastPackage.getName();
        this.description = breakfastPackage.getDescription();
        this.pricePerDay = breakfastPackage.getPricePerDay();
        this.pricePerWeek = breakfastPackage.getPricePerWeek();
        this.pricePerMonth = breakfastPackage.getPricePerMonth();
        this.isActive = breakfastPackage.getIsActive();
        this.paymentsCount = breakfastPackage.getPaymentsCount();
    }

    // Utility methods
    public BigDecimal getPriceByPeriodType(PeriodType periodType) {
        return switch (periodType) {
            case DIARIO -> pricePerDay;
            case SEMANAL -> pricePerWeek;
            case MENSUAL -> pricePerMonth;
        };
    }

    public String getDisplayName() {
        return name + (description != null && !description.trim().isEmpty() ?
               " - " + description : "");
    }
}
