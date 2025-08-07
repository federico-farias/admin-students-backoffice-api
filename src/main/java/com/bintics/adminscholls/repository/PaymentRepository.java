package com.bintics.adminscholls.repository;

import com.bintics.adminscholls.model.Payment;
import com.bintics.adminscholls.model.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentId(Long studentId);

    List<Payment> findByStatus(PaymentStatus status);

    Page<Payment> findByStatusOrderByDueDateAsc(PaymentStatus status, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = :status")
    Long countByStatus(@Param("status") PaymentStatus status);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'PAGADO' AND MONTH(p.paymentDate) = MONTH(CURRENT_DATE) AND YEAR(p.paymentDate) = YEAR(CURRENT_DATE)")
    BigDecimal getMonthlyRevenue();

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status IN ('PENDIENTE', 'VENCIDO')")
    BigDecimal getUnpaidAmount();

    @Query("SELECT p FROM Payment p WHERE p.dueDate < CURRENT_DATE AND p.status = 'PENDIENTE'")
    List<Payment> findOverduePayments();
}
