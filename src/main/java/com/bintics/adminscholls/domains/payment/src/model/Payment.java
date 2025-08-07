package com.bintics.adminscholls.domains.payment.src.model;

import com.bintics.adminscholls.shared.model.BaseEntity;
import com.bintics.adminscholls.shared.model.PaymentMethod;
import com.bintics.adminscholls.shared.model.PaymentStatus;
import com.bintics.adminscholls.shared.model.PeriodType;
import com.bintics.adminscholls.domains.student.src.model.Student;
import com.bintics.adminscholls.domains.breakfast.src.model.BreakfastPackage;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"student", "breakfastPackage"}) // Evitar referencia circular en toString
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @NotNull(message = "El estudiante es obligatorio")
    private Student student;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "El método de pago es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @NotBlank(message = "El período es obligatorio")
    @Size(max = 100)
    @Column(name = "period", nullable = false)
    private String period;

    @Enumerated(EnumType.STRING)
    @Column(name = "period_type")
    private PeriodType periodType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breakfast_package_id")
    private BreakfastPackage breakfastPackage;

    @Size(max = 1000)
    @Column(name = "notes")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor personalizado para casos comunes
    public Payment(Student student, BigDecimal amount, String description,
                   PaymentMethod paymentMethod, PaymentStatus status,
                   LocalDate dueDate, String period) {
        this.student = student;
        this.amount = amount;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.dueDate = dueDate;
        this.period = period;
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

}
