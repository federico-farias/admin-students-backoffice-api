package com.bintics.adminscholls.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "breakfast_packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "payments") // Evitar referencia circular en toString
public class BreakfastPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 500)
    @Column(name = "description")
    private String description;

    @NotNull(message = "El precio por día es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio por día debe ser mayor a 0")
    @Column(name = "price_per_day", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerDay;

    @NotNull(message = "El precio por semana es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio por semana debe ser mayor a 0")
    @Column(name = "price_per_week", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerWeek;

    @NotNull(message = "El precio por mes es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio por mes debe ser mayor a 0")
    @Column(name = "price_per_month", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerMonth;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Builder.Default
    @OneToMany(mappedBy = "breakfastPackage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor personalizado para casos comunes
    public BreakfastPackage(String name, String description, BigDecimal pricePerDay,
                           BigDecimal pricePerWeek, BigDecimal pricePerMonth) {
        this.name = name;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.pricePerWeek = pricePerWeek;
        this.pricePerMonth = pricePerMonth;
        this.isActive = true;
        this.payments = new ArrayList<>();
    }

    // Utility methods
    public BigDecimal getPriceByPeriodType(PeriodType periodType) {
        return switch (periodType) {
            case DIARIO -> pricePerDay;
            case SEMANAL -> pricePerWeek;
            case MENSUAL -> pricePerMonth;
        };
    }

    public int getPaymentsCount() {
        return payments != null ? payments.size() : 0;
    }
}
