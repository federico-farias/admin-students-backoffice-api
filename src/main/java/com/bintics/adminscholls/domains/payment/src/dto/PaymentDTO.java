package com.bintics.adminscholls.domains.payment.src.dto;

import com.bintics.adminscholls.domains.payment.src.model.Payment;
import com.bintics.adminscholls.shared.model.PaymentMethod;
import com.bintics.adminscholls.shared.model.PaymentStatus;
import com.bintics.adminscholls.shared.model.PeriodType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    private Long id;

    @NotNull(message = "El estudiante es obligatorio")
    private Long studentId;

    private String studentName;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal amount;

    private LocalDate paymentDate;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500)
    private String description;

    @NotNull(message = "El método de pago es obligatorio")
    private PaymentMethod paymentMethod;

    @NotNull(message = "El estado es obligatorio")
    private PaymentStatus status;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate dueDate;

    @NotBlank(message = "El período es obligatorio")
    @Size(max = 100)
    private String period;

    private PeriodType periodType;

    private Long breakfastPackageId;
    private String breakfastPackageName;

    @Size(max = 1000)
    private String notes;

    // Constructor para convertir desde entidad
    public PaymentDTO(Payment payment) {
        this.id = payment.getId();
        this.studentId = payment.getStudent().getId();
        this.studentName = payment.getStudent().getFullName();
        this.amount = payment.getAmount();
        this.paymentDate = payment.getPaymentDate();
        this.description = payment.getDescription();
        this.paymentMethod = payment.getPaymentMethod();
        this.status = payment.getStatus();
        this.dueDate = payment.getDueDate();
        this.period = payment.getPeriod();
        this.periodType = payment.getPeriodType();
        this.notes = payment.getNotes();

        if (payment.getBreakfastPackage() != null) {
            this.breakfastPackageId = payment.getBreakfastPackage().getId();
            this.breakfastPackageName = payment.getBreakfastPackage().getName();
        }
    }

    // Utility methods
    public boolean isPaid() {
        return status == PaymentStatus.PAGADO;
    }

    public boolean isPending() {
        return status == PaymentStatus.PENDIENTE;
    }

    public boolean isOverdue() {
        return status == PaymentStatus.VENCIDO ||
               (status == PaymentStatus.PENDIENTE && dueDate.isBefore(LocalDate.now()));
    }

    public String getStatusDisplayName() {
        return switch (status) {
            case PAGADO -> "Pagado";
            case PENDIENTE -> "Pendiente";
            case VENCIDO -> "Vencido";
        };
    }
}
