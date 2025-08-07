package com.bintics.adminscholls.domains.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsDTO {

    private Long totalStudents;
    private Long activeStudents;
    private Long totalPayments;
    private Long pendingPayments;
    private BigDecimal monthlyRevenue;
    private BigDecimal unpaidAmount;
    private Long totalGroups;
    private Long fullGroups;
    private Double averageOccupancy;

    // Constructor personalizado para casos comunes
    public DashboardStatsDTO(Long totalStudents, Long activeStudents, Long totalPayments,
                           Long pendingPayments, BigDecimal monthlyRevenue, BigDecimal unpaidAmount) {
        this.totalStudents = totalStudents;
        this.activeStudents = activeStudents;
        this.totalPayments = totalPayments;
        this.pendingPayments = pendingPayments;
        this.monthlyRevenue = monthlyRevenue;
        this.unpaidAmount = unpaidAmount;
    }

    // Utility methods
    public Double getStudentActivationRate() {
        if (totalStudents != null && totalStudents > 0 && activeStudents != null) {
            return (activeStudents.doubleValue() / totalStudents.doubleValue()) * 100;
        }
        return 0.0;
    }

    // Método para compatibilidad con código existente
    public Double getActiveStudentPercentage() {
        return getStudentActivationRate();
    }

    public Double getPaymentCompletionRate() {
        if (totalPayments != null && totalPayments > 0 && pendingPayments != null) {
            Long paidPayments = totalPayments - pendingPayments;
            return (paidPayments.doubleValue() / totalPayments.doubleValue()) * 100;
        }
        return 0.0;
    }

    // Método para compatibilidad con código existente
    public Double getPaidPaymentPercentage() {
        return getPaymentCompletionRate();
    }

    public Double getGroupOccupancyRate() {
        if (totalGroups != null && totalGroups > 0 && fullGroups != null) {
            return (fullGroups.doubleValue() / totalGroups.doubleValue()) * 100;
        }
        return 0.0;
    }
}
